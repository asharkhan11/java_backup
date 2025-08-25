package in.ashar.scanCheque.controller;

import in.ashar.scanCheque.service.OcrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/ocr")
public class OcrController {
    @Autowired
    private OcrService ocrService;

    @PostMapping("/extract")
    public String extractText(@RequestParam("file")MultipartFile file) throws IOException {
       return ocrService.extractText(file);
    }

}
