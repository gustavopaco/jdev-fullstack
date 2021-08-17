package br.com.curso.webmvnspringbootmicroservicos.controller;

import br.com.curso.webmvnspringbootmicroservicos.model.ExceptionObject;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.sql.SQLException;
import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

//@ControllerAdvice
@RestControllerAdvice
public class ExceptionController extends ResponseEntityExceptionHandler {

    // IMPORTANT: Tratamento de erros generico a nivel runtime
    @Override
    @NonNull @ExceptionHandler(value = {Exception.class, RuntimeException.class, Throwable.class})
    protected ResponseEntity<Object> handleExceptionInternal(@NonNull Exception exception, Object body, @NonNull HttpHeaders headers,
                                                             @NonNull HttpStatus status, @NonNull WebRequest request) {

        StringBuilder message = new StringBuilder();

        if (exception instanceof MethodArgumentNotValidException) {
            List<ObjectError> exceptionObjects = ((MethodArgumentNotValidException) exception).getBindingResult().getAllErrors();
            for (ObjectError error: exceptionObjects) {
                message.append(error.getDefaultMessage());
            }
        } else {
            message.append(exception.getMessage());
        }

        ExceptionObject exceptionObject = new ExceptionObject();
        exceptionObject.setErro(message.toString());
        exceptionObject.setCodigo(status.value() + " ==> " + status.getReasonPhrase());

        return new ResponseEntity<>(exceptionObject, headers,status);
    }

    // IMPORTANT: Tratamento de erros a nivel de persistencia de banco de dados
    @ExceptionHandler(value = {DataIntegrityViolationException.class, ConstraintViolationException.class, SQLException.class})
    protected ResponseEntity<Object> handleExceptionDataIntegry(Exception exception) {

        StringBuilder message = new StringBuilder();

        if (exception instanceof DataIntegrityViolationException) {
            message.append(exception.getCause().getCause().getMessage());
        } else if(exception instanceof ConstraintViolationException) {
            message.append(exception.getCause().getCause().getMessage());
        } else if(exception instanceof SQLException){
            message.append(exception.getCause().getCause().getMessage());
        }else {
            message.append(exception.getMessage());
        }

        ExceptionObject exceptionObject = new ExceptionObject();
        exceptionObject.setErro(message.toString());
        exceptionObject.setCodigo(INTERNAL_SERVER_ERROR + " ==> " + INTERNAL_SERVER_ERROR.getReasonPhrase());
        return new ResponseEntity<>(exceptionObject, INTERNAL_SERVER_ERROR);
    }
}
