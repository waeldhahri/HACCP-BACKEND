package com.example.haccpbackend.moduleUsers;


import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin()
@RequestMapping("/users")
public class UserContrtoller {

    @Autowired
    private  IServiceUser iServiceUser;

    @Autowired
    private   UserRepository userRepository;

    //private final TokenRepository tokenRepository ;


    public UserContrtoller(IServiceUser iServiceUser) {
        this.iServiceUser = iServiceUser;
    }

    @GetMapping("")
    //@PreAuthorize("hasAuthority('ADMIN')")
    public List<User> getAllEmploye(){

        return  iServiceUser.getAllUsers();

    }


    @GetMapping("/email/{email}")
    //@PreAuthorize("hasRole('ADMIN') or hasRole('SUPER_ADMIN')")
    public ResponseEntity<User> getEmployeByEmail(@PathVariable String email){
        return ResponseEntity.ok(iServiceUser.findUserByEmail(email));
    }



    @PostMapping("")
   // @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user){

        return ResponseEntity.status(HttpStatus.CREATED).body(iServiceUser.createUser(user));

    }



    @GetMapping("/{userId}")
    public ResponseEntity<User> findEmployeById(@PathVariable Long userId){

        return ResponseEntity.ok(iServiceUser.findUserById(userId));
    }



    @DeleteMapping("/{userId}")
    @Transactional
    //@PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId){

       //   tokenRepository.clearUserReferences(userId);

        //sessionRepository.clearEmployeeReferences(userId);

        iServiceUser.deleteUser(iServiceUser.findUserById(userId));

        return ResponseEntity.noContent().build();
    }



    @PutMapping("update/{userId}")
    //@PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<User> updateEmploye(@PathVariable Long userId , @Valid @RequestBody User user ){
        return ResponseEntity.status(HttpStatus.CREATED).body(iServiceUser.updateUser(userId ,user));
    }


}
