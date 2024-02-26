package com.example.pastebin.controller;

import com.example.pastebin.dto.PasteRequestDTO;
import com.example.pastebin.dto.PasteResponseDTO;
import com.example.pastebin.dto.PasteUrlResponseDTO;
import com.example.pastebin.entity.PasteEntity;
import com.example.pastebin.security.UserPrincipal;
import com.example.pastebin.service.PasteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.net.URLEncoder;
import java.util.List;

@RestController
@RequestMapping("api/")
@CrossOrigin
public class PasteController {
    private final PasteService pasteService;

    @GetMapping("public-pastes")
    public ResponseEntity<List<PasteResponseDTO>> getAllPublicPaste(
            @RequestParam(name = "title",required = false) String title
    ){
        return ResponseEntity.ok(pasteService.getAllPublicPaste(title));
    }

    @GetMapping("pastes")
    public ResponseEntity<List<PasteResponseDTO>> getAllUserPaste(@AuthenticationPrincipal UserPrincipal userPrincipal){
       return ResponseEntity.ok(pasteService.getAllUserPaste(userPrincipal.getId()));
    }

    @GetMapping("{hash}")
    public ResponseEntity<PasteResponseDTO> getOnePaste(@PathVariable String hash){
      return ResponseEntity.ok(pasteService.getOnePaste(hash));
    }

    @PostMapping("pastes")
    public ResponseEntity<PasteUrlResponseDTO> createPaste(@RequestBody PasteRequestDTO pasteRequestDTO,@AuthenticationPrincipal UserPrincipal userPrincipal){
       return ResponseEntity.ok(pasteService.createPaste(pasteRequestDTO,userPrincipal.getId()));
    }

    @Autowired
    public PasteController(PasteService pasteService) {
        this.pasteService = pasteService;
    }
}
