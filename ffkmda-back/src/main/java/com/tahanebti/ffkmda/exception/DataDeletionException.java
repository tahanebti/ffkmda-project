package com.tahanebti.ffkmda.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class DataDeletionException extends RuntimeException {

    public <T> DataDeletionException(T entity) {
        super(String.format("Data '%s' cannot be deleted", entity));
    }
}