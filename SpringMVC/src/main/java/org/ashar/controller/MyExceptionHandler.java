package org.ashar.controller;


import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class MyExceptionHandler {


    @ExceptionHandler(Exception.class)
    public String exceptionHandled(Exception e){
        return "Exceptoin handled : "+ e.getMessage();
    }
}
