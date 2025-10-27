package in.ashar.pdf.service;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

@Service
public class PdfService {
    public String readPdf(InputStream in) {

        StringBuilder data = new StringBuilder();

        try (PDDocument document = PDDocument.load(in)) {


            PDFRenderer renderer = new PDFRenderer(document);

            Tesseract tesseract = new Tesseract();
            tesseract.setDatapath("C:\\Program Files\\Tesseract-OCR\\tessdata-main");
            tesseract.setLanguage("eng");



            for (int i = 0; i < document.getNumberOfPages(); i++) {
                BufferedImage image = renderer.renderImageWithDPI(i, 300);
                data.append(tesseract.doOCR(image));
            }

        } catch (IOException | TesseractException e) {
            throw new RuntimeException(e);
        }

        if (data.isEmpty()) return "Retry with good image";

        return data.toString();
    }
}