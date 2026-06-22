package com.example.Risk_Engane.Device;

import com.example.Risk_Engane.Auth.Auth;
import com.example.Risk_Engane.Auth.AuthRepo;
import com.example.Risk_Engane.ErrorHandling.CustomResponseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class DeviceService {

    @Autowired
    private DeviceRepo deviceRepo;
    @Autowired
    private AuthRepo authRepo;

    public List<Device> getAllDevices () {
        return deviceRepo.findAll();
    }

    public Device createDevice (DeviceDto.CreateDevice createDevice) {
        System.out.println("Auth ID: " + createDevice.authId());
        System.out.println(createDevice.deviceName());

        Auth auth = authRepo.findById(createDevice.authId())
                .orElseThrow(() -> CustomResponseException.idIsNotFound(createDevice.authId()));
        Device device = Device.CreateDevice(createDevice, auth.getId());
        deviceRepo.save(device);
        return device;
    }

    public Device findDeviceByAuthId (UUID authId) {
        Device device = deviceRepo.findByAuthId(authId)
                .orElseThrow(() -> CustomResponseException.idIsNotFound(authId));
        return device;
    }

}
