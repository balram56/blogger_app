package com.blog.exception;

import com.blog.payload.ErrorDetails;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@ControllerAdvice //advice to any exception handle

public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {//like cath block -supresses the exception

    //for specific exception handle
    @ExceptionHandler(ResourceNotFoundException.class)//for exception
    public ResponseEntity<ErrorDetails> resourceNotFoundException(
            ResourceNotFoundException exception,
            WebRequest webRequest


    ) {//ErrorDetails is DTO class
        //when an exception occurce , it will go to this method and this method will put the information error detaiol object nd give into postman response

        //Create object of dto
        ErrorDetails errorDetails = new ErrorDetails(new Date(), exception.getMessage(), webRequest.getDescription(false));//if write true then client details gives
        //but if u wite false then only show exception at postman
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);


    }
//Global exception -> this method handle all type of exception
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> handleGlobalException(
            Exception exception,
            WebRequest webRequest


    ) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(), exception.getMessage(), webRequest.getDescription(false));//if write true then client details gives
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);

    }
}
