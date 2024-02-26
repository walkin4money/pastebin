package com.example.pastebin.service.serviceImpl;

import com.example.pastebin.dto.AuthRequestDTO;
import com.example.pastebin.repository.RefreshTokenRepository;
import com.example.pastebin.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {
    private final AuthenticationManager authenticationManager;
    private final RefreshTokenRepository refreshTokenRepository;
    public void auth(AuthRequestDTO jwtRequestDTO){
      authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(jwtRequestDTO.getUsername(),jwtRequestDTO.getPassword()));
    }
    public String logout(){
        SecurityContextHolder.clearContext();
        //refreshTokenRepository.delete();
        return "Logout";
    }

    @Autowired
    public AuthServiceImpl(AuthenticationManager authenticationManager,RefreshTokenRepository refreshTokenRepository){
        this.authenticationManager = authenticationManager;
        this.refreshTokenRepository = refreshTokenRepository;
    }
}
