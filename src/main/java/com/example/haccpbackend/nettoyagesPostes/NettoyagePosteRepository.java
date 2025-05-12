package com.example.haccpbackend.nettoyagesPostes;

import com.example.haccpbackend.modulPlanning.Planning;
import com.example.haccpbackend.modulPlanning.PlanningCategorie;
import com.example.haccpbackend.modulTepuratureFrigo.Frigo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Repository
public interface NettoyagePosteRepository extends JpaRepository<NettoyagesPoste,Long> {




    public List<NettoyagesPoste> findByDateOfCreation(LocalDateTime dateTime);

    public List<NettoyagesPoste> findByCreatedDay(LocalDate date);

    public List<NettoyagesPoste> findByCategorieNettoyage_NameIgnoreCase(String name);


    Page<NettoyagesPoste> findAllByOrderByIdDesc(Pageable pageable);




    Optional<NettoyagesPoste> findFirstByNameOfPosteAndCategorieNettoyageAndCreatedDay(
            String nameOfPoste ,
            CategorieNettoyage categorieNettoyage
            , LocalDate createdDay
    );


}
