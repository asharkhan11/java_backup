package in.ashar.filehandling.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import in.ashar.filehandling.entity.FileData;
import in.ashar.filehandling.entity.FileInSystem;
import in.ashar.filehandling.exception.FileException;
import in.ashar.filehandling.exception.NoFileException;
import in.ashar.filehandling.repository.FileInSystemRepository;
import in.ashar.filehandling.repository.FileRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@Slf4j
@Service
public class FileService {

    @Autowired
    private FileRepository fileRepository;

    public ResponseEntity<FileData> uploadFile(MultipartFile file) {

        try {
            byte[] bytes = file.getBytes();

            FileData fileData = new FileData();
            fileData.setFile(bytes);
            fileData.setContentType(file.getContentType());

            FileData save = fileRepository.save(fileData);
            return new ResponseEntity<>(save, HttpStatus.CREATED);
        } catch (IOException e) {
            throw new FileException(e.getMessage());
        }
    }

    public ResponseEntity<byte[]> readFile(int id) {

        Optional<FileData> oFile = fileRepository.findById(id);

        if (oFile.isEmpty()) throw new NoFileException("file not found for id : " + id);

        FileData fileData = oFile.get();

        byte[] fileContent = fileData.getFile();
        String contentType = fileData.getContentType();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.valueOf(contentType));

        return new ResponseEntity<>(fileContent, headers, HttpStatus.OK);

    }

}
