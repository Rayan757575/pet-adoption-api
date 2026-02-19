package com.rayancatapreta.pet_adoption_api.infra.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice // It tells Spring to watch for exceptions in all controllers.
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    // Catches general logic errors (such as email already registered)
    @ExceptionHandler(RuntimeException.class)
    private ResponseEntity<ErrorMessageDTO> runtimeHandler(RuntimeException exception) {
        ErrorMessageDTO errorResponse = new ErrorMessageDTO(HttpStatus.BAD_REQUEST, exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    // It captures login errors (wrong password or email)
    @ExceptionHandler(BadCredentialsException.class)
    private ResponseEntity<ErrorMessageDTO> badCredentialsHandler(BadCredentialsException exception) {
        ErrorMessageDTO errorResponse = new ErrorMessageDTO(HttpStatus.FORBIDDEN, "Invalid Credentials.");
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponse);
    }
}