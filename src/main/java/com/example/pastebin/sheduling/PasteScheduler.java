package com.example.pastebin.sheduling;


import com.example.pastebin.repository.PasteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Component
public class PasteScheduler {
    private final PasteRepository pasteRepository;

    @Scheduled(fixedDelay = 600000)
    @Transactional
    public void deleteExpiredPastes() {
        pasteRepository.deletePasteEntityByExpirationTimeBefore(LocalDateTime.now());
    }

    @Autowired
    public PasteScheduler(PasteRepository pasteRepository) {
        this.pasteRepository = pasteRepository;
    }
}
