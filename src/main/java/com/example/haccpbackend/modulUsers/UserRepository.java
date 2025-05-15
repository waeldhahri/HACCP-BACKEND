package com.example.haccpbackend.modulUsers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findByEmail(String Email);
    List<User> findUserByRole(Role role);

    Optional<List<User>> findByFullName(String fullname);


    Optional<User> findByResetToken(String resetToken);


    Page<User> findAllByOrderByIdDesc(Pageable pageable);



}
