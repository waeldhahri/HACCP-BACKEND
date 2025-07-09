package com.example.haccpbackend.organisation;

import com.example.haccpbackend.modulUsers.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.List;


@Entity
public class Organisation {




    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(unique = true)
    private String name;



    @Lob
    @JsonIgnore
    private byte[] image;


    private String imageUrl ;



    // Relation avec User
    @OneToMany(mappedBy = "organisation", cascade = CascadeType.ALL , orphanRemoval = true )
    @JsonManagedReference
    private List<User> users;


    public Organisation() {
    }


    public Organisation(Long id, String name, byte[] image, String imageUrl, List<User> users) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.imageUrl = imageUrl;
        this.users = users;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }


}
