package com.example.Risk_Engane.Auth;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AuthRepo extends JpaRepository<Auth, UUID> {
    Optional<Auth> findByEmail (String email);
}
