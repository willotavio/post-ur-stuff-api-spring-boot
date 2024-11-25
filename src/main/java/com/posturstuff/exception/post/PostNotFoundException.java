package com.posturstuff.exception.post;

import com.posturstuff.exception.ApplicationException;

public class PostNotFoundException extends ApplicationException {
    public PostNotFoundException(String message) {
        super(message);
    }
}
