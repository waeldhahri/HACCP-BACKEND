package com.example.haccpbackend.modulUsers;


import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin()
@RequestMapping("/users")
public class UserContrtoller {

    @Autowired
    private  IServiceUser iServiceUser;



    //private final TokenRepository tokenRepository ;


    public UserContrtoller(IServiceUser iServiceUser) {
        this.iServiceUser = iServiceUser;
    }

    @GetMapping("")
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<User> getAllUsers(){

        return  iServiceUser.getAllUsers();

    }


    @GetMapping("/email/{email}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<User> getUserByEmail(@PathVariable String email){
        return ResponseEntity.ok(iServiceUser.findUserByEmail(email));
    }



    @PostMapping("")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user){

        return ResponseEntity.status(HttpStatus.CREATED).body(iServiceUser.createUser(user));

    }



    @GetMapping("/{userId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<User> findEmployeById(@PathVariable Long userId){

        return ResponseEntity.ok(iServiceUser.findUserById(userId));
    }



    @DeleteMapping("/{userId}")
    @Transactional
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId){

       //   tokenRepository.clearUserReferences(userId);

        //sessionRepository.clearEmployeeReferences(userId);

        iServiceUser.deleteUser(iServiceUser.findUserById(userId));

        return ResponseEntity.noContent().build();
    }



    @PutMapping("update/{userId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    //@PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<User> updateEmploye(@PathVariable Long userId , @Valid @RequestBody User user ){
        return ResponseEntity.status(HttpStatus.CREATED).body(iServiceUser.updateUser(userId ,user));
    }


}
