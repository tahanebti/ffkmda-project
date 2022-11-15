package com.tahanebti.ffkmda.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class DataNotFoundException extends RuntimeException {

    public <ID> DataNotFoundException(ID id) {
        super(String.format("Data with '%s' not found", id));
    }
}