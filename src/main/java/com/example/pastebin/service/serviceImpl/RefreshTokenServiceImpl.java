package com.example.pastebin.service.serviceImpl;

import com.example.pastebin.entity.RefreshTokenEntity;
import com.example.pastebin.exception.RefreshTokenExpiredException;
import com.example.pastebin.exception.UserNotFoundException;
import com.example.pastebin.repository.RefreshTokenRepository;
import com.example.pastebin.repository.UserRepository;
import com.example.pastebin.security.CustomUserDetailsService;
import com.example.pastebin.security.SecurityUser;
import com.example.pastebin.service.RefreshTokenService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenServiceImpl implements RefreshTokenService {
    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final CustomUserDetailsService customUserDetailsService;
    public RefreshTokenEntity generateRefreshToken(String username){
        SecurityUser securityUser = (SecurityUser) customUserDetailsService.loadUserByUsername(username);
       RefreshTokenEntity refreshTokenEntity =  RefreshTokenEntity.builder()
               .user(userRepository.findById(securityUser.getId()).orElseThrow(()->new UserNotFoundException("User not found",String.format("User with id %d was not found",securityUser.getId()))))
               .token(UUID.randomUUID().toString())
               .expiryDate(LocalDateTime.now().plusDays(30))
               .build();
       refreshTokenRepository.save(refreshTokenEntity);
       return refreshTokenEntity;
    }


    public Optional<RefreshTokenEntity> getRefreshTokenEntityByToken(String refreshToken){
        return refreshTokenRepository.getRefreshTokenEntityByToken(refreshToken);
    }

    public RefreshTokenEntity validateRefreshToken(RefreshTokenEntity refreshTokenEntity) {
        if(refreshTokenEntity.getExpiryDate().isBefore(LocalDateTime.now())){
            refreshTokenRepository.delete(refreshTokenEntity);
            throw new RefreshTokenExpiredException("Refresh token expired",String.format("Your refresh token %s has expired",refreshTokenEntity.getToken()));
        }
        return refreshTokenEntity;
    }

    public RefreshTokenServiceImpl(UserRepository userRepository, RefreshTokenRepository refreshTokenRepository, CustomUserDetailsService customUserDetailsService){
        this.userRepository = userRepository;
        this.refreshTokenRepository = refreshTokenRepository;
        this.customUserDetailsService = customUserDetailsService;
    }

}
