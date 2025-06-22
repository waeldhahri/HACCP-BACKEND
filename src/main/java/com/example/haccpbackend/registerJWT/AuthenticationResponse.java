package com.example.haccpbackend.registerJWT;


import com.example.haccpbackend.modulUsers.UserDto;
import lombok.Builder;


public class AuthenticationResponse {




    private String token;

    private UserDto user;


    // Constructeurs
    public AuthenticationResponse() {
    }

    public AuthenticationResponse(String token , UserDto user) {
        this.token = token;
        this.user = user;
    }

    // Getter
    public String getToken() {
        return token;
    }

    // Setter
    public void setToken(String token) {
        this.token = token;
    }


    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;

    }

    // Builder manuel
    public static class AuthenticationResponseBuilder {
        private String token;
        private UserDto user;

        public AuthenticationResponseBuilder token(String token) {
            this.token = token;
            return this;
        }

        public AuthenticationResponseBuilder user(UserDto user) {
            this.user = user;
            return this;
        }

        public AuthenticationResponse build() {
            return new AuthenticationResponse(token , user);
        }
    }

    public static AuthenticationResponseBuilder builder() {
        return new AuthenticationResponseBuilder();
    }
}










