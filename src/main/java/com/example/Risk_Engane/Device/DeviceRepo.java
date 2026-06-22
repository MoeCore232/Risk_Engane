package com.example.Risk_Engane.Device;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface DeviceRepo extends JpaRepository<Device, UUID> {
    Optional<Device> findByAuthId (UUID authId);
}
