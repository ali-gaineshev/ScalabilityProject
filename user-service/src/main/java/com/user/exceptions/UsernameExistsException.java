package com.user.exceptions;


import org.springframework.web.bind.annotation.ResponseStatus;

public class UsernameExistsException extends RuntimeException {
    public UsernameExistsException(String errorMessage) {
        super(errorMessage);
    }
    
}