package com.example.pastebin.dto;

import com.example.pastebin.entity.TypePaste;
import lombok.Data;

import java.time.Duration;
import java.time.LocalDateTime;

@Data
public class PasteRequestDTO {
    private String title;
    private String content;
    private TypePaste typePaste;
    private Duration lifetime;
}
