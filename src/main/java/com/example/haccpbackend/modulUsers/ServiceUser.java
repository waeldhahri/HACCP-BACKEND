package com.example.haccpbackend.modulUsers;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
public class ServiceUser implements IServiceUser {

    @Autowired
    private  UserRepository userRepository;


    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private PasswordEncoder passwordEncoder;


    //private static final String FRONTEND_RESET_URL = "http://localhost:8080/users/reset-password?token=";

    //private static final String FRONTEND_RESET_URL = ServletUriComponentsBuilder.fromCurrentContextPath().path("/users/reset-password?token=").toUriString();



    public String getResetPasswordUrl(String token) {
        return ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/users/reset-password")
                .queryParam("token", token)
                .toUriString();
    }











    // Étape 1: Générer un token et envoyer l'email
    public void sendResetEmail(String email) throws MessagingException {
        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isEmpty()) {
            throw new RuntimeException("Email non trouvé");
        }

        User user = userOpt.get();
        String token = UUID.randomUUID().toString(); // Générer un token unique
        user.setResetToken(token);
        userRepository.save(user);





        // Construction dynamique du lien de réinitialisation
        String resetLink = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/users/reset-password")
                .queryParam("token", token)
                .toUriString();



        String content = "<p>Bonjour,</p>"
                + "<p>Nous avons reçu une demande de réinitialisation de votre mot de passe.</p>"
                + "<p>Pour réinitialiser votre mot de passe, veuillez cliquer sur le lien ci-dessous :</p>"
                + "<p><a href=\"" + resetLink + "\">Réinitialiser mon mot de passe</a></p>"
                + "<br>"
                + "<p>Si vous n'êtes pas à l'origine de cette demande, vous pouvez ignorer cet e-mail. "
                + "Votre mot de passe actuel restera inchangé.</p>"
                + "<br>"
                + "<p>Cordialement,<br>L'équipe support</p>";



        // Envoi de l'email
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(email);
        helper.setSubject("Réinitialisation de votre mot de passe");
       // helper.setText("Cliquez sur le lien suivant pour réinitialiser votre mot de passe : " + resetLink);
        helper.setText(content, true);
        mailSender.send(message);
    }


    // Étape 2: Vérifier le token et réinitialiser le mot de passe
    public void resetPassword(String token, String newPassword) {
        Optional<User> userOpt = userRepository.findByResetToken(token);
        if (userOpt.isEmpty()) {
            throw new RuntimeException("Token invalide");
        }

        User user = userOpt.get();
        user.setMotdepasse(passwordEncoder.encode(newPassword)); // Hash du mot de passe
        user.setResetToken(null); // Supprimer le token après utilisation
        userRepository.save(user);
    }




/*
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

        user.setMotdepasse(passwordEncoder.encode(user.getMotdepasse()));
        return userRepository.save(user);
    }

    @Override
    public User findUserById(Long id) {
        return userRepository.findById(id)
                .orElse(null);
    }

    @Override
    public User updateUser(Long id , User newuser) {


        return userRepository.findById(id).map(user->{
            user.setFullName(newuser.getFullName());
            user.setMotdepasse(passwordEncoder.encode(newuser.getPassword()));
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
    public List<User> findUserByRole(Role roleName) {

        return userRepository.findUserByRole(roleName);

    }
}
