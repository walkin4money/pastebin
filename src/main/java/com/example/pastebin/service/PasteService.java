package com.example.pastebin.service;

import com.example.pastebin.dto.PasteRequestDTO;
import com.example.pastebin.dto.PasteResponseDTO;
import com.example.pastebin.dto.PasteUrlResponseDTO;
import com.example.pastebin.entity.PasteEntity;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface PasteService {
    List<PasteResponseDTO> getAllPublicPaste(String title);
    List<PasteResponseDTO> getAllUserPaste(Integer userId);
    PasteResponseDTO getOnePaste(String hash);
    PasteUrlResponseDTO createPaste(PasteRequestDTO pasteDTO,Integer userId);
}
