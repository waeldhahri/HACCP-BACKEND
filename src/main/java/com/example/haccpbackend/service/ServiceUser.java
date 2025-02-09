package com.example.haccpbackend.service;

import com.example.haccpbackend.entities.User;
import com.example.haccpbackend.repository.Rolerepository;
import com.example.haccpbackend.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
public class ServiceUser implements IServiceUser{



    private final UserRepository userRepository;
    private final Rolerepository rolerepository;

    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User findUserById(Long id) {
        return userRepository.findById(id).get();
    }

    @Override
    public User updateUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll().stream()
                .sorted(Comparator.comparingLong(User::getId)).
                collect(Collectors.toList());
    }

    @Override
    public void deleteUser(User user) {

        userRepository.delete(user);

    }

    @Override
    public User findUserByEmail(String email) {

        return userRepository.findByEmail(email).isPresent() ? userRepository.findByEmail(email).get() : null ;
    }

    @Override
    public List<User> findUserByRole(String roleName) {
        return null;
    }
}
