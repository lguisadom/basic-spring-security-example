package com.lagm.springsecurity.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class WebSecurityConfig {
//     @Bean
//     public UserDetailsService userDetailsService() {
//     var user = User.withUsername("luis")
//              .password("password123")
//              .roles("read")
//              .build();
//
//          return new InMemoryUserDetailsManager(user);-
//     }

//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return NoOpPasswordEncoder.getInstance(); // No apto para producción
//    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        return http
                .httpBasic()
                .and()
                .authorizeHttpRequests()
                // .anyRequest().permitAll()
                // .anyRequest().denyAll()
                // .anyRequest().authenticated()
                // .anyRequest().hasRole("ADMIN")
                // .anyRequest().hasAuthority("WRITE")
                // .anyRequest().access(new WebExpressionAuthorizationManager("hasRole('ADMIN') or hasRole('DBA')"))

                .requestMatchers("/demo").permitAll()
                .requestMatchers("/admin").hasRole("ADMIN")
                .requestMatchers("/dba").hasAnyRole("DBA", "ADMIN")

                .requestMatchers(HttpMethod.POST, "/add").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/add").authenticated()

                // .and().build();
                .and().csrf().disable().build(); // deshabilita csrf para poder hacer petición por postman
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // return PasswordEncoderFactories.createDelegatingPasswordEncoder();
        return new BCryptPasswordEncoder();
    }

}
