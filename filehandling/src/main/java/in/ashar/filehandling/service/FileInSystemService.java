package in.ashar.filehandling.service;

import in.ashar.filehandling.entity.FileInSystem;
import in.ashar.filehandling.repository.FileInSystemRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.WritableByteChannel;
import java.nio.file.*;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class FileInSystemService {

    @Autowired
    private FileInSystemRepository fileInSystemRepository;

    private final Path uploadPath = Paths.get("C:\\Users\\jalas\\OneDrive\\Desktop\\LargerFiles");

    public ResponseEntity<String> uploadLargeFile(MultipartFile file) {
        try {
            if (Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            String fileName = UUID.randomUUID().toString();
            Path filePath = uploadPath.resolve(fileName);

            FileInSystem fis = new FileInSystem();
            fis.setContentType(file.getContentType());
            fis.setPath(filePath.toString());

            InputStream inputStream = file.getInputStream();
            Files.copy(inputStream,filePath, StandardCopyOption.REPLACE_EXISTING);

            inputStream.close();

            return new ResponseEntity<>(String.valueOf(fileInSystemRepository.save(fis).getId()),HttpStatus.OK);


        } catch (IOException e) {
            log.error("error : {}",e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public void readLargeFile(int id, HttpServletResponse response) {

        try {
            Optional<FileInSystem> byId = fileInSystemRepository.findById(id);

            if(byId.isEmpty()) response.sendError(HttpServletResponse.SC_NOT_FOUND);

            FileInSystem fileInSystem = byId.get();

            response.setContentType(fileInSystem.getContentType());

            FileChannel fileChannel = FileChannel.open(Path.of(fileInSystem.getPath()), StandardOpenOption.READ);

            WritableByteChannel writableByteChannel = Channels.newChannel(response.getOutputStream());

            long position =0;
            long size = fileChannel.size();

            while (position<size){
                position += fileChannel.transferTo(position, size-position,writableByteChannel);
            }

            writableByteChannel.close();
            fileChannel.close();

        }catch (IOException e){
            log.error("error : {}",e.getMessage());
        }



    }
}
