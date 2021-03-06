package br.com.insannity.catalog.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

import java.time.Instant;

@ControllerAdvice
@Slf4j
public class ControllerExceptionHandler {

    StandardError error = new StandardError();

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<StandardError> entityNotFound(EntityNotFoundException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        error.setTimestamp(Instant.now());
        error.setStatus(status.value());
        error.setError("Entity not found");
        error.setMessage(e.getMessage());
        error.setPath(request.getRequestURI());
        log.error("Classe: "+e.getStackTrace()[0].getFileName()+" Linha: "+ e.getStackTrace()[0].getLineNumber());
        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler(DatabaseException.class)
    public ResponseEntity<StandardError> database(DatabaseException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        error.setTimestamp(Instant.now());
        error.setStatus(status.value());
        error.setError("Integrity violation");
        error.setMessage(e.getMessage());
        error.setPath(request.getRequestURI());
        log.error("Classe: "+e.getStackTrace()[0].getFileName()+" Linha: "+ e.getStackTrace()[0].getLineNumber());
        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationError> validation(MethodArgumentNotValidException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
        ValidationError error = new ValidationError();
        error.setTimestamp(Instant.now());
        error.setStatus(status.value());
        error.setError("Validation exception");
        error.setMessage(e.getMessage());
        error.setPath(request.getRequestURI());

        for(FieldError fieldError: e.getBindingResult().getFieldErrors()) {
            error.addError(fieldError.getField(), fieldError.getDefaultMessage());
        }

        log.error("Classe: "+e.getStackTrace()[0].getFileName()+" Linha: "+ e.getStackTrace()[0].getLineNumber());
        return ResponseEntity.status(status).body(error);
    }

}
