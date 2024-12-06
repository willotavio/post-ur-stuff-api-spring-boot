package com.posturstuff.exception.user;

import com.posturstuff.exception.ApplicationException;

public class UserNotFoundException extends ApplicationException {
    public UserNotFoundException(String message) {
        super(message);
    }
}
