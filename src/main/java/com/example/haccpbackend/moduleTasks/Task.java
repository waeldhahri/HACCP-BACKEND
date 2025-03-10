package com.example.haccpbackend.moduleTasks;

import com.example.haccpbackend.modulUsers.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalTime;



@Entity
@Table(name = "tasks")
public class Task {




    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;

    @NotNull
    @NotEmpty
    private String name = " A faire " ;


    private String status ;


    @Column(updatable = false)
    private LocalDate dateOfDone;

    @Column(updatable = false)
    private LocalTime timeOfDone;


    @Enumerated(EnumType.STRING)
    private TaskMomentDone taskMomentDone;



    private String note ;



    @JsonIgnore
    @Lob
    private byte[] imageOfTask;


    public Task() {
    }

    @ManyToOne
    @JoinColumn(name = "category_id")
    private TaskCategory taskCategory;


    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getDateOfDone() {
        return dateOfDone;
    }

    public void setDateOfDone(LocalDate dateOfDone) {
        this.dateOfDone = dateOfDone;
    }

    public LocalTime getTimeOfDone() {
        return timeOfDone;
    }

    public void setTimeOfDone(LocalTime timeOfDone) {
        this.timeOfDone = timeOfDone;
    }

    public TaskCategory getTaskCategory() {
        return taskCategory;
    }

    public void setTaskCategory(TaskCategory taskCategory) {
        this.taskCategory = taskCategory;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


    public TaskMomentDone getTaskMomentDone() {
        return taskMomentDone;
    }

    public void setTaskMomentDone(TaskMomentDone taskMomentDone) {
        this.taskMomentDone = taskMomentDone;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public byte[] getImageOfTask() {
        return imageOfTask;
    }

    public void setImageOfTask(byte[] imageOfTask) {
        this.imageOfTask = imageOfTask;
    }


    public Task(Long id, String name, String status,
                LocalDate dateOfDone, LocalTime timeOfDone, TaskMomentDone taskMomentDone, String note,
                byte[] imageOfTask, TaskCategory taskCategory, User user) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.dateOfDone = dateOfDone;
        this.timeOfDone = timeOfDone;
        this.taskMomentDone = taskMomentDone;
        this.note = note;
        this.imageOfTask = imageOfTask;
        this.taskCategory = taskCategory;
        this.user = user;
    }




}
