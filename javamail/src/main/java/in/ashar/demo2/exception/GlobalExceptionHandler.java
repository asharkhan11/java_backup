package in.ashar.demo2.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IncompleteDataException.class)
    public String incompleteDataExceptionHandler(IncompleteDataException e){
        return e.getMessage();
    }
}
