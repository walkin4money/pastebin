package com.example.pastebin.service;

import com.example.pastebin.security.SecurityUser;
import com.example.pastebin.security.UserPrincipal;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;

public interface JwtTokenService {
    String generateToken(String username);
    String getTokenFromRequest(HttpServletRequest request);
    UserPrincipal getPrincipalsFromToken(String token);
    Set<SimpleGrantedAuthority> getAuthoritiesFromToken(String token);
    Authentication createAuthenticationFromToken(String token);
    Claims getClaimsFromToken(String token);

}
