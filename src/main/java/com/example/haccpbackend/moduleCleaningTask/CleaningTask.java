package com.example.haccpbackend.moduleCleaningTask;


import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class CleaningTask {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String location;
    private String description;

    private String responsible;
    private String status;
    private LocalDateTime taskDate;


    @Lob
    private byte[] beforeImage;

    @Lob
    private byte[] afterImage;


    public CleaningTask(Long id, String location, String description, String responsible, String status, LocalDateTime taskDate, byte[] beforeImage, byte[] afterImage) {
        this.id = id;
        this.location = location;
        this.description = description;
        this.responsible = responsible;
        this.status = status;
        this.taskDate = taskDate;
        this.beforeImage = beforeImage;
        this.afterImage = afterImage;
    }


    public CleaningTask() {
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getResponsible() {
        return responsible;
    }

    public void setResponsible(String responsible) {
        this.responsible = responsible;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getTaskDate() {
        return taskDate;
    }

    public void setTaskDate(LocalDateTime taskDate) {
        this.taskDate = taskDate;
    }

    public byte[] getBeforeImage() {
        return beforeImage;
    }

    public void setBeforeImage(byte[] beforeImage) {
        this.beforeImage = beforeImage;
    }

    public byte[] getAfterImage() {
        return afterImage;
    }

    public void setAfterImage(byte[] afterImage) {
        this.afterImage = afterImage;
    }
}
