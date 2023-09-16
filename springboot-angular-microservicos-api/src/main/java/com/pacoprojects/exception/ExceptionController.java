package com.pacoprojects.exception;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.sql.SQLException;

@RestControllerAdvice
public class ExceptionController extends ResponseEntityExceptionHandler {


    // Tratamento de erros generico do Java a nivel runtime
    @Override
    @ExceptionHandler(value = {Exception.class, RuntimeException.class, Throwable.class})
    protected ResponseEntity<Object> handleExceptionInternal(@NonNull Exception exception, Object body, @NonNull HttpHeaders headers, @NonNull HttpStatusCode statusCode, @NonNull WebRequest request) {
        StringBuilder builder = new StringBuilder();

        if (exception instanceof MethodArgumentNotValidException) {
            BindingResult bindingResult = ((MethodArgumentNotValidException) exception).getBindingResult();
            if (bindingResult.hasErrors()) {
                bindingResult.getAllErrors().forEach(objectError -> builder.append(objectError.getDefaultMessage()).append("\n"));
                String message = builder.substring(0, builder.length() - 1);
                builder.delete(0, builder.length());
                builder.append(message);
            }
        } else if (exception instanceof ResponseStatusException) {
            builder.append(((ResponseStatusException) exception).getReason());
        } else {
            builder.append(exception.getMessage());
        }

        return new ResponseEntity<>(ExceptionObject
                .builder()
                .message(builder.toString())
                .code(statusCode.value())
                .build(), headers, statusCode);
    }

    // Tratamento de Erros a nivel de Banco de dados
    @ExceptionHandler(value = {DataIntegrityViolationException.class, ConstraintViolationException.class, SQLException.class})
    protected ResponseEntity<Object> handleExceptionDataIntegrity(Exception exception) {

        StringBuilder message = new StringBuilder();

        if (exception instanceof DataIntegrityViolationException) {
            message.append(exception.getCause().getCause().getMessage());
        } else if (exception instanceof ConstraintViolationException) {
            message.append(exception.getCause().getCause().getMessage());
        } else if (exception instanceof SQLException) {
            message.append(exception.getCause().getCause().getMessage());
        } else {
            message.append(exception.getMessage());
        }

        return new ResponseEntity<>(ExceptionObject
                .builder()
                .message(message.toString())
                .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .build(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
