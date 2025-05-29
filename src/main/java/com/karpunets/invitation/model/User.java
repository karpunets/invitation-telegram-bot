package com.karpunets.invitation.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "telegram_users", indexes = {
        @Index(name = "idx_telegram_id", columnList = "telegramId", unique = true)
})
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private Long telegramId;

    private String username;

    private String firstName;

    private String lastName;

    private String languageCode;

    private Boolean isBot;

    private LocalDateTime createdAt;

    private String state;
}
