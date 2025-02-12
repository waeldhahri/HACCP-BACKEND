package com.example.haccpbackend.springSecurity;

/*
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
/*
@Configuration
@EnableWebSecurity
public class SecurityConfig {




    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }



    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder){

        UserDetails khalil = User.builder()
                .username("khalil")
                .password(passwordEncoder.encode("password"))
                .authorities("ADMIN").build();
        return new InMemoryUserDetailsManager(khalil);
    }



/*
    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails admin = User.withDefaultPasswordEncoder()
                .username("khalil")
                .password("password")
                .authorities("SUPER_ADMIN")
                .build();


        UserDetails ouvrier = User.withDefaultPasswordEncoder()
                .username("ouvrier")
                .password("password")
                .authorities("OUVRIER")
                .build();

        return new InMemoryUserDetailsManager(admin, ouvrier);

    }*/
/*

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/users").permitAll()
                                .requestMatchers("/products").permitAll()
                        .requestMatchers(HttpMethod.POST, "/products").permitAll()  // Permet à tout le monde d'accéder
                        .requestMatchers(HttpMethod.GET, "/products").permitAll()
                        //.requestMatchers("/users/**").hasAuthority("ADMIN")
                       // .requestMatchers("/products/**").hasAnyAuthority("ADMIN", "EMPLOYEE")
                       // .anyRequest().authenticated()
                )
                .formLogin(Customizer.withDefaults())
                .httpBasic(Customizer.withDefaults());


        return http.build();
    }


}
*/