package com.example.pastebin.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class JwtResponseDTO {
    private String accessToken;
    private String refreshToken;
}
