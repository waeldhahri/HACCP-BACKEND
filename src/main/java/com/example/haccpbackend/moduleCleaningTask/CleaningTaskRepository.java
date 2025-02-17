package com.example.haccpbackend.moduleCleaningTask;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CleaningTaskRepository extends JpaRepository<CleaningTask, Long> {
    List<CleaningTask> findByTaskDateBetween(LocalDateTime start, LocalDateTime end);
}