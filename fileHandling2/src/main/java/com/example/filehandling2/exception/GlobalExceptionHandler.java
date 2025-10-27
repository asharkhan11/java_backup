package com.example.filehandling2.exception;


import io.netty.handler.timeout.ReadTimeoutException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MyError.class)
    public ResponseEntity<String> internalServerError(MyError e){
        return ResponseEntity.internalServerError().body(e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> genericExceptionHandler(Exception e){
        return ResponseEntity.internalServerError().body(e.getMessage());
    }
}
