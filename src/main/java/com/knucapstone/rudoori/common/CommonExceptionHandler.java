package com.knucapstone.rudoori.common;

import com.knucapstone.rudoori.common.ApiResponse;
import com.sun.jdi.request.DuplicateRequestException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackages = {"com.knucapstone.rudoori"})
public class CommonExceptionHandler {
    @ExceptionHandler({DuplicateKeyException.class, DuplicateRequestException.class})
    public ResponseEntity<ApiResponse<?>> handleDuplicatedUserException(RuntimeException exception) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ApiResponse.createError(exception.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<?>> handleValidationExceptions(BindingResult bindingResult) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.createFail(bindingResult));
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<ApiResponse<?>> handleNullPointerException(RuntimeException exception){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.createError("값을 찾을 수 없습니다."));
    }
}
