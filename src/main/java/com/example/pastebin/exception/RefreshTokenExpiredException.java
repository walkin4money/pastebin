package com.example.pastebin.exception;


import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class RefreshTokenExpiredException extends RuntimeException{
    private String errorName;
    private String errorMessage;
}
