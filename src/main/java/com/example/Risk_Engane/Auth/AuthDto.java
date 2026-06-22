package com.example.Risk_Engane.Auth;

public class AuthDto {

    public record SignUp (
            String email,
            String password
    ) {}

    public record Login (
            String email,
            String password,
            String deviceName,
            String network,
            String location,
            String ipAddress,
            int loginTypingSpeed
    ) {}
}
