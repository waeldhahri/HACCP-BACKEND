package com.example.haccpbackend.modulUsers;


import com.example.haccpbackend.controleReception.Product;

import com.example.haccpbackend.organisation.Organisation;
import com.example.haccpbackend.registerJWT.Token;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;


@Entity
@Table(name = "users")
@EntityListeners(AuditingEntityListener.class)
public class User implements UserDetails , Principal {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "User_id", nullable = false )
    private Long id;

    @NotNull
    @NotEmpty
    @NotBlank
    private String fullName;

    @Email(message = "Please enter a valid email address")
    @Column(nullable = false, unique = true)
    private String email;

    @Column(name = "motdepasse" , updatable = true , nullable = false )
    private String motdepasse;





    @OneToMany(mappedBy = "users" )
    private List<Product> products;


    @OneToMany(mappedBy = "userToken" , cascade = CascadeType.ALL , orphanRemoval = true)
    @JsonIgnore
    private List<Token> tokens = new ArrayList<>();


    @Column(name = "imageOfUser")
    @Lob
    @JsonIgnore
    private byte[] imageOfUser;

    @Column(name = "imageUrl")
    private String imageUrl ;

    private boolean enabled=true;
    private boolean accountLocked;



    @Enumerated(EnumType.STRING)
    @NotNull
    @Column(nullable = false)
    private Role role;



    @ManyToOne
    @JoinColumn(name = "organisation_id")
    @JsonBackReference
    private Organisation organisation;




    @Column(unique = true)
    private String resetToken; // Token pour la réinitialisation du mot de passe

    public String getResetToken() {
        return resetToken;
    }

    public void setResetToken(String resetToken) {
        this.resetToken = resetToken;
    }



    public User() {
    }


    public User(Long id, String fullName, String email, String motdepasse, List<Product> products, List<Token> tokens, byte[] imageOfUser,
                String imageUrl, boolean enabled, boolean accountLocked, Role role, Organisation organisation, String resetToken) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.motdepasse = motdepasse;
        this.products = products;
        this.tokens = tokens;
        this.imageOfUser = imageOfUser;
        this.imageUrl = imageUrl;
        this.enabled = enabled;
        this.accountLocked = accountLocked;
        this.role = role;
        this.organisation = organisation;
        this.resetToken = resetToken;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public byte[] getImageOfUser() {
        return imageOfUser;
    }

    public void setImageOfUser(byte[] imageOfUser) {
        this.imageOfUser = imageOfUser;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
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

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isAccountLocked() {
        return accountLocked;
    }

    public void setAccountLocked(boolean accountLocked) {
        this.accountLocked = accountLocked;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }


    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public List<Token> getTokens() {
        return tokens;
    }

    public void setTokens(List<Token> tokens) {
        this.tokens = tokens;
    }


    public Organisation getOrganisation() {
        return organisation;
    }

    public void setOrganisation(Organisation organisation) {
        this.organisation = organisation;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return List.of(new SimpleGrantedAuthority(this.role.name()));

    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !accountLocked;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }


    @Override
    public String getName() {
        return email;
    }
    @Override
    public String getPassword() {
        return motdepasse;
    }


}
