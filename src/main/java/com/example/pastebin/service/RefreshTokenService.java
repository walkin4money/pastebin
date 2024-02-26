package com.example.pastebin.service;

import com.example.pastebin.entity.RefreshTokenEntity;
import com.example.pastebin.security.SecurityUser;

import javax.security.auth.RefreshFailedException;
import java.util.Optional;

public interface RefreshTokenService {
    RefreshTokenEntity generateRefreshToken(String username);
    RefreshTokenEntity validateRefreshToken(RefreshTokenEntity refreshTokenEntity);
    Optional<RefreshTokenEntity> getRefreshTokenEntityByToken(String refreshToken);
}
