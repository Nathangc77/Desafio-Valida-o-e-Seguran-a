package com.devsuperior.demo.controllers.handlers;

import com.devsuperior.demo.dto.exceptions.FieldMessage;
import com.devsuperior.demo.dto.exceptions.ValidationError;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;

@ControllerAdvice
public class ControlExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationError> MethodArgumentNotValid(MethodArgumentNotValidException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
        ValidationError err = new ValidationError(Instant.now(), status.value(), "Validation Error", request.getRequestURI());

        for (FieldError f : e.getFieldErrors()) {
            err.addError(new FieldMessage(f.getField(), f.getDefaultMessage()));
        }

        return ResponseEntity.status(status).body(err);
    }
}
