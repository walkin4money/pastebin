package com.example.pastebin.exception;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ExceptionHandlerRestController {
    @ExceptionHandler(PasteNotFoundException.class)
    public ResponseEntity<Map<String,Object>> pasteNotFoundExceptionHandler(PasteNotFoundException pasteNotFoundException){
        Map<String,Object> body = new HashMap<>();
        body.put("error_name:",pasteNotFoundException.getErrorName());
        body.put("error_message:",pasteNotFoundException.getErrorMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Map<String,Object>> userNotFoundExceptionHandler(UserNotFoundException userNotFoundException){
        Map<String,Object> body = new HashMap<>();
        body.put("error_name:",userNotFoundException.getErrorName());
        body.put("error_message:",userNotFoundException.getErrorMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }
    @ExceptionHandler(RefreshTokenExpiredException.class)
    public ResponseEntity<Map<String,Object>> refreshTokenExpiredExceptionHandler(RefreshTokenExpiredException refreshTokenExpiredException){
        Map<String,Object> body = new HashMap<>();
        body.put("error_name:",refreshTokenExpiredException.getErrorName());
        body.put("error_message:",refreshTokenExpiredException.getErrorMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(body);
    }

    @ExceptionHandler(RefreshTokenNotFoundException.class)
    public ResponseEntity<Map<String,Object>> refreshTokenNotFoundExceptionHandler(RefreshTokenNotFoundException refreshTokenNotFoundException){
        Map<String,Object> body = new HashMap<>();
        body.put("error_name:",refreshTokenNotFoundException.getErrorName());
        body.put("error_message:",refreshTokenNotFoundException.getErrorMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<Map<String,Object>> expiredJwtExceptionHandler(ExpiredJwtException expiredJwtException){
        Map<String,Object> responseBody = new HashMap<>();
        responseBody.put("error_name","The access token has expired");
        responseBody.put("error_message","Your access token has expired");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseBody);
    }

    @ExceptionHandler(MalformedJwtException.class)
    public ResponseEntity<Map<String,Object>> malformedJwtExceptionHandler(MalformedJwtException malformedJwtException){
        Map<String,Object> responseBody = new HashMap<>();
        responseBody.put("error_name","Incorrect access token jwt format");
        responseBody.put("error_message","Your access token has incorrect payload or headers");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseBody);
    }

    @ExceptionHandler(io.jsonwebtoken.security.SignatureException.class)
    public ResponseEntity<Map<String,Object>> signatureExceptionHandler(io.jsonwebtoken.security.SignatureException signatureException){
        Map<String,Object> responseBody = new HashMap<>();
        responseBody.put("error_name","The access token jwt signature is corrupted");
        responseBody.put("error_message","Jwt the signature of your access token is corrupted");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseBody);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Map<String,Object>> handleIBadCredentialsException(BadCredentialsException badCredentialsException){
        Map<String,Object> response = new HashMap<>();
        response.put("error_name","Incorrect credentials");
        response.put("error_message", "Incorrect login or password");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }
}
