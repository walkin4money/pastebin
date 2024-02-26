package com.example.pastebin.service.serviceImpl;


import com.example.pastebin.security.CustomUserDetailsService;
import com.example.pastebin.security.SecurityUser;
import com.example.pastebin.security.UserPrincipal;
import com.example.pastebin.security.UserRole;
import com.example.pastebin.service.JwtTokenService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Service
public class JwtTokenServiceImpl implements JwtTokenService {

    @Value("${jwt.lifetime}")
    private Duration lifetime;
    @Value("${jwt.secretKey}")
    private String secretKey;

    private final CustomUserDetailsService customUserDetailsService;
    public String generateToken(String username){
        SecurityUser securityUser = (SecurityUser) customUserDetailsService.loadUserByUsername(username);
        Date issuedDate = new Date();
        Date expiredDate = new Date(issuedDate.getTime() + lifetime.toMillis());
        Map<String,Object> claims = new HashMap<>();
        claims.put("role",securityUser.getUserRole());
        claims.put("id",securityUser.getId());
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(securityUser.getUsername())
                .setExpiration(expiredDate)
                .setIssuedAt(issuedDate)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public String getTokenFromRequest(HttpServletRequest request){
        String authorizationHeader = request.getHeader("Authorization");;
        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer")){
            return authorizationHeader.substring(7);
        }
        return null;
    }

    public UserPrincipal getPrincipalsFromToken(String token){
         Claims claims = getClaimsFromToken(token);
        return new UserPrincipal(
                claims.getSubject(),
                claims.get("id",Integer.class)
        );
    }

    public Set<SimpleGrantedAuthority> getAuthoritiesFromToken(String token){
        return UserRole.valueOf(getClaimsFromToken(token).get("role", String.class)).getAuthority();
    }
    public Authentication createAuthenticationFromToken(String token){
        return new UsernamePasswordAuthenticationToken(
                getPrincipalsFromToken(token),
                null,
                getAuthoritiesFromToken(token)
        );
    }
    public Claims getClaimsFromToken(String token){
        return Jwts.parser().setSigningKey(secretKey)
                .parseClaimsJws(token).getBody();
    }

    public JwtTokenServiceImpl(CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }
}
