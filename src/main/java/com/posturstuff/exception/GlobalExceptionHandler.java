package com.posturstuff.exception;

import com.mongodb.ErrorCategory;
import com.mongodb.MongoWriteException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ResponseEntity<Object>(errors, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MongoWriteException.class)
    public ResponseEntity<Object> handleMongoWriteException(MongoWriteException ex) {
        Map<String, String> errors = new HashMap<>();
        if(ex.getError().getCategory().equals(ErrorCategory.DUPLICATE_KEY)) {
            String field = "";
            int indexStart = ex.getMessage().indexOf("index: ") + 7;
            int indexEnd = ex.getMessage().indexOf(" ", indexStart);
            field = ex.getMessage().substring(indexStart, indexEnd);
            errors.put("error", "The provided " + field + " already exists");
        }
        else {
            errors.put("error", "Error saving data");
        }
        return new ResponseEntity<Object>(errors, HttpStatus.BAD_REQUEST);
    }
}