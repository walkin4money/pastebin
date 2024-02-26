package com.example.pastebin.repository;

import com.example.pastebin.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity,Integer> {
    Optional<UserEntity> getUserEntityByUsername(String username);
}
