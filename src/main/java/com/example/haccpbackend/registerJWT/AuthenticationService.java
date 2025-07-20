
package com.example.haccpbackend.registerJWT;


import com.example.haccpbackend.modulUsers.Role;
import com.example.haccpbackend.modulUsers.User;
import com.example.haccpbackend.modulUsers.UserDto;
import com.example.haccpbackend.modulUsers.UserRepository;
import com.example.haccpbackend.organisation.Organisation;
import jakarta.mail.MessagingException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthenticationService {



    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    private final TokenRepository tokenRepository;


    private  long jwtExpiration =86400000;


    public AuthenticationService(PasswordEncoder passwordEncoder, UserRepository userRepository, AuthenticationManager authenticationManager, JwtService jwtService, TokenRepository tokenRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.tokenRepository = tokenRepository;
    }


    public Role getRoleFromRequest(String roleName) {
        try {
            return Role.valueOf(roleName.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalStateException("Le rôle " + roleName + " est invalide.");
        }
    }


    public List<String> getAvailableRoles() {
        return Arrays.stream(Role.values())
                .map(Enum::name)
                .collect(Collectors.toList());
    }





    public void register(RegistrationRequest request) throws MessagingException{


        Role userRole = getRoleFromRequest(request.getRole());

        var user = new User();

        user.setFullName(request.getFullname());
        user.setEmail(request.getEmail());
        user.setMotdepasse(passwordEncoder.encode(request.getMotdepasse()));
        user.setRole(userRole);
        //user.setImageUrl(request.getImageUrl());
        user.setImageOfUser(request.getImageOfUser());
        user.setOrganisation(request.getOrganisation());

        user = userRepository.save(user);

        user.setImageUrl(ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/users/")
                .path(user.getId().toString())
                .path("/image")
                .toUriString());



        userRepository.save(user);


    }




    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        var auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getmotdepasse()
                )
        );

        var claims = new HashMap<String, Object>();
        var user = ((User) auth.getPrincipal());
        claims.put("email", user.getUsername());


        var jwtToken = jwtService.generateToken(claims, (User) auth.getPrincipal());
        saveToken(user, jwtToken);

        System.out.println("Token enregistré");

        Organisation org = user.getOrganisation();

        Long organisationId = (org != null) ? org.getId() : null;

        UserDto userDTO = new UserDto(user.getId(), user.getFullName(), user.getEmail(), user.getRole().name() , organisationId);

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .user(userDTO)
                .build();
    }





    private void saveToken(User user, String jwtToken) {
        Token token = new Token();
        token.setToken(jwtToken);
        token.setCreatedAt(LocalDateTime.now());
        token.setExpiredAt(LocalDateTime.now().plusDays(1)); // ou autre expiration
        token.setValidateAt(null); // si tu veux le gérer plus tard
        token.setUserToken(user);
        tokenRepository.save(token); // bien appeler le repository ici
    }

















    private String generateActivationCode(int length) {

        String characters = "0123456789";
        StringBuilder codeBuilder = new StringBuilder();

        SecureRandom secureRandom = new SecureRandom();

        for (int i = 0; i < length; i++) {
            int randomIndex = secureRandom.nextInt(characters.length());
            codeBuilder.append(characters.charAt(randomIndex));
        }

        return codeBuilder.toString();
    }







    private String generateAndSaveActivationToken(User user) {

        // Generate a token
        String generatedToken = generateActivationCode(6);
        var token = Token.builder()
                .token(generatedToken)
                .createdAt(LocalDateTime.now())
                .expiredAt(LocalDateTime.now().plusMinutes(15))
                .userToken(user)
                .build();
        tokenRepository.save(token);



        return generatedToken;
    }







}

