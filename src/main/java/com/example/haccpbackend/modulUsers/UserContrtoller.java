package com.example.haccpbackend.modulUsers;


import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin()
@RequestMapping("/users")
public class UserContrtoller {

    @Autowired
    private  IServiceUser iServiceUser;

    @Autowired
    private ServiceUser serviceUser;

    @Autowired
    private UserRepository userRepository;



    //private final TokenRepository tokenRepository ;


    public UserContrtoller(IServiceUser iServiceUser , ServiceUser serviceUser) {
        this.iServiceUser = iServiceUser;
        this.serviceUser = serviceUser;
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
    public ResponseEntity<User> findUserById(@PathVariable Long userId){

        return ResponseEntity.ok(iServiceUser.findUserById(userId));
    }



    public ResponseEntity<User> findUserByUserName(@PathVariable String fullname){


        //return ResponseEntity.ok(userRepository.findByUsername(username).isPresent() ? userRepository.findByUsername(username).get():null);

        return userRepository.findByFullName(fullname).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }



    @DeleteMapping("/{userId}")
    @Transactional
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId){

       //   tokenRepository.clearUserReferences(userId);

        //sessionRepository.clearEmployeeReferences(userId);

        Optional<User> optionalUser=userRepository.findById(userId);

        if (optionalUser.isPresent()){
            iServiceUser.deleteUser(optionalUser.get());
            return ResponseEntity.noContent().build();

        } else {

            return ResponseEntity.notFound().build();
        }



        //iServiceUser.deleteUser(iServiceUser.findUserById(userId));

    }



    @PutMapping("update/{userId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    //@PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<User> updateEmploye(@PathVariable Long userId , @Valid @RequestBody User user ){
        return ResponseEntity.status(HttpStatus.CREATED).body(iServiceUser.updateUser(userId ,user));
    }















    // Étape 1: Demande de réinitialisation (envoi email)
    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestParam String email) {
        try {
            serviceUser.sendResetEmail(email);
            return ResponseEntity.ok("Email de réinitialisation envoyé !");
        } catch (MessagingException e) {
            return ResponseEntity.status(500).body("Erreur lors de l'envoi de l'email");
        }
    }





    // Étape 2: Réinitialisation du mot de passe
    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestParam String token, @RequestParam String newPassword) {
        try {
            serviceUser.resetPassword(token, newPassword);
            return ResponseEntity.ok("Mot de passe réinitialisé avec succès !");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }




}
