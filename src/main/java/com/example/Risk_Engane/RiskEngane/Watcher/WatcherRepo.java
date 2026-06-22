package com.example.Risk_Engane.RiskEngane.Watcher;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface WatcherRepo extends JpaRepository<Watcher, UUID> {
}
