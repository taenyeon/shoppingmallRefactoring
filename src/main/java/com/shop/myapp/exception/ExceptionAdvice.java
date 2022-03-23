package com.shop.myapp.exception;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class ExceptionAdvice {

    @ExceptionHandler(IllegalStateException.class)
    public String illegalStateExceptionAdvice(IllegalStateException e, HttpServletResponse response){
        log.info("오류 : {}", e.getMessage());
        response.setContentType("text/html; charset=utf-8");
        return "<script>alert('" + e.getMessage() + "'); history.go(-1); </script>";
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<Object> methodArgumentNotValidExceptionAdvice(BindException e){
        BindingResult bindingResult = e.getBindingResult();
        Map<String,String> errors = new HashMap<>();
        for (FieldError fieldError : bindingResult.getFieldErrors()){
            log.info("오류 : {}", fieldError.getField());
            errors.put(fieldError.getField(),fieldError.getDefaultMessage());
        }
        return ResponseEntity.status(400).body(errors);
    }
}
