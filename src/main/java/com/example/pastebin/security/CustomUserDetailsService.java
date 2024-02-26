package com.example.pastebin.security;

import com.example.pastebin.entity.UserEntity;
import com.example.pastebin.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {
    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return toUserDetails(userRepository.getUserEntityByUsername(username).orElseThrow(()->new BadCredentialsException("User not found")));
    }

    @Autowired
    public CustomUserDetailsService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    private UserDetails toUserDetails(UserEntity userEntity){
      return new SecurityUser(userEntity.getId(),userEntity.getPassword(),userEntity.getUsername(),userEntity.getRole());
    }

}
