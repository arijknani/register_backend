package com.example.register.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "token")
public class ConfirmationToken {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "token", nullable = false, length = 255)
    private String token;

    @Column(name = "createAt", nullable = false, length = 20)
    private LocalDateTime createAt;

    @Column(name = "expiresAt", nullable = false, length = 20)
    private LocalDateTime expiresAt;

    @Column(name = "confirmedAt", nullable = false, length = 20)
    private LocalDateTime confirmedAt;

    @ManyToOne
    @JoinColumn(name = "app_user_id", nullable = false)
    private User user;

    public ConfirmationToken(String token,
                             LocalDateTime createAt,
                             LocalDateTime expiredAt,
                             User user
                             ) {
        this.token = token;
        this.createAt = createAt;
        this.expiresAt = expiredAt;
        this.user = user;
        this.confirmedAt = LocalDateTime.now();
    }
}
