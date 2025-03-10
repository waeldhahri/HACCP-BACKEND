package com.example.haccpbackend.registerJWT;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;



public class AuthenticationRequest {




    @Email(message = "Email is not well formatted")
    @NotEmpty(message = "Email is mandatory")
    @NotNull(message = "Email is mandatory")
    private String email;

    @NotEmpty(message = "Password is mandatory")
    @NotNull(message = "Password is mandatory")
    @Size(min = 8, message = "Password should be 8 characters long minimum")
    private String motdepasse;



    // Constructeurs
    public AuthenticationRequest() {
    }

    public AuthenticationRequest(String email, String motdepasse) {
        this.email = email;
        this.motdepasse = motdepasse;
    }

    // Getters & Setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getmotdepasse() {
        return motdepasse;
    }

    public void setmotdepasse(String motdepasse) {
        this.motdepasse = motdepasse;
    }

    // Builder manuel
    public static class AuthenticationRequestBuilder {
        private String email;
        private String motdepasse;

        public AuthenticationRequestBuilder email(String email) {
            this.email = email;
            return this;
        }

        public AuthenticationRequestBuilder motdepasse(String motdepasse) {
            this.motdepasse = motdepasse;
            return this;
        }

        public AuthenticationRequest build() {
            return new AuthenticationRequest(email, motdepasse);
        }
    }

    public static AuthenticationRequestBuilder builder() {
        return new AuthenticationRequestBuilder();
    }
}










