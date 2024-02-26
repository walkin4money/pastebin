package com.example.pastebin.security;

import lombok.Getter;

@Getter
public enum UserPermission {
    WRITE("user:write"),
    READ("user:read");

    private final String permission;
    UserPermission(String permission){
        this.permission = permission;
    }

}
