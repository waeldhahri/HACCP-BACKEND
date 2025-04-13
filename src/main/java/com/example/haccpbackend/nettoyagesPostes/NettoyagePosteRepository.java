package com.example.haccpbackend.nettoyagesPostes;

import com.example.haccpbackend.modulTepuratureFrigo.Frigo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;




@Repository
public interface NettoyagePosteRepository extends JpaRepository<NettoyagesPoste,Long> {




    public List<NettoyagesPoste> findByDateOfCreation(LocalDateTime dateTime);

    public List<NettoyagesPoste> findByCategorieNettoyage_NameIgnoreCase(String name);


    Page<NettoyagesPoste> findAllByOrderByIdDesc(Pageable pageable);


}
