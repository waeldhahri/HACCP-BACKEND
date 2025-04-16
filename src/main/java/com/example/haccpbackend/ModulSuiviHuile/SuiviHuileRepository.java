package com.example.haccpbackend.ModulSuiviHuile;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface SuiviHuileRepository extends JpaRepository<SuiviHuiles , Long> {






    Page<SuiviHuiles> findAllByOrderByIdDesc(Pageable pageable);



    List<SuiviHuiles> findByDateOfCreation(LocalDate date);





}
