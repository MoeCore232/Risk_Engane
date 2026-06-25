package com.example.Risk_Engane.ResetCode;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TrustCodeRepo extends JpaRepository<TrustCode, UUID> {
    Optional<TrustCode> findByCode(String code);
}
