package in.ashar.filehandling.controller;

import in.ashar.filehandling.entity.FileData;
import in.ashar.filehandling.service.FileInSystemService;
import in.ashar.filehandling.service.FileService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/file")
public class FileController {

    @Autowired
    private FileService fileService;

    @Autowired
    private FileInSystemService fileInSystemService;

    @PostMapping("/db/upload")
    public ResponseEntity<FileData> uploadFile(@RequestParam("file") MultipartFile file) {
        return fileService.uploadFile(file);
    }

    @GetMapping("/db/{id}")
    public ResponseEntity<byte[]> readFile(@PathVariable("id") int id){
        return fileService.readFile(id);
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadLargeFile(@RequestParam("file") MultipartFile file){
        return fileInSystemService.uploadLargeFile(file);
    }

    @GetMapping("/{id}")
    public void readLargeFile(@PathVariable("id") int id, HttpServletResponse response){
        fileInSystemService.readLargeFile(id,response);
    }
}
