package com.example.haccpbackend.controller;


import com.example.haccpbackend.entities.User;
import com.example.haccpbackend.repository.UserRepository;
import com.example.haccpbackend.service.IServiceUser;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserContrtoller {

    private final IServiceUser iServiceUser;
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository ;



    @GetMapping("")
    public List<User> getAllEmploye(){

        return  iServiceUser.getAllUsers();

    }


    @GetMapping("/email/{email}")
    public ResponseEntity<User> getEmployeByEmail(@PathVariable String email){
        return ResponseEntity.ok(iServiceUser.findUserByEmail(email));
    }



    @PostMapping("")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user){

        return ResponseEntity.status(HttpStatus.CREATED).body(iServiceUser.createUser(user));
    }



    @GetMapping("/{userId}")
    public ResponseEntity<User> findEmployeById(@PathVariable Long userId){

        return ResponseEntity.ok(iServiceUser.findUserById(userId));
    }



    @DeleteMapping("/{userId}")
    @Transactional
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId){

        tokenRepository.clearUserReferences(userId);

        //sessionRepository.clearEmployeeReferences(userId);

        iServiceUser.deleteUser(iServiceUser.findUserById(userId));

        return ResponseEntity.noContent().build();
    }



    @PutMapping("update/{userId}")
    public ResponseEntity<User> updateEmploye(@Valid @RequestBody User user, @PathVariable Long userId ){
        user.setId(userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(iServiceUser.updateUser(user));
    }


}
