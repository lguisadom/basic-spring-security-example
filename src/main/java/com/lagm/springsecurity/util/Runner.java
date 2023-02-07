package com.lagm.springsecurity.util;

import com.lagm.springsecurity.model.Authority;
import com.lagm.springsecurity.model.User;
import com.lagm.springsecurity.repositories.AuthorityRepository;
import com.lagm.springsecurity.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Runner implements CommandLineRunner {

    private final UserRepository userRepository;

    private final AuthorityRepository authorityRepository;

    public Runner(UserRepository userRepository, AuthorityRepository authorityRepository) {
        this.userRepository = userRepository;
        this.authorityRepository = authorityRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Hello world!");
        if (this.authorityRepository.count() == 0) {
                this.authorityRepository.saveAll(List.of(
                        new Authority(AuthorityName.ADMIN),
                        new Authority(AuthorityName.READ),
                        new Authority(AuthorityName.WRITE)
                ));
        }


        // NoOpPasswordEncoder
        /*if (this.userRepository.count() == 0) {
            this.userRepository.saveAll(List.of(
                    new User("luis", "luis123", List.of(this.authorityRepository.findByName(AuthorityName.ADMIN).get())),
                    new User("luis2", "luis123", List.of(this.authorityRepository.findByName(AuthorityName.READ).get())),
                    new User("luis3", "luis123", List.of(this.authorityRepository.findByName(AuthorityName.WRITE).get()))
            ));
        }*/

        // BCryptPasswordEncoder();
        if (this.userRepository.count() == 0) {
            var encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
            this.userRepository.saveAll(List.of(
                    new User("luis", encoder.encode("luis123"), List.of(this.authorityRepository.findByName(AuthorityName.ADMIN).get())),
                    new User("luis2", "luis123", List.of(this.authorityRepository.findByName(AuthorityName.READ).get())),
                    new User("luis3", "luis123", List.of(this.authorityRepository.findByName(AuthorityName.WRITE).get()))
            ));
        }
    }
}
