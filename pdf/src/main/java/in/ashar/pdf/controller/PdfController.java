package in.ashar.pdf.controller;

import in.ashar.pdf.service.PdfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("/pdf")
public class PdfController {

    @Autowired
    private PdfService pdfService;

    @GetMapping("/read")
    public String readPdf(@RequestParam("file") MultipartFile file) {
        try (InputStream in = file.getInputStream()) {
            return pdfService.readPdf(in);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
