package com.blog.payload;

import java.util.Date;

public class ErrorDetails {//Dto class
    private Date timestamp;//this Date class is improt frm java.util package
    private String message;
    private String details;

    public ErrorDetails(Date timestamp, String message, String details) {//create constructors
        this.timestamp = timestamp;
        this.message = message;
        this.details = details;
    }
    //Generate getter
    public Date getTimestamp() {
        return timestamp;
    }

    public String getMessage() {
        return message;
    }

    public String getDetails() {
        return details;
    }


}
