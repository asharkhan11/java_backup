package in.ashar.pdf.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;

@Slf4j
@Service
public class ExcelService {

    public String readExcel(InputStream in) throws IOException {


        StringBuilder sb = new StringBuilder();

        Workbook workbook = WorkbookFactory.create(in);



        Sheet sheet = workbook.getSheetAt(0);

        Iterator<Row> ri = sheet.rowIterator();

        while (ri.hasNext()){

            Iterator<Cell> ci = ri.next().cellIterator();

            while (ci.hasNext()){

                Cell cell = ci.next();

                switch (cell.getCellType()){
                    case STRING:
                        sb.append(cell.getStringCellValue()).append(" ");
                        break;
                    case NUMERIC:
                        if(DateUtil.isCellDateFormatted(cell)){

                            SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                            String date = df.format(cell.getDateCellValue());
                            sb.append(date).append(" ");
                        }
                        else {
                            sb.append(cell.getNumericCellValue()).append(" ");
                            log.info("else block : {}",cell.getNumericCellValue());
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
}
