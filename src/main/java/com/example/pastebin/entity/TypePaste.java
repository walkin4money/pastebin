package com.example.pastebin.entity;

import lombok.Getter;

@Getter
public enum TypePaste {
    PUBLIC("PUBLIC"),
    UNLISTED("UNLISTED");
    private final String type;
    TypePaste(String type) {
        this.type = type;
    }
}
