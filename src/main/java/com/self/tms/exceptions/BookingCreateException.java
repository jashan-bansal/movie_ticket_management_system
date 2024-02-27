package com.self.tms.exceptions;

import lombok.Data;

@Data
public class BookingCreateException  extends Throwable {
    private String message;
    private int httpStatusCode;

    public BookingCreateException(String message, int httpStatusCode) {
        this.message = message;
        this.httpStatusCode = httpStatusCode;
    }
}
