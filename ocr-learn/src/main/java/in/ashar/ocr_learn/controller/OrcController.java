package in.ashar.ocr_learn.controller;

import in.ashar.ocr_learn.service.OcrService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/ocr")
@RequiredArgsConstructor
public class OrcController {

    private final OcrService ocrService;

    @PostMapping
    public String extractText(@RequestParam("file")MultipartFile file){
        return ocrService.extractText(file);
    }
}
