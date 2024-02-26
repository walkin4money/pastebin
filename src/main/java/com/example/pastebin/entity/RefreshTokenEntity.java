package com.example.pastebin.entity;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name="refresh_token")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RefreshTokenEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "token")
    private String token;

    @Column(name = "expiry_date")
    private LocalDateTime expiryDate;

    @OneToOne
    @JoinColumn(name = "user_id")
    UserEntity user;
}
