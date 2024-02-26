package com.example.pastebin.exception;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

@Component
public class ExceptionFilter extends OncePerRequestFilter {

    private final HandlerExceptionResolver handlerExceptionResolver;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        try{
            doFilter(request,response,filterChain);
        }
        catch (ExpiredJwtException expiredJwtException){
            handlerExceptionResolver.resolveException(request,response,null,expiredJwtException);
        }
        catch (SignatureException signatureException){
            handlerExceptionResolver.resolveException(request,response,null,signatureException);
        }
        catch (MalformedJwtException malformedJwtException){
            handlerExceptionResolver.resolveException(request,response,null,malformedJwtException);
        }

    }

    @Autowired
    public ExceptionFilter(HandlerExceptionResolver handlerExceptionResolver) {
        this.handlerExceptionResolver = handlerExceptionResolver;
    }

}
