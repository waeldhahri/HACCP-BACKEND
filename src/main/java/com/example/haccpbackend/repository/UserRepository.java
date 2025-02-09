package com.example.haccpbackend.repository;

import com.example.haccpbackend.entities.Role;
import com.example.haccpbackend.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findByEmail(String Email);

    List<User> findByRoles(Optional<Role> roles);

}
