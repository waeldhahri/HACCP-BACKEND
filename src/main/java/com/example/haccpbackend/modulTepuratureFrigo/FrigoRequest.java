package com.example.haccpbackend.modulTepuratureFrigo;

public class FrigoRequest {

    private String name;
    private Long categorieId;

    private String categorie;

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public FrigoRequest(String name, Long categorieId , String categorie) {
        this.name = name;
        this.categorieId = categorieId;
        this.categorie=categorie;
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
}
