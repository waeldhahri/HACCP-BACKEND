package com.example.haccpbackend.modulPlanning.modulePlanningVersion2.planningHuile;

public class PlanningHuileDTO {

    private String tache;
    private boolean checked;
    private Long suiviHuileId;


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

    public Long getSuiviHuileId() {
        return suiviHuileId;
    }

    public void setSuiviHuileId(Long suiviHuileId) {
        this.suiviHuileId = suiviHuileId;
    }


    public PlanningHuileDTO(String tache, boolean checked, Long suiviHuileId) {
        this.tache = tache;
        this.checked = checked;
        this.suiviHuileId = suiviHuileId;
    }

    public PlanningHuileDTO() {
    }
}
