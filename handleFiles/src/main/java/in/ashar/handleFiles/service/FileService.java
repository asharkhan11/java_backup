package in.ashar.handleFiles.service;

import in.ashar.handleFiles.entities.mysql.Invoice;
import in.ashar.handleFiles.entities.mysql.PdfTable;
import in.ashar.handleFiles.entities.oracle.ExcelTable;
import in.ashar.handleFiles.exception.DataNotFoundException;
import in.ashar.handleFiles.exception.InputOutputException;
import in.ashar.handleFiles.exception.InvalidDataException;
import in.ashar.handleFiles.repositories.mysql.InvoiceRepository;
import in.ashar.handleFiles.repositories.mysql.PdfTableRepository;
import in.ashar.handleFiles.repositories.oracle.ExcelRepository;
import in.ashar.handleFiles.response.Response;
import in.ashar.handleFiles.response.SuccessResponse;
import in.ashar.handleFiles.utility.ImagePreprocessor;
import in.ashar.handleFiles.utility.ResponseEnum;
import lombok.extern.slf4j.Slf4j;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import technology.tabula.*;
import technology.tabula.Table;
import technology.tabula.extractors.SpreadsheetExtractionAlgorithm;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import java.awt.Rectangle;
import java.io.IOException;

@Slf4j
@Service
public class FileService {

    private final InvoiceRepository invoiceRepository;
    private final PdfTableRepository pdfTableRepository;
    private final ExcelRepository excelRepository;

    public FileService(InvoiceRepository invoiceRepository, PdfTableRepository pdfTableRepository, ExcelRepository excelRepository) {
        this.invoiceRepository = invoiceRepository;
        this.excelRepository = excelRepository;
        this.pdfTableRepository = pdfTableRepository;
    }

    // Method called from controller
    public ResponseEntity<Response> readFile(MultipartFile file) {
        return readContent(file);
    }

    // Gateway method
    private ResponseEntity<Response> readContent(MultipartFile file) {

        // Checking type of file
        String fileType = checkFileType(file);

        if (fileType.equals("application/pdf")) {

            log.info("processing pdf : {}", file.getOriginalFilename());
            return processPdf(file);

        } else if (fileType.contains("spreadsheetml") || fileType.contains("ms-excel")) {
            log.info("processing Excel file : {}", file.getOriginalFilename());
            return processExcel(file);

        } else if (fileType.contains("image")) {
            log.info("processing an image : {}", file.getOriginalFilename());
            return processImage(file);

        } else if (fileType.contains("application/octet-stream")) {

            if (Objects.requireNonNull(file.getOriginalFilename()).endsWith(".jfif")) {
                log.info("processing image : {}", file.getOriginalFilename());
                return processImage(file);
            } else {
                log.info("Unsupported binary file : {}", file.getOriginalFilename());
                throw new InvalidDataException("Unsupported file type : " + file.getContentType());
            }

        } else {
            log.error("Unsupported file format : {}", checkFileType(file));
            throw new InvalidDataException("Unsupported file type : " + file.getContentType());
        }
    }

