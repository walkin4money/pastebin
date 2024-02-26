package com.example.pastebin.controller;

import com.example.pastebin.dto.AuthRequestDTO;
import com.example.pastebin.dto.JwtResponseDTO;
import com.example.pastebin.dto.RefreshTokenRequestDTO;
import com.example.pastebin.entity.RefreshTokenEntity;
import com.example.pastebin.entity.UserEntity;
import com.example.pastebin.exception.RefreshTokenNotFoundException;
import com.example.pastebin.service.AuthService;
import com.example.pastebin.service.JwtTokenService;
import com.example.pastebin.service.RefreshTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/auth/")
@CrossOrigin
public class AuthController {
    private final AuthService authService;
    private final JwtTokenService jwtTokenService;
    private final RefreshTokenService refreshTokenService;

    @PostMapping("login")
    public ResponseEntity<JwtResponseDTO> login(@RequestBody AuthRequestDTO authRequestDTO){
        authService.auth(authRequestDTO);
      RefreshTokenEntity  refreshTokenEntity = refreshTokenService.generateRefreshToken(authRequestDTO.getUsername());
      String accessToken = jwtTokenService.generateToken(authRequestDTO.getUsername());
      return ResponseEntity.ok(JwtResponseDTO.builder()
              .accessToken(accessToken)
              .refreshToken(refreshTokenEntity.getToken())
              .build()
      );
    }

    @PostMapping("refreshToken")
    public ResponseEntity<JwtResponseDTO> refreshToken(@RequestBody RefreshTokenRequestDTO refreshTokenRequestDTO){
        return refreshTokenService.getRefreshTokenEntityByToken(refreshTokenRequestDTO.getRefreshToken())
                .map(refreshTokenService::validateRefreshToken)
                .map(RefreshTokenEntity::getUser)
                .map(userEntity->{
                    String accessToken = jwtTokenService.generateToken(userEntity.getUsername());
                    return ResponseEntity.ok(JwtResponseDTO.builder()
                            .accessToken(accessToken)
                            .refreshToken(refreshTokenRequestDTO.getRefreshToken())
                            .build()
                    );
                }).orElseThrow(()->new RefreshTokenNotFoundException("The refresh token is not in the database.","The refresh token you provided does not exist in the database."));
    }

    @GetMapping("logout")
    public ResponseEntity<String> logout(){
        return ResponseEntity.ok(authService.logout());
    }


    @Autowired
    public AuthController(AuthService authService,JwtTokenService jwtTokenService,RefreshTokenService refreshTokenService) {
        this.authService = authService;
        this.jwtTokenService = jwtTokenService;
        this.refreshTokenService = refreshTokenService;
    }
}
