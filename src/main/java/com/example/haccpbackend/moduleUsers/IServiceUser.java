package com.example.haccpbackend.moduleUsers;

import com.example.haccpbackend.moduleUsers.User;

import java.util.List;

public interface IServiceUser {


    public User createUser(User user);
    public User findUserById(Long id);

    public User updateUser(Long id,User newUser);

    public List<User> getAllUsers();

    public void deleteUser(User user);


    User findUserByEmail(String email);


    List<User> findUserByRole(String roleName);


}
