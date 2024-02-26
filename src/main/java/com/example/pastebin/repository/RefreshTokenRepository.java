package com.example.pastebin.repository;

import com.example.pastebin.entity.RefreshTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshTokenEntity,Integer> {
    Optional<RefreshTokenEntity> getRefreshTokenEntityByToken(String token);
}
