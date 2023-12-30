package com.blog.exception;

import org.springframework.http.HttpStatus;

public class BlogApiException extends Throwable {
    public BlogApiException(HttpStatus httpStatus, String invalidJwtSignature) {
    }
}
