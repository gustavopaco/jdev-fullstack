package com.pacoprojects.util;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.server.ResponseStatusException;

@Service
public class BeanValidator {

    public void validate(BindingResult bindingResult) {
        StringBuilder builder = new StringBuilder();
        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach( objectError -> builder.append(objectError.getDefaultMessage()).append("\n"));
            String value = builder.substring(0, builder.length() -1);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, value);
        }
    }
}
