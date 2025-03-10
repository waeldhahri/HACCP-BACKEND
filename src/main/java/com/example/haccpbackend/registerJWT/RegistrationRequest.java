package com.example.haccpbackend.registerJWT;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public class RegistrationRequest {


    @NotEmpty(message = "fullname cannot be empty")
    @NotBlank
    @NotNull
    private String fullname;



    @Email(message = "Please enter a valid email address")
    private String email;


    @NotEmpty(message = "motdepasse cannot be empty")
    @NotBlank
    @NotNull
    private String motdepasse;


    @NotEmpty(message = "ROLE cannot be empty")
    @NotBlank
    @NotNull
    private String role ;


    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMotdepasse() {
        return motdepasse;
    }

    public void setMotdepasse(String motdepasse) {
        this.motdepasse = motdepasse;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
