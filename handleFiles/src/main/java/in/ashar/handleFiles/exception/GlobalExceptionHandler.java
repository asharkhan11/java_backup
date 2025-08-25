package in.ashar.handleFiles.exception;

import in.ashar.handleFiles.response.FailureResponse;
import in.ashar.handleFiles.response.Response;
import in.ashar.handleFiles.utility.ResponseEnum;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidDataException.class)
    public ResponseEntity<Response> invalidDataExceptionHandler(InvalidDataException e){
        FailureResponse response = new FailureResponse();
        response.setStatus(ResponseEnum.FAILURE);
        response.setDetail("Invalid data : "+e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<Response> dataNotFoundExceptionHandler(DataNotFoundException e){
        FailureResponse response = new FailureResponse();
        response.setStatus(ResponseEnum.FAILURE);
        response.setDetail("data not found : "+e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<Response> nullPointerExceptionHandler(NullPointerException e){
        FailureResponse response = new FailureResponse();
        response.setStatus(ResponseEnum.FAILURE);
        response.setDetail("null pointer exception : "+e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InputOutputException.class)
    public ResponseEntity<Response> inputOutputExceptionHandler(InputOutputException e){
        FailureResponse response = new FailureResponse();
        response.setStatus(ResponseEnum.FAILURE);
        response.setDetail(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
