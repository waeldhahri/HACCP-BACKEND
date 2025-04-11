package com.example.haccpbackend.modulTepuratureFrigo;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategorieFrigoRepository extends JpaRepository<CategorieFrigo,Long> {




    List<CategorieFrigo> findByNameIgnoreCase(String name);


}
