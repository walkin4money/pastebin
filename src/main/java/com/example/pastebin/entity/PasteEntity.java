package com.example.pastebin.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "pastes")
public class PasteEntity {
    @Id
    private String hash;

    @Column(name="title")
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "type_paste")
    @Enumerated(EnumType.STRING)
    private TypePaste typePaste;

    @Column(name="expiration_time")
    private LocalDateTime expirationTime;

    @ManyToOne
    @JoinColumn(name="user_id")
    @JsonBackReference
    UserEntity user;
}