    // process PDF File
    private ResponseEntity<Response> processPdf(MultipartFile file) {
        ITesseract tesseract = new Tesseract();
        tesseract.setLanguage("eng");
        tesseract.setDatapath("C:\\Program Files\\Tesseract-OCR\\tessdata-main");

        StringBuilder extractedText = new StringBuilder();

        List<Table> tablesInPdf = findTableInPdf(file);

        if (tablesInPdf != null) {

            log.info("Invoice and Transaction tables found");

            if (storeTableInMysqlAndReturnData(tablesInPdf)) {
                log.info("Tables stored into mysql database");
            } else {
                log.error("error while storing tables into database");
            }
        } else {
            log.error("Invoice and Transaction tables not found, only returning content back to user");
        }

        try (InputStream is = file.getInputStream();
             PDDocument document = PDDocument.load(is)) {

            PDFTextStripper stripper = new PDFTextStripper();
            String stripperText = stripper.getText(document);

            if(stripperText.isEmpty()){
                PDFRenderer renderer = new PDFRenderer(document);
                for (int page = 0; page < document.getNumberOfPages(); page++) {
                    BufferedImage bim = renderer.renderImageWithDPI(page, 300.0f);
                    extractedText.append(tesseract.doOCR(bim)).append(System.lineSeparator());
                }
            }else{
                extractedText = new StringBuilder(stripperText);
            }


        } catch (IOException | TesseractException e) {
            throw new InputOutputException("Error while extracting text from pdf : " + e.getMessage());
        }

        SuccessResponse<String> response = new SuccessResponse<>();
        response.setStatus(ResponseEnum.SUCCESS);
        response.setDetail("text extracted successfully");
        response.setResponse(extractedText.toString());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // process Excel File
    private ResponseEntity<Response> processExcel(MultipartFile file) {

        try (InputStream is = file.getInputStream();
             Workbook workbook = WorkbookFactory.create(is)) {

            Sheet sheet = workbook.getSheetAt(0);
            List<ExcelTable> excelTables = new ArrayList<>();

            int firstDataRow = getFirstDataRow(sheet);

            if (firstDataRow == -1) {
                log.error("required table not found in excel file, returning extracted data without storing into database");
                String excelData = readExcel(file.getInputStream());
                SuccessResponse<String> response = new SuccessResponse<>();
                response.setStatus(ResponseEnum.SUCCESS);
                response.setDetail("excel data extracted successfully");
                response.setResponse(excelData);
                return new ResponseEntity<>(response, HttpStatus.OK);
            }

            // validate header
            Row headerRow = sheet.getRow(firstDataRow - 1);
            boolean isValid = validateHeaderRow(headerRow);

            if (isValid) {
                log.info("expected table found in excel file, storing into database");
                for (int i = firstDataRow; i <= sheet.getLastRowNum(); i++) {
                    Row row = sheet.getRow(i);
                    if (row == null) continue; // skip empty rows

                    ExcelTable excelTable = new ExcelTable();
                    excelTable.setFirstName(getSafeCellString(row.getCell(1))); // col B
                    excelTable.setLastName(getSafeCellString(row.getCell(2)));  // col C
                    excelTable.setGender(getSafeCellString(row.getCell(3)));    // col D
                    excelTable.setCountry(getSafeCellString(row.getCell(4)));   // col E
                    excelTable.setAge(Integer.parseInt(getSafeCellString(row.getCell(5)))); // col F
                    excelTable.setDateOfJoining(getSafeCellString(row.getCell(6)));    // col G
                    excelTable.setUserId(Integer.parseInt(getSafeCellString(row.getCell(7)))); // col H
                    excelTables.add(excelTable);
                }
                return storeTableInOracleAndReturnData(excelTables);

            } else {
                log.info("expected table not found in excel file, returning data without storing");
                String excelData = readExcel(file.getInputStream());
                SuccessResponse<String> response = new SuccessResponse<>();
                response.setStatus(ResponseEnum.SUCCESS);
                response.setDetail("excel data extracted successfully");
                response.setResponse(excelData);
                return new ResponseEntity<>(response, HttpStatus.OK);
            }

        } catch (IOException e) {
            throw new InputOutputException("Error while reading/writing file : " + e.getMessage());
        }
    }

    // process Image
//    private ResponseEntity<Response> processImage(MultipartFile file) {
//
//        String data;
//        try {
//            BufferedImage image = ImageIO.read(file.getInputStream());
//            if (image == null) throw new InvalidDataException("Image has incorrect format");
//
//
//            // preprocess for cheque
//            BufferedImage preprocessed = ImagePreprocessor.preprocessForCheque(image);
//
//            Tesseract tesseract = new Tesseract();
//            tesseract.setDatapath("C:\\Program Files\\Tesseract-OCR\\tessdata_best-main");
//            tesseract.setLanguage("eng");
//            data = tesseract.doOCR(preprocessed);
//
//            if (data.isBlank()) throw new DataNotFoundException("Data cannot be read");
//
//            SuccessResponse<String> response = new SuccessResponse<>();
//            response.setStatus(ResponseEnum.SUCCESS);
//            response.setDetail("Test Extracted Successfully");
//            response.setResponse(data);
//
//            return new ResponseEntity<>(response, HttpStatus.OK);
//
//        } catch (IOException | TesseractException e) {
//            throw new InputOutputException("Error while reading/writing image : "+e.getMessage());
//        }
//    }




    /// ///////////////////////////////////////////////////////////////////////////////////////////



    private ResponseEntity<Response> processImage(MultipartFile file) {
        try {
            BufferedImage image = ImageIO.read(file.getInputStream());
            if (image == null) throw new InvalidDataException("Image has incorrect format");

            // Preprocess cheque
            BufferedImage preprocessed = ImagePreprocessor.preprocessForCheque(image);

            // ========== Configure Tesseract ==========
            Tesseract tesseract = new Tesseract();
            tesseract.setDatapath("C:\\Program Files\\Tesseract-OCR\\tessdata_best-main");
            tesseract.setLanguage("eng");

            try {
                tesseract.setOcrEngineMode(1); // LSTM only
            } catch (Throwable ignored) { }

            tesseract.setVariable("user_defined_dpi", "300");
            tesseract.setPageSegMode(6); // default = block of text

            // ========== 1) OCR full cheque ==========
            String fullText = tesseract.doOCR(preprocessed);


            // Tailored OCR for numbers
            tesseract.setPageSegMode(7); // single-line
            tesseract.setVariable("tessedit_char_whitelist", "0123456789.,-/ ");


            // Reset
            tesseract.setVariable("tessedit_char_whitelist", "");
            tesseract.setPageSegMode(6);


            System.out.println(fullText);



            SuccessResponse<String> response = new SuccessResponse<>();
            response.setStatus(ResponseEnum.SUCCESS);
            response.setDetail("Test Extracted Successfully");
            response.setResponse(fullText);

            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (IOException | TesseractException e) {
            throw new InputOutputException("Error while reading/writing image : " + e.getMessage());
        }
    }



    ///  //////////////////////// HELPER CLASSES ///////////////////////////////////////////////////

    public String readExcel(InputStream in) throws IOException {

        StringBuilder sb = new StringBuilder();

        Workbook workbook = WorkbookFactory.create(in);

        Sheet sheet = workbook.getSheetAt(0);
        Iterator<Row> ri = sheet.rowIterator();

        while (ri.hasNext()) {

            Iterator<Cell> ci = ri.next().cellIterator();
            while (ci.hasNext()) {

                Cell cell = ci.next();

                switch (cell.getCellType()) {
                    case STRING:
                        sb.append(cell.getStringCellValue()).append(" ");
                        break;
                    case NUMERIC:
                        if (DateUtil.isCellDateFormatted(cell)) {

                            SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                            String date = df.format(cell.getDateCellValue());
                            sb.append(date).append(" ");
                        } else {
                            sb.append(cell.getNumericCellValue()).append(" ");
                        }
                        break;
                    case FORMULA:
                        sb.append(cell.getCellFormula()).append(" ");
                        break;
                    case BOOLEAN:
                        sb.append(cell.getBooleanCellValue()).append(" ");
                        break;
                    default:
                        break;
                }
            }
            sb.append(System.lineSeparator());
        }
        workbook.close();
        return sb.toString();
    }

    private boolean validateHeaderRow(Row headerRow) {
        String[] expectedHeaders = {
                "First Name", "Last Name", "Gender", "Country", "Age", "Date", "Id"
        };

        for (int i = 0; i < expectedHeaders.length; i++) {
            Cell cell = headerRow.getCell(i + 1); // since data starts at col B
            String actual = getSafeCellString(cell);
            if (!expectedHeaders[i].equalsIgnoreCase(actual)) {
                return false;
            }
        }
        return true;
    }

    private static int getFirstDataRow(Sheet sheet) {
        int headerRowIndex = -1;
        for (Row row : sheet) {
            for (Cell cell : row) {
                String value = getSafeCellString(cell); // use safe extractor
                if ("first name".equalsIgnoreCase(value.trim())) {
                    headerRowIndex = row.getRowNum();
                    break;
                }
            }
            if (headerRowIndex != -1) break;
        }

        if (headerRowIndex == -1) {
            return -1;
        }

        return headerRowIndex + 1; // return row after header
    }

    private static String getSafeCellString(Cell cell) {
        if (cell == null) return "";

        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getLocalDateTimeCellValue().toLocalDate().toString();
                } else {
                    return String.valueOf((long) cell.getNumericCellValue());
                }
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                try {
                    return cell.getStringCellValue(); // if formula results in string
                } catch (IllegalStateException e) {
                    return String.valueOf(cell.getNumericCellValue());
                }
            case BLANK:
            default:
                return "";
        }
    }

