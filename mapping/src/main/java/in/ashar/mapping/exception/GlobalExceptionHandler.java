package in.ashar.mapping.exception;

import in.ashar.mapping.response.FailureResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<FailureResponse> genericExceptionHandler(Exception e){
        return new ResponseEntity<>(new FailureResponse("FAILURE",e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<FailureResponse> noSuchCompanyExceptionHandler(NotFoundException e){
        return new ResponseEntity<>(new FailureResponse("NOT FOUND",e.getMessage()),HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IllegalOperationException.class)
    public ResponseEntity<FailureResponse> illegalOperationExceptionHandler(IllegalOperationException e){
        return new ResponseEntity<>(new FailureResponse("UNAUTHORIZED",e.getMessage()),HttpStatus.FORBIDDEN);
    }
}
