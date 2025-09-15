package com.campus1.manager.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class ManagerGlobalExceptionHandler {
    @ExceptionHandler(ManagerManagerNotFoundException.class)
    public ResponseEntity<Map<String, Object>> ManagerNotFound(ManagerManagerNotFoundException e) {
        Map<String, Object> map = new HashMap<>();
        map.put("message", e.getMessage()); // Use the message, not the exception object
        map.put("statusCode", HttpStatus.NOT_FOUND.value()); // Use 404, not 403
        return new ResponseEntity<>(map, HttpStatus.NOT_FOUND);
    }
    
    @ExceptionHandler(ManagerUsersNotFound.class)
    public ResponseEntity<Map<String, Object>> ManagerUsersNotFound(ManagerUsersNotFound e) {
        Map<String, Object> map = new HashMap<>();
        map.put("message", e.getMessage()); // Use the message, not the exception object
        map.put("statusCode", HttpStatus.NOT_FOUND.value()); // Use 404, not 403
        return new ResponseEntity<>(map, HttpStatus.NOT_FOUND);
    }
}