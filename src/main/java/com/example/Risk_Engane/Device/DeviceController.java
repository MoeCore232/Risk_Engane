package com.example.Risk_Engane.Device;

import com.example.Risk_Engane.Auth.Auth;
import com.example.Risk_Engane.Auth.AuthRepo;
import com.example.Risk_Engane.ErrorHandling.CustomResponseException;
import com.example.Risk_Engane.ErrorHandling.GlobalResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/api/device")
public class DeviceController {

    @Autowired
    private DeviceService deviceService;

    @GetMapping("/get-all-devices")
    public ResponseEntity<GlobalResponse<List<Device>>> getAllDevices () {
        List<Device> devices = deviceService.getAllDevices();
        return new ResponseEntity<>(new GlobalResponse<>(devices), HttpStatus.OK);
    }

    @PostMapping("/create-device")
    public ResponseEntity<GlobalResponse<Device>> createDevice (@RequestBody DeviceDto.CreateDevice createDevice) {
        Device device = deviceService.createDevice(createDevice);
        return new ResponseEntity<>(new GlobalResponse<>(device), HttpStatus.OK);
    }

}
