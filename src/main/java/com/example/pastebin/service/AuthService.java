package com.example.pastebin.service;

import com.example.pastebin.dto.AuthRequestDTO;

import java.util.Map;

public interface AuthService {
    void auth(AuthRequestDTO jwtRequestDTO);
    String logout();
}
