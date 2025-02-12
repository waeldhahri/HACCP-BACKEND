package com.example.haccpbackend.moduleUsers;

import com.example.haccpbackend.moduleUsers.IServiceUser;
import com.example.haccpbackend.moduleUsers.User;
import com.example.haccpbackend.moduleUsers.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ServiceUser implements IServiceUser {

    @Autowired
    private  UserRepository userRepository;








/*
    @Autowired
    private PasswordEncoder passwordEncoder;


    public UserDetails createAdminUser() {
        return org.springframework.security.core.userdetails.User.builder()
                .username("khalil")
                .password(passwordEncoder.encode("password")) // ✅ Hacher le mot de passe
                .authorities("SUPER_ADMIN")
                .build();
    }



*/

    @Override
    public User createUser(User user) {
       // user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public User findUserById(Long id) {
        return userRepository.findById(id).get();
    }

    @Override
    public User updateUser(Long id , User newuser) {

        //newuser.setPassword(passwordEncoder.encode(newuser.getPassword()));
        return userRepository.findById(id).map(user->{
            user.setFullName(newuser.getFullName());
            user.setPassword(newuser.getPassword());
            user.setEmail(newuser.getEmail());
            user.setRole(newuser.getRole());
            return userRepository.save(user);
        }).orElseThrow(()-> new RuntimeException(" User not found "));
     }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
                //.stream()
                //.sorted(Comparator.comparingLong(User::getId)).
                //collect(Collectors.toList());
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
        return userRepository.findUserByRole(roleName);
    }
}
