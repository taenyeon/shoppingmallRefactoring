package com.shop.myapp.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletResponse;

@RestControllerAdvice
@Slf4j
public class ExceptionAdvice {
    @ExceptionHandler(IllegalStateException.class)
    public String illegalStateExceptionAdvice(IllegalStateException e, HttpServletResponse response){
        log.info("오류 : {}", e.getMessage());
        response.setContentType("text/html; charset=utf-8");
        return "<script>alert('" + e.getMessage() + "'); history.go(-1); </script>";
    }
}
