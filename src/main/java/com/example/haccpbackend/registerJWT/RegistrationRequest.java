package com.example.haccpbackend.registerJWT;


import com.example.haccpbackend.organisation.Organisation;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Lob;
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

    @JsonIgnore
    @Lob
    private byte[] imageOfUser;


    private String imageUrl ;

    @NotEmpty(message = "ROLE cannot be empty")
    @NotBlank
    @NotNull
    private Organisation organisation;



    public byte[] getImageOfUser() {
        return imageOfUser;
    }

    public void setImageOfUser(byte[] imageOfUser) {
        this.imageOfUser = imageOfUser;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

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

    public Organisation getOrganisation() {
        return organisation;
    }

    public void setOrganisation(Organisation organisation) {
        this.organisation = organisation;
    }

    public RegistrationRequest() {

    }


    public RegistrationRequest(String fullname, String email, String motdepasse, String role, byte[] imageOfUser, String imageUrl, Organisation organisation) {
        this.fullname = fullname;
        this.email = email;
        this.motdepasse = motdepasse;
        this.role = role;
        this.imageOfUser = imageOfUser;
        this.imageUrl = imageUrl;
        this.organisation = organisation;
    }
}
