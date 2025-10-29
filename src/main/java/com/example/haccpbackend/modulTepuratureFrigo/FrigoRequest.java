package com.example.haccpbackend.modulTepuratureFrigo;

public class FrigoRequest {

    private String name;
    private Long categorieId;

    private String categorie;

    private boolean active;

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }


    public FrigoRequest(String name, Long categorieId, String categorie, boolean active) {
        this.name = name;
        this.categorieId = categorieId;
        this.categorie = categorie;
        this.active = active;
    }

    public Long getCategorieId() {
        return categorieId;
    }

    public void setCategorieId(Long categorieId) {
        this.categorieId = categorieId;
    }

    public FrigoRequest() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