    private ResponseEntity<Response> storeTableInOracleAndReturnData(List<ExcelTable> excelTables) {

        try {
            excelRepository.saveAll(excelTables);
        } catch (Exception e) {
            log.error("Error while storing data into table : {}", e.getMessage());
        }

        SuccessResponse<List<ExcelTable>> response = new SuccessResponse<>();
        response.setResponse(excelTables);
        response.setStatus(ResponseEnum.SUCCESS);
        response.setDetail("data extracted successfully");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    private List<Table> findTableInPdf(MultipartFile file) {

        try (InputStream is = file.getInputStream();
             PDDocument document = PDDocument.load(is)) {

            ObjectExtractor extractor = new ObjectExtractor(document);
            SpreadsheetExtractionAlgorithm sAlgorithm = new SpreadsheetExtractionAlgorithm();
            PageIterator pages = extractor.extract();

            Page page = pages.next();
            List<Table> tables = sAlgorithm.extract(page);

            if (tables.size() == 2) return tables;

        } catch (IOException e) {
            throw new RuntimeException("Error while extracting table from pdf : " + e.getMessage());
        }

        return null;
    }

    private boolean storeTableInMysqlAndReturnData(List<Table> tables) {

        if (tables.getFirst().getRowCount() != 2 || tables.getFirst().getColCount() != 4) {
            return false;
        }

        if (tables.get(1).getRowCount() < 2 || tables.get(1).getColCount() != 4) {
            return false;
        }


        List<RectangularTextContainer> rowOfInvoice = tables.get(0).getRows().get(1);
        Invoice invoice = new Invoice();
        invoice.setInvoiceNo(Integer.parseInt(rowOfInvoice.getFirst().getText()));
        invoice.setCustomerNo(Integer.parseInt(rowOfInvoice.get(1).getText()));
        invoice.setInvoicePeriod(rowOfInvoice.get(2).getText());
        invoice.setDate(rowOfInvoice.get(3).getText());
        Invoice savedInvoice = invoiceRepository.save(invoice);

        int rowCountOfPdfTable = tables.get(1).getRowCount();
        List<PdfTable> pdfTables = new ArrayList<>();
        for (int i = 1; i < rowCountOfPdfTable - 3; i++) {
            PdfTable pdfTable = new PdfTable();
            pdfTable.setDescription(tables.get(1).getCell(i, 0).getText());
            pdfTable.setAmount(tables.get(1).getCell(i, 1).getText());
            pdfTable.setQuantity(tables.get(1).getCell(i, 2).getText());
            pdfTable.setTotalAmount(tables.get(1).getCell(i, 3).getText());
            pdfTable.setInvoice(savedInvoice);
            pdfTables.add(pdfTable);
        }

        pdfTableRepository.saveAll(pdfTables);
        return true;
    }

    private String checkFileType(MultipartFile file) {
        return file.getContentType();
    }


}
