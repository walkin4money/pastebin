package com.example.pastebin.security;

import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;


@Getter
public enum UserRole {
    USER(Set.of(UserPermission.READ,UserPermission.WRITE));
    ;
    private final Set<UserPermission> permissions;

    UserRole(Set<UserPermission> permissions){
        this.permissions = permissions;
    }
   public Set<SimpleGrantedAuthority> getAuthority(){
        return getPermissions().stream().map(p->new SimpleGrantedAuthority(p.getPermission())).collect(Collectors.toSet());
   }
}
