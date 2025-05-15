package com.example.haccpbackend.springSecurity;


import com.example.haccpbackend.registerJWT.JwtFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfig {



    private final JwtFilter jwtAuthFilter;



    @Autowired
    private  AuthenticationProvider authenticationProvider;

    public SecurityConfig(JwtFilter jwtFilter) {
        this.jwtAuthFilter = jwtFilter;


    }




/*
    @Bean
    public SecurityFilterChain securityFilterChain (HttpSecurity http) throws Exception{
        http.cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(req->
                        req.requestMatchers(
                                        "/auth/authenticate","/auth/register"

                                ).permitAll()
                                .anyRequest().authenticated()
                ).sessionManagement(session->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
    */






















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
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                       // .requestMatchers("/users").permitAll()
                             //   .requestMatchers("/products").permitAll()
                       // .requestMatchers(HttpMethod.POST, "/products").permitAll()  // Permet à tout le monde d'accéder
                        //.requestMatchers(HttpMethod.GET, "/products").permitAll()
                        //.requestMatchers("/users/**").hasAuthority("ADMIN")
                       // .requestMatchers("/products/**").hasAnyAuthority("ADMIN", "EMPLOYEE")
                       // .anyRequest().authenticated()
                        .anyRequest().hasAuthority("ADMIN")
                )
                .formLogin(Customizer.withDefaults())
                .httpBasic(Customizer.withDefaults());


        return http.build();
    }
*/



/*
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Désactive CSRF pour tests API (à éviter en production)
                .authorizeHttpRequests(auth -> auth.requestMatchers("**").permitAll()
                       // .requestMatchers("/users/**").hasAuthority("ADMIN") // ADMIN peut accéder à `/users/**`
                       // .anyRequest().authenticated() // Toutes les autres requêtes nécessitent une authentification
                )
                .formLogin(form -> form.defaultSuccessUrl("/home", true)) // Redirige après login
                .httpBasic(Customizer.withDefaults()); // Active l'authentification Basic pour API

        return http.build();
    }*/


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtFilter jwtFilter) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/**").permitAll()
                        .requestMatchers(
                                "/v3/api-docs/**",
                                "/swagger-ui/**",
                                "/swagger-ui.html"
                        ).permitAll()
                        .anyRequest().authenticated()
                )
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

}


