package com.example.pastebin.security;


import com.example.pastebin.service.JwtTokenService;
import com.example.pastebin.service.serviceImpl.JwtTokenServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    JwtTokenService jwtTokenService;
    @Override
    protected void doFilterInternal( HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = jwtTokenService.getTokenFromRequest(request);
        if(token != null && SecurityContextHolder.getContext().getAuthentication() == null){
            SecurityContextHolder.getContext().setAuthentication(jwtTokenService.createAuthenticationFromToken(token));
        }
        filterChain.doFilter(request,response);
    }

    @Autowired
    public JwtAuthenticationFilter(JwtTokenService jwtTokenService) {
        this.jwtTokenService = jwtTokenService;
    }
}
