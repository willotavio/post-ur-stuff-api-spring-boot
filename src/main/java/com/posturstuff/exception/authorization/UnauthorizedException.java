package com.posturstuff.exception.authorization;

import com.posturstuff.exception.ApplicationException;

public class UnauthorizedException extends ApplicationException {
    public UnauthorizedException(String message) {
        super(message);
    }
}
