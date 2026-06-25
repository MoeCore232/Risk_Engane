package com.example.Risk_Engane.ResetCode;

import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
public class GenerateRandomCode {

    public String generateRandomCode () {
        SecureRandom secureRandom = new SecureRandom();
        int code = secureRandom.nextInt(900_000) + 100_000;
        return String.valueOf(code);
    }

}
