package com.example.haccpbackend.moduleTasks;


import com.example.haccpbackend.modulProducts.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task , Long> {



    List<Task> findByDateOfDone(LocalDate date);


    List<Task> findTasksByStatus(String status);


    List<Task> findByTaskCategory(TaskCategory categorie);


    List<Task> findByTaskMomentDone(TaskMomentDone taskMomentDone);




    Page<Task> findAllByOrderByIdDesc(Pageable pageable);
}
