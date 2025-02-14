package com.example.haccpbackend.modulUsers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    @Autowired
    private UserRepository userRepository;

/*

    public void register(User request) throws MessagingException  {



        var user = User.b
                .username(request.getUsername())
                .identifiant(request.getIdentifiant())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getIdentifiant()))
                .bloc2(request.getBloc2())
                .accountLocked(false)
                .enabled(true)
                .roles(userRole)
                .build();

        employeRepository.save(user);
        sendValidationEmail(user);


    }
*/

}
