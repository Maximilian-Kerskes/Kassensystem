package com.kassensystem.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Map;

@Controller
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public ResponseEntity<Map<String, Object>> handleError(HttpServletRequest request) {
        Object statusCode = request.getAttribute("jakarta.servlet.error.status_code");
        Object message = request.getAttribute("jakarta.servlet.error.message");

        int status = (statusCode != null) ? Integer.parseInt(statusCode.toString()) : 500;

        Map<String, Object> errorDetails = new HashMap<>();
        errorDetails.put("status", status);
        errorDetails.put("error", HttpStatus.valueOf(status).getReasonPhrase());
        errorDetails.put("message", message != null ? message : "Unexpected error");
        errorDetails.put("path", request.getAttribute("jakarta.servlet.error.request_uri"));

        return new ResponseEntity<>(errorDetails, HttpStatus.valueOf(status));
    }

    // Optional (Spring Boot 2.3+ doesn't need this, but harmless)
    public String getErrorPath() {
        return "/error";
    }
}
