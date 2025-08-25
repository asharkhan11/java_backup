package in.ashar.handleFiles.controller;

import in.ashar.handleFiles.response.Response;
import in.ashar.handleFiles.service.FileService;
import in.ashar.handleFiles.service.InvoiceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;

@RestController
@RequestMapping("/file")
public class FileController {

    private final FileService fileService;
    private final InvoiceService invoiceService;

    public FileController(FileService fileService, InvoiceService invoiceService){
        this.fileService = fileService;
        this.invoiceService = invoiceService;
    }

    @GetMapping("/extract")
    public ResponseEntity<Response> getFile(@RequestParam("file")MultipartFile file) throws IOException {
        return fileService.readFile(file);
    }

    @GetMapping("/invoice/{id}")
    public ResponseEntity<Response> getInvoiceByInvoiceNumber(@PathVariable("id") int id) throws FileNotFoundException {
        return invoiceService.getInvoiceByInvoiceNumber(id);
    }

}
