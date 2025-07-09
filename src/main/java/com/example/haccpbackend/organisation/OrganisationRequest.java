package com.example.haccpbackend.organisation;

public class OrganisationRequest {




    private String name;
    private Long organisationId;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getOrganisationId() {
        return organisationId;
    }

    public void setOrganisationId(Long organisationId) {
        this.organisationId = organisationId;
    }


    public OrganisationRequest(String name, Long organisationId) {
        this.name = name;
        this.organisationId = organisationId;
    }

    public OrganisationRequest() {
    }
}
