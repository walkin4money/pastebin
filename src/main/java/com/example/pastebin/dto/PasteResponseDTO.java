package com.example.pastebin.dto;


import com.example.pastebin.entity.TypePaste;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;


@Data
public class PasteResponseDTO {
    private String url;
    private String title;
    private String content;
    private TypePaste typePaste;
    private Long lifetimeMinutes;
}
