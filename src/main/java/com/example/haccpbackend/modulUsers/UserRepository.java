package com.example.haccpbackend.modulUsers;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findByEmail(String Email);
    List<User> findUserByRole(String role);


    Optional<User> findByResetToken(String resetToken);

}
