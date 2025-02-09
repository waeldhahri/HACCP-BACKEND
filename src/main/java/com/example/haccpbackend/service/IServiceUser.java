package com.example.haccpbackend.service;

import com.example.haccpbackend.entities.User;

import java.util.List;
import java.util.Optional;

public interface IServiceUser {


    public User createUser(User user);
    public User findUserById(Long Id);

    public User updateUser(User user);

    public List<User> getAllUsers();

    public void deleteUser(User user);


    User findUserByEmail(String email);


    List<User> findUserByRole(String roleName);


}
