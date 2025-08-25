package in.ashar.handleFiles.service;

import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.pdf.draw.DottedLineSeparator;
import in.ashar.handleFiles.entities.mysql.Invoice;
import in.ashar.handleFiles.entities.mysql.PdfTable;
import in.ashar.handleFiles.exception.DataNotFoundException;
import in.ashar.handleFiles.repositories.mysql.InvoiceRepository;
import in.ashar.handleFiles.response.Response;
import in.ashar.handleFiles.response.SuccessResponse;
import in.ashar.handleFiles.utility.ResponseEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Optional;

@Service
public class InvoiceService {

    @Autowired
    private InvoiceRepository invoiceRepository;

    public ResponseEntity<Response> getInvoiceByInvoiceNumber(int id) throws FileNotFoundException {

//        Optional<Invoice> oInvoice = invoiceRepository.findByInvoiceNo(id);

        Optional<Invoice> oInvoice = invoiceRepository.findById(id);

        if(oInvoice.isEmpty()) throw new DataNotFoundException("Invoice for invoice ID : "+id +" not found");

        Invoice invoice = oInvoice.get();

        // generate pdf
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream("invoice_"+invoice.getInvoiceNo()+".pdf"));
        document.open();

        //Title

        PdfPTable titleTable = new PdfPTable(2);
        DottedLineSeparator lineSeparator = new DottedLineSeparator();
        lineSeparator.setPercentage(80);
        lineSeparator.setAlignment(Element.ALIGN_CENTER);
        lineSeparator.setLineWidth(1.5f);

        titleTable.addCell(String.valueOf(invoice.getInvoiceNo()));
        titleTable.addCell(String.valueOf(invoice.getCustomerNo()));
        titleTable.addCell(invoice.getInvoicePeriod());
        titleTable.addCell(invoice.getDate());
        titleTable.setSpacingAfter(15f);

        document.add(titleTable);

        document.add(lineSeparator);

        // Main table

        PdfPTable table = new PdfPTable(new float[]{1f, 1f,2f,1f,1f});
        table.setSpacingBefore(15f);
        table.setWidthPercentage(95);

        List<PdfTable> pdfTables = invoice.getPdfTable();

        table.addCell("id");
        table.addCell("amount");
        table.addCell("description");
        table.addCell("quantity");
        table.addCell("totalAmount");

        double grandTotal = 0.0;

        for(PdfTable pdfTable : pdfTables){
            table.addCell(String.valueOf(pdfTable.getId()));
            table.addCell(String.valueOf(pdfTable.getAmount()));
            table.addCell(pdfTable.getDescription());
            table.addCell(pdfTable.getQuantity());
            table.addCell(String.valueOf(pdfTable.getTotalAmount()));

            String totalAmount = pdfTable.getTotalAmount();
            String stringAmount = totalAmount.replace("€", "").replace(",", ".").trim();
            grandTotal += Double.parseDouble(stringAmount);
        }

        //Total
        PdfPCell cell1 = new PdfPCell(new Phrase("Total : "));
        cell1.setBackgroundColor(Color.LIGHT_GRAY);
        cell1.setColspan(4);
        cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell1.setVerticalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell1);
        table.addCell(String.valueOf(grandTotal)+" €");

        // GST
        double gst = grandTotal * 0.18;

        PdfPCell cell2 = new PdfPCell(new Phrase("GST 18% : "));
        cell2.setBackgroundColor(Color.LIGHT_GRAY);
        cell2.setColspan(4);
        cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell2.setVerticalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell2);
        table.addCell(String.valueOf(gst)+" €");


        // Gross Amount
        double grossAmount = grandTotal + gst;

        PdfPCell cell3 = new PdfPCell(new Phrase("Gross Amount : "));
        cell3.setBackgroundColor(Color.LIGHT_GRAY);
        cell3.setColspan(4);
        cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell3.setVerticalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell3);
        table.addCell(String.valueOf(grossAmount)+" €");

        document.add(table);

        SuccessResponse<String> response = new SuccessResponse<>();
        response.setStatus(ResponseEnum.SUCCESS);
        response.setDetail("Invoice found");
        response.setResponse("Invoice created");

        document.close();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}