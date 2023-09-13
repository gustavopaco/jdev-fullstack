package com.pacoprojects.exception;

import lombok.Builder;

@Builder
public record ExceptionObject(String message, Integer code) {

}
