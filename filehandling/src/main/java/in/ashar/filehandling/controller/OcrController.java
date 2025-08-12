package in.ashar.filehandling.controller;

import in.ashar.filehandling.service.OcrService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/ocr")
public class OcrController {

    private final OcrService ocrService;

    OcrController(OcrService ocrService){
        this.ocrService = ocrService;
    }


    @GetMapping("/text")
    public String getTextFromImage(@RequestParam("image")MultipartFile file){
        return ocrService.getTextFromImage(file);
    }
}
