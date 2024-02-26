package com.example.pastebin.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;


@AllArgsConstructor
@Getter
public class PasteNotFoundException extends RuntimeException{
    private String errorName;
    private String errorMessage;
}
