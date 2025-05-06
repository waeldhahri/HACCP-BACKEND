package com.example.haccpbackend.modulPlanning;


import com.example.haccpbackend.etiquetteProduit.CategorieProduit;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class Planning {


    public Planning(Long id, String tache, PlanningCategorie planningCategorie, LocalDateTime createdAt, LocalDate createdDay, LocalTime createdTime) {
        this.id = id;
        this.tache = tache;
        this.planningCategorie = planningCategorie;
        this.createdAt = createdAt;
        this.createdDay = createdDay;
        this.createdTime = createdTime;
    }


    public Planning() {
    }





    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(unique = true )
    private String tache;


    @ManyToOne()
    @JoinColumn(nullable = false , name = "categorie_id")
    private PlanningCategorie planningCategorie;






    @CreatedDate
    @JsonFormat(pattern = "HH:mm:ss")
    @Column(name = "createdAt")
    private LocalDateTime createdAt;

    @Column(name = "createdDay")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate createdDay;

    @Column(name = "createdTime")
    @JsonFormat(pattern = "HH:mm")
    private LocalTime createdTime;

    @PrePersist
    @PreUpdate
    private void updateDateAndTime() {
        if (createdAt != null) {
            this.createdDay = createdAt.toLocalDate();
            this.createdTime = createdAt.toLocalTime().withNano(0);;
        }
    }




    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTache() {
        return tache;
    }

    public void setTache(String tache) {
        this.tache = tache;
    }

    public PlanningCategorie getPlanningCategorie() {
        return planningCategorie;
    }

    public void setPlanningCategorie(PlanningCategorie planningCategorie) {
        this.planningCategorie = planningCategorie;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDate getCreatedDay() {
        return createdDay;
    }

    public void setCreatedDay(LocalDate createdDay) {
        this.createdDay = createdDay;
    }

    public LocalTime getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(LocalTime createdTime) {
        this.createdTime = createdTime;
    }


}
