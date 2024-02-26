package com.example.pastebin.repository;

import com.example.pastebin.entity.PasteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface PasteRepository extends JpaRepository<PasteEntity,String> {
    void deletePasteEntityByExpirationTimeBefore(LocalDateTime localDateTime);
}
