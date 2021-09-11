package com.github.matheusmv.personapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CPFAlreadyRegisteredException extends RuntimeException {

    public CPFAlreadyRegisteredException(String message) {
        super(message);
    }
}
