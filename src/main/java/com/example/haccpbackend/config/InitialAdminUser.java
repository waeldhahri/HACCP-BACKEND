package com.example.haccpbackend.config;


import com.example.haccpbackend.modulUsers.Role;
import com.example.haccpbackend.modulUsers.User;
import com.example.haccpbackend.modulUsers.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class InitialAdminUser {



        private final UserRepository userRepository;

        private final PasswordEncoder passwordEncoder ;


    public InitialAdminUser(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }




    @PostConstruct
    public void createSuperAdminIfNotExists() {
        String email = "khalil@gmail.com";

        if (userRepository.findByEmail(email).isEmpty()) {
            User superAdmin = new User();
            superAdmin.setEmail(email);
            superAdmin.setFullName("khalil");
            superAdmin.setMotdepasse(passwordEncoder.encode("password"));
            superAdmin.setRole(Role.SUPER_ADMIN);
            superAdmin.setEnabled(true);
            superAdmin.setAccountLocked(false);

            userRepository.save(superAdmin);
            System.out.println("Super Admin created successfully.");
        }
    }
}







