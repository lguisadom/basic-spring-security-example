package com.lagm.springsecurity.util;

import com.lagm.springsecurity.model.Authority;
import com.lagm.springsecurity.model.User;
import com.lagm.springsecurity.repositories.IAuthorityRepository;
import com.lagm.springsecurity.repositories.IUserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Runner implements CommandLineRunner {

    private final IUserRepository iUserRepository;
    private final IAuthorityRepository iAuthorityRepository;

    public Runner(IUserRepository iUserRepository, IAuthorityRepository iAuthorityRepository) {
        this.iUserRepository = iUserRepository;
        this.iAuthorityRepository = iAuthorityRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Hello world!");
        if (this.iAuthorityRepository.count() == 0) {
                this.iAuthorityRepository.saveAll(List.of(
                        new Authority(AuthorityName.READ),
                        new Authority(AuthorityName.WRITE),
                        new Authority(AuthorityName.ROLE_ADMIN),
                        new Authority(AuthorityName.ROLE_USER),
                        new Authority(AuthorityName.ROLE_DBA)
                ));
        }


        // NoOpPasswordEncoder
        /*if (this.iUserRepository.count() == 0) {
            this.iUserRepository.saveAll(List.of(
                    new User("luis", "luis123", List.of(this.iAuthorityRepository.findByName(AuthorityName.ADMIN).get())),
                    new User("luis2", "luis123", List.of(this.iAuthorityRepository.findByName(AuthorityName.READ).get())),
                    new User("luis3", "luis123", List.of(this.iAuthorityRepository.findByName(AuthorityName.WRITE).get()))
            ));
        }*/

        // BCryptPasswordEncoder();
        if (this.iUserRepository.count() == 0) {
            var encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
            this.iUserRepository.saveAll(List.of(
                    new User("admin", new BCryptPasswordEncoder().encode("password123"), true,
                            List.of(
                                    iAuthorityRepository.findByName(AuthorityName.ROLE_ADMIN).get(),
                                    iAuthorityRepository.findByName(AuthorityName.READ).get(),
                                    iAuthorityRepository.findByName(AuthorityName.WRITE).get()
                            )
                    ),
                    new User("user", new BCryptPasswordEncoder().encode("password123"), true,
                            List.of(
                                    iAuthorityRepository.findByName(AuthorityName.READ).get(),
                                    iAuthorityRepository.findByName(AuthorityName.ROLE_USER).get()
                            )
                    ),
                    new User("dba", new BCryptPasswordEncoder().encode("password123"), true,
                            List.of(
                                    iAuthorityRepository.findByName(AuthorityName.READ).get(),
                                    iAuthorityRepository.findByName(AuthorityName.ROLE_DBA).get()
                            )
                    )
            ));
        }
    }
}
