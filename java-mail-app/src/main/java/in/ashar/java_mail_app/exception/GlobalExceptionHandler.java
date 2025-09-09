package in.ashar.java_mail_app.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ErrorWhileSendingMail.class)
    public ResponseEntity<String> errorWhileSendingMailHandler(ErrorWhileSendingMail e){
        return ResponseEntity.internalServerError().body(e.getMessage());
    }

    @ExceptionHandler(ErrorWhileReadingMail.class)
    public ResponseEntity<String> errorWhileReadingMailHandler(ErrorWhileReadingMail e){
        return ResponseEntity.internalServerError().body(e.getMessage());
    }

    @ExceptionHandler(ErrorWhileStoringFile.class)
    public ResponseEntity<String> errorWhileStoringFileHandler(ErrorWhileStoringFile e){
        return ResponseEntity.internalServerError().body(e.getMessage());
    }

}
