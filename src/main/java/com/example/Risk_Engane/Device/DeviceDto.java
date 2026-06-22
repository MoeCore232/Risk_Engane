package com.example.Risk_Engane.Device;

import java.util.UUID;

public class DeviceDto {

    public record CreateDevice (
            String deviceName,
            String network,
            String location,
            String ipAddress,
            UUID authId
    ) {}

}
