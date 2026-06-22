package com.example.Risk_Engane.BlockTarget;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface BlockRepo extends JpaRepository<Block, UUID> {

    Optional<Block> findByTargetValue (String targetValue);

}
