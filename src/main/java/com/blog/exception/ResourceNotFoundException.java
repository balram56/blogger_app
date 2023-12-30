package com.blog.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND) //For resource not found .
////create custome exception class
public class ResourceNotFoundException extends RuntimeException{


    public ResourceNotFoundException(String message) {
      super(message); //its call the parent class constructors
    }
}
