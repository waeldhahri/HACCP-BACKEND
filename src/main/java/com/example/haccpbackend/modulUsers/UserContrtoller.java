package com.example.haccpbackend.modulUsers;


import com.example.haccpbackend.modulProducts.Product;
import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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




    public UserContrtoller(IServiceUser iServiceUser , ServiceUser serviceUser) {
        this.iServiceUser = iServiceUser;
        this.serviceUser = serviceUser;
    }

    @GetMapping("")
    //@PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<User>> getAllUsers(){


        List<User> users = iServiceUser.getAllUsers();
        if (users != null && !users.isEmpty()) {
            return ResponseEntity.ok(users);
        } else {
            return ResponseEntity.notFound().build();
        }


    }



    @GetMapping("/page")
    @Transactional
    public Page<User> getAllUsersByPage(Pageable pageable){


        return userRepository.findAllByOrderByIdDesc(pageable);

    }



    @GetMapping("/email/{email}")
    @Transactional
    //@PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<User> getUserByEmail(@PathVariable String email){


        User emailuser = iServiceUser.findUserByEmail(email);

        if (emailuser != null ){

            return ResponseEntity.ok(iServiceUser.findUserByEmail(email));
        } else {

             return ResponseEntity.notFound().build();

        }


    }




    @GetMapping("/role/{role}")
    @Transactional
//@PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> getUserByRole3(@PathVariable String role) {
        Role matchedRole = null;

        for (Role r : Role.values()) {
            if (r.name().equalsIgnoreCase(role)) {
                matchedRole = r;
                break;
            }
        }

        if (matchedRole == null) {
            return ResponseEntity.badRequest().body("Le rôle '" + role + "' n'existe pas.");
        }

        List<User> roleUsers = iServiceUser.findUserByRole(matchedRole);
        if (roleUsers != null && !roleUsers.isEmpty()) {
            return ResponseEntity.ok(roleUsers);
        } else {
            return ResponseEntity.status(404).body("Aucun utilisateur trouvé avec le rôle '" + matchedRole + "'.");
        }
    }




    @PostMapping("")
    //@PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user){

        return ResponseEntity.status(HttpStatus.CREATED).body(iServiceUser.createUser(user));

    }




    @GetMapping("/fullname/{fullname}")
    @Transactional
    public ResponseEntity<List<User>> getUserByFullname(@PathVariable String fullname) {
        Optional<List<User>> fullnameUser = userRepository.findByFullName(fullname);

        return fullnameUser
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }













    @GetMapping("/{userId}")
    @Transactional
    //@PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<User> findUserById(@PathVariable Long userId){



        User idUser = iServiceUser.findUserById(userId);

        if (idUser != null){

            return ResponseEntity.ok(iServiceUser.findUserById(userId));

        } else {

            return ResponseEntity.notFound().build();

        }



    }




    @DeleteMapping("/{userId}")
    @Transactional
    //@PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId){

       //   tokenRepository.clearUserReferences(userId);

        //sessionRepository.clearEmployeeReferences(userId);

        Optional<User> optionalUser = userRepository.findById(userId);

        if (optionalUser.isPresent()){
            iServiceUser.deleteUser(optionalUser.get());
            return ResponseEntity.noContent().build();

        } else {

            return ResponseEntity.notFound().build();
        }





    }



    @PutMapping("update/{userId}")
    //@PreAuthorize("hasAuthority('ADMIN')")
    //@PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<User> updateUser(@PathVariable Long userId , @Valid @RequestBody User user ){

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(iServiceUser.updateUser(userId ,user));

    }















    // Étape 1: Demande de réinitialisation (envoi email)
    @PostMapping("/forgot-password")
    @Transactional
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
    @Transactional
    public ResponseEntity<String> resetPassword(@RequestParam String token, @RequestParam String newPassword) {
        try {
            serviceUser.resetPassword(token, newPassword);
            return ResponseEntity.ok("Mot de passe réinitialisé avec succès !");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }




    @GetMapping("/{id}/image")
    //@PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<byte[]> getImage(@PathVariable Long id) {

        Optional<User> userOptional=userRepository.findById(id);

        if (userOptional.isEmpty() || userOptional.get().getImageOfUser() == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        return ResponseEntity.ok()
                .header("Content-Type", "image/jpeg")
                .body(userOptional.get().getImageOfUser());
    }



}
