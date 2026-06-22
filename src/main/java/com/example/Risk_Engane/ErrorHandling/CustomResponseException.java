package com.example.Risk_Engane.ErrorHandling;


import java.util.UUID;

public class CustomResponseException extends RuntimeException {

    private String message;
    private int code;

    public CustomResponseException (String message, int code) {
        this.message = message;
        this.code = code;
    }

    public static CustomResponseException idIsNotFound(UUID id) {
        return new CustomResponseException("Error: ID (" + id + ") is not found!", 404);
    }

    public static CustomResponseException publicError (String message, int code) {
        return new CustomResponseException(message, code);
    }

    public static CustomResponseException unExpectedErrorOccurred () {
        return new CustomResponseException("An unexpected error occurred. please try again later", 500);
    }

    public static CustomResponseException blockDevice () {
        return new CustomResponseException("Your device blocked, contact the support", 400);
    }

    public static CustomResponseException temporaryBlock (int time, String type) {
        return new CustomResponseException("Your device blocked for: " + time + " " + type, 400);
    }

    public String getMessage () {return message;}
    public int getCode () {return code;}

}
