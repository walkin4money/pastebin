package com.example.pastebin.security;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.security.Principal;

@AllArgsConstructor
public class UserPrincipal implements Principal {
    private String username;

    @Getter
    private Integer id;

    @Override
    public String getName() {
        return username;
    }

}
