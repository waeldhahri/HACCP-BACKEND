package com.example.haccpbackend.nettoyagesPostes;

import com.example.haccpbackend.modulTepuratureFrigo.Frigo;
import com.example.haccpbackend.modulTepuratureFrigo.FrigoRequest;
import com.example.haccpbackend.modulUsers.User;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;



public interface IServiceNettoyagePoste {





   // public List<NettoyagesPoste> findNettoyagesPosteByCategorie(String categorieName);



    public NettoyagesPoste updateNettoyagePoste(Long id , NettoyagePosteRequest nettoyagePosteRequest , MultipartFile file);


    public void deleteNettoyagePoste(NettoyagesPoste nettoyagesPoste);



    public NettoyagesPoste createNettoyagePoste(NettoyagesPoste nettoyagesPoste);




    public NettoyagesPoste validatePosteNettoyage(Long id , NettoyagePosteRequest nettoyagePosteRequest , MultipartFile file1 , MultipartFile file2);















}
