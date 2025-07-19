package com.example.haccpbackend.modulPlanning.modulePlanningVersion2.planningFrigo;

public class PlanningFrigoDTO {

    private String tache;
    private Long frigoId;

    private boolean checked ;

    // getter and setter


    public String getTache() {
        return tache;
    }

    public void setTache(String tache) {
        this.tache = tache;
    }

    public Long getFrigoId() {
        return frigoId;
    }

    public void setFrigoId(Long frigoId) {
        this.frigoId = frigoId;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }


    //constructors
    public PlanningFrigoDTO(String tache, Long frigoId, boolean checked) {
        this.tache = tache;
        this.frigoId = frigoId;
        this.checked = checked;
    }

    public PlanningFrigoDTO() {
    }
}
