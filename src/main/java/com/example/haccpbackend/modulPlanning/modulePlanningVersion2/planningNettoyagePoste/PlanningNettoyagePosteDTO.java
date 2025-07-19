package com.example.haccpbackend.modulPlanning.modulePlanningVersion2.planningNettoyagePoste;

public class PlanningNettoyagePosteDTO {

    private String tache;
    private boolean checked;
    private Long nettoyagePosteId;


    public String getTache() {
        return tache;
    }

    public void setTache(String tache) {
        this.tache = tache;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public Long getNettoyagePosteId() {
        return nettoyagePosteId;
    }

    public void setNettoyagePosteId(Long nettoyagePosteId) {
        this.nettoyagePosteId = nettoyagePosteId;
    }

    public PlanningNettoyagePosteDTO(String tache, boolean checked, Long nettoyagePosteId) {
        this.tache = tache;
        this.checked = checked;
        this.nettoyagePosteId = nettoyagePosteId;
    }
}
