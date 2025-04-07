package com.example.haccpbackend.registerJWT;

import com.example.haccpbackend.modulUsers.User;
import jakarta.persistence.*;
import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalDateTime;



@Entity
public class Token {




    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;

    @Column(length = 1024)
    private String token;
    private LocalDateTime createdAt;
    private LocalDateTime expiredAt;
    private LocalDateTime validateAt;




    @ManyToOne
    @JoinColumn(name = "user_id")
    private User userToken;


    public Token(Long id, String token, LocalDateTime createdAt, LocalDateTime expiredAt, LocalDateTime validateAt, User userToken) {
        this.id = id;
        this.token = token;
        this.createdAt = createdAt;
        this.expiredAt = expiredAt;
        this.validateAt = validateAt;
        this.userToken = userToken;
    }

    public Token() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getExpiredAt() {
        return expiredAt;
    }

    public void setExpiredAt(LocalDateTime expiredAt) {
        this.expiredAt = expiredAt;
    }

    public LocalDateTime getValidateAt() {
        return validateAt;
    }

    public void setValidateAt(LocalDateTime validateAt) {
        this.validateAt = validateAt;
    }

    public User getUserToken() {
        return userToken;
    }

    public void setUserToken(User userToken) {
        this.userToken = userToken;
    }



    // Builder manuel
    public static class Builder {
        private Long id;
        @Column(length = 1000)
        private String token;
        private LocalDateTime createdAt;
        private LocalDateTime expiredAt;
        private LocalDateTime validateAt;
        private User userToken;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder token(String token) {
            this.token = token;
            return this;
        }

        public Builder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public Builder expiredAt(LocalDateTime expiredAt) {
            this.expiredAt = expiredAt;
            return this;
        }

        public Builder validateAt(LocalDateTime validateAt) {
            this.validateAt = validateAt;
            return this;
        }

        public Builder userToken(User userToken) {
            this.userToken = userToken;
            return this;
        }

        public Token build() {
            return new Token(id, token, createdAt, expiredAt, validateAt, userToken);
        }
    }

    public static Builder builder() {
        return new Builder();
    }
}



