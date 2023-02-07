package com.lagm.springsecurity.repositories;

import com.lagm.springsecurity.model.Authority;
import com.lagm.springsecurity.util.AuthorityName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {
    Optional<Authority> findByName(AuthorityName name);
}
