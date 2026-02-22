package com.rayancatapreta.pet_adoption_api.infra.exception;

import lombok.NonNull;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.stream.Collectors;

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

    // It captures errors due to annotations.
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            @NonNull MethodArgumentNotValidException ex,
            @NonNull HttpHeaders headers,
            @NonNull HttpStatusCode status,
            @NonNull WebRequest request){
        String messages = getValidationErrors(ex);
        ErrorMessageDTO errorResponse = new ErrorMessageDTO(HttpStatus.BAD_REQUEST, messages);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    // Collects all error messages from annotations (Ex: @Size, @NotBlank)
    private String getValidationErrors(MethodArgumentNotValidException ex) {
        return ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage) // Usando a sugestão do IntelliJ
                .collect(Collectors.joining(", "));
    }
}