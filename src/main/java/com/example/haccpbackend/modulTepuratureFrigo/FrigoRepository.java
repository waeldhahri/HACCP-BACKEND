package com.example.haccpbackend.modulTepuratureFrigo;

import com.example.haccpbackend.modulProducts.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface FrigoRepository extends JpaRepository<Frigo,Long> {



   List<Frigo> findByCategorieFrigo_NameIgnoreCase(String name);




   Page<Frigo> findAllByOrderByIdDesc(Pageable pageable);

}
