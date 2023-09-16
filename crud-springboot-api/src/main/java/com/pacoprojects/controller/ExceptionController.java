package com.pacoprojects.controller;

import com.pacoprojects.model.ExceptionObj;
import lombok.NonNull;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.sql.SQLException;
import java.util.List;

//@RestControllerAdvice
public class ExceptionController extends ResponseEntityExceptionHandler {

//    @Override
//    /* Mapeia todas as exceptions Nao sendo de Banco de Dados e direciona para esse metodo */
//    @NonNull @ExceptionHandler(value = {Exception.class, RuntimeException.class, Throwable.class})
//    protected ResponseEntity<Object> handleExceptionInternal(@NonNull Exception exception, Object body,@NonNull HttpHeaders headers,@NonNull HttpStatus status,@NonNull WebRequest request) {
//
//        ExceptionObj exceptionObj = new ExceptionObj();
//        StringBuilder builder = new StringBuilder();
//
//        if (exception instanceof MethodArgumentNotValidException) {
//
//            List<ObjectError> objectErrorList = ((MethodArgumentNotValidException) exception).getBindingResult().getAllErrors();
//            objectErrorList.forEach(objectError -> builder.append(objectError.getDefaultMessage()).append("\n"));
//        } else {
//            builder.append(exception.getMessage());
//        }
//
//
//        /* Codigo do status e a razao*/
//        exceptionObj.setCode(status.value() + " ==> " + status.getReasonPhrase());
//        exceptionObj.setError(builder.toString());
//
//        return new ResponseEntity<>(exceptionObj,headers, status);
//    }

    /* Mapeia Todas as exceptions com erros de Banco de Dados*/
//    @ExceptionHandler(value = {DataIntegrityViolationException.class, ConstraintViolationException.class, SQLException.class})
//    protected ResponseEntity<Object> handleExceptionDataIntegrity(Exception exception) {
//
//        StringBuilder builder = new StringBuilder();
//        ExceptionObj exceptionObj = new ExceptionObj();
//
//        if (exception instanceof DataIntegrityViolationException) {
//            builder.append(exception.getCause().getCause().getMessage());
//        } else if (exception instanceof ConstraintViolationException) {
//            builder.append(exception.getCause().getCause().getMessage());
//        }  else if (exception instanceof SQLException) {
//            builder.append(exception.getCause().getCause().getMessage());
//        }
//        else {
//            builder.append(exception.getMessage());
//        }
//
//        exceptionObj.setError(builder.toString());
//        exceptionObj.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value() + " ==> " + HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
//        return new ResponseEntity<>(exceptionObj, HttpStatus.INTERNAL_SERVER_ERROR);
//    }
}
