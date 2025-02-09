package com.example.haccpbackend.repository;

import com.example.haccpbackend.entities.Role;
import org.springframework.data.jpa.mapping.JpaPersistentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface Rolerepository extends JpaRepository<Role,Long> {


    Optional<Role> findByRole(String role);
}
