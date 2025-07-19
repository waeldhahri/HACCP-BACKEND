package com.example.haccpbackend.modulPlanning.modulePlanningVersion2;


import com.example.haccpbackend.modulSuiviHuile.SuiviHuiles;
import com.example.haccpbackend.modulTepuratureFrigo.Frigo;
import com.example.haccpbackend.modulTepuratureFrigo.TemperatureFrigo;
import com.example.haccpbackend.nettoyagesPostes.NettoyagesPoste;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.List;


public class PlanningVersion2 {








    private List<Frigo> frigos;
    private List<NettoyagesPoste> nettoyagesPostes;
    private List<SuiviHuiles> suivisHuiles;

    // getters/setters


    public List<Frigo> getFrigos() {
        return frigos;
    }

    public void setFrigos(List<Frigo> frigos) {
        this.frigos = frigos;
    }

    public List<NettoyagesPoste> getNettoyagesPostes() {
        return nettoyagesPostes;
    }

    public void setNettoyagesPostes(List<NettoyagesPoste> nettoyagesPostes) {
        this.nettoyagesPostes = nettoyagesPostes;
    }

    public List<SuiviHuiles> getSuivisHuiles() {
        return suivisHuiles;
    }

    public void setSuivisHuiles(List<SuiviHuiles> suivisHuiles) {
        this.suivisHuiles = suivisHuiles;
    }

    // constructors
    public PlanningVersion2(List<Frigo> frigos, List<NettoyagesPoste> nettoyagesPostes,
                            List<SuiviHuiles> suivisHuiles) {
        this.frigos = frigos;
        this.nettoyagesPostes = nettoyagesPostes;
        this.suivisHuiles = suivisHuiles;
    }


    public PlanningVersion2() {
    }
}
