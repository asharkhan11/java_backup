package in.ashar.handleFiles.controller;

import in.ashar.handleFiles.response.Response;
import in.ashar.handleFiles.service.FileService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/file")
public class FileController {

    private final FileService fileService;

    public FileController(FileService fileService){
        this.fileService = fileService;
    }

    @GetMapping("/read")
    public ResponseEntity<Response> getFile(@RequestParam("file")MultipartFile file){
        return fileService.readFile(file);
    }

}
