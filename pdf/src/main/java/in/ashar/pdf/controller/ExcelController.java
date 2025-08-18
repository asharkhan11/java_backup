package in.ashar.pdf.controller;

import in.ashar.pdf.service.ExcelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/excel")
public class ExcelController {

    @Autowired
    private ExcelService excelService;

    @GetMapping("/read")
    public String readExcel(@RequestParam("file") MultipartFile file){

        try(InputStream in = file.getInputStream()){
            return excelService.readExcel(in);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
