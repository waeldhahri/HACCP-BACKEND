package com.example.haccpbackend.modulUsers;

import com.example.haccpbackend.organisation.Organisation;

public class UserDto {



        private Long id;
        private String fullname;
        private String email;
        private String role;

        private Long organisationID;

        // Constructeurs, getters, setters


    public UserDto(Long id, String fullname, String email, String role, Long organisationID) {
        this.id = id;
        this.fullname = fullname;
        this.email = email;
        this.role = role;
        this.organisationID = organisationID;
    }

    public UserDto() {
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Long getOrganisationID() {
        return organisationID;
    }

    public void setOrganisationID(Long organisationID) {
        this.organisationID = organisationID;
    }
}
