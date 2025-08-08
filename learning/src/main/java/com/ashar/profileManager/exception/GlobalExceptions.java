package com.ashar.profileManager.exception;

import com.ashar.profileManager.response.ErrorResponseMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@Slf4j
@RestControllerAdvice
public class GlobalExceptions {


    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponseMessage> resourceNotFoundException(ResourceNotFoundException ex){
        log.error("Resource not found exception : {}", ex.getMessage());
        return new ResponseEntity<>(new ErrorResponseMessage("NOT FOUND", ex.getMessage()),HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ResourceAlreadyExists.class)
    public ResponseEntity<ErrorResponseMessage> resourceAlreadyExists(ResourceAlreadyExists ex){
        log.error("Resource Already exists : {}", ex.getMessage());
        return new ResponseEntity<>(new ErrorResponseMessage("CONFLICT", ex.getMessage()),HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ProfileNotFoundException.class)
    public String profileNotFoundException(ProfileNotFoundException profileNotFoundException)
    {
        log.error("Handling the exception");
        return profileNotFoundException.getMessage();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseMessage> validationError(MethodArgumentNotValidException ex){

        List<String> errors = ex.getBindingResult().getFieldErrors().stream()
                .map(n -> n.getField() + " : " + n.getDefaultMessage())
                .toList();

        log.error("validation error occured : {}",errors);

        return new ResponseEntity<>(new ErrorResponseMessage("Validation Failed",errors.toString()),HttpStatus.BAD_REQUEST);
    }
}
