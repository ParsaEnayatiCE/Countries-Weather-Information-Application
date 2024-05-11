package com.sharif.edu.hw1.DataBase;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TokenCookieRepository extends JpaRepository<TokenCookie, Long> {
    Optional<TokenCookie> findByToken(String token);
}