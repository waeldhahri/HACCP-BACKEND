package com.example.haccpbackend.modulSuiviHuile;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface SuiviHuileRepository extends JpaRepository<SuiviHuiles , Long> {






    Page<SuiviHuiles> findAllByOrderByIdDesc(Pageable pageable);



    List<SuiviHuiles> findByDateOfCreation(LocalDateTime date);


    List<SuiviHuiles> findByCreatedDay(LocalDate date);





}
