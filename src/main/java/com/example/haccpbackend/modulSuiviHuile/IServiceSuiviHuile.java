package com.example.haccpbackend.modulSuiviHuile;

import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

public interface IServiceSuiviHuile {




    public SuiviHuiles createFruiteuse(SuiviHuiles suiviHuiles);



    public void deleteFriteuse(SuiviHuiles suiviHuiles);



    public List<SuiviHuiles> findFriteuseDeJour();


    public List<SuiviHuiles> findFriteuseByDate(LocalDate date);


    public SuiviHuiles validateFriteuse(Long id , SuiviHuilesRequest suiviHuilesRequest  , MultipartFile file1);

    public SuiviHuiles updateFriteuse(Long id , SuiviHuiles newFriteuse);




}
