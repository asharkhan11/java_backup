package in.ashar.handleFiles.service;

import in.ashar.handleFiles.response.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileService {


    public ResponseEntity<Response> readFile(MultipartFile file) {

        String fileType = checkFileType(file);

        if(fileType.equals("application/pdf"))
        {
            System.out.println(file.getOriginalFilename() +" : is pdf");
        }
        else if (fileType.contains("spreadsheetml") || fileType.contains("ms-excel"))
        {
            System.out.println(file.getOriginalFilename() +" : is xlsx or xls");
        }
        else if (fileType.contains("image"))
        {
            System.out.println(file.getOriginalFilename() +" : is image");
        }
        else
        {
            System.out.println("Unsupported file format : "+ checkFileType(file));
        }

        System.out.println(fileType);
        return null;
    }

    private String checkFileType(MultipartFile file) {
        return file.getContentType();
    }


}
