package com.example.Risk_Engane.Device;

import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
@Table(name = "devices")
public class Device {

    @Id
    @GeneratedValue(generator = "UUID")
    @UuidGenerator
    private UUID id;

    @Column(name = "device_name", nullable = false)
    private String deviceName;

    @Column(name = "network", nullable = false)
    private String network;

    @Column(name = "location", nullable = false)
    private String location;

    @Column(name = "ipAddress", nullable = false)
    private String ipAddress;

    @Column(name = "auth_id", nullable = false)
    private UUID authId;

    private Device (UUID id, String deviceName, String network, String location, String ipAddress,
                    UUID authId) {
        this.id = id;
        this.deviceName = deviceName;
        this.network = network;
        this.location = location;
        this.ipAddress = ipAddress;
        this.authId = authId;
    }
    private Device () {}

    public static Device CreateDevice (DeviceDto.CreateDevice createDevice, UUID authId) {
        Device device = new Device();
        device.deviceName = createDevice.deviceName();
        device.network = createDevice.network();
        device.location = createDevice.location();
        device.ipAddress = createDevice.ipAddress();
        device.authId = authId;
        return device;
    }

    public UUID getId () {return id;}
    public String getDeviceName () {return deviceName;}
    public String getNetwork () {return network;}
    public String getLocation () {return location;}
    public String getIpAddress () {return ipAddress;}
    public UUID getAuthId () {return authId;}

}
