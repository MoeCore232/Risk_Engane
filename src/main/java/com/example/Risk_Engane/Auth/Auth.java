package com.example.Risk_Engane.Auth;

import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
@Table(name = "auths")
public class Auth {

    public enum UserStatus {
        BLOCK, ACTIVE
    }

    @Id
    @GeneratedValue(generator = "UUID")
    @UuidGenerator
    private UUID id;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "user_status", nullable = false)
    private UserStatus userStatus;

    public Auth (UUID id, String email, String password, UserStatus userStatus) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.userStatus = userStatus;
    }
    public Auth () {}

    public static Auth createAuth (AuthDto.SignUp signUp) {
        Auth auth = new Auth();
        auth.email = signUp.email();
        auth.password = signUp.password();
        auth.userStatus = UserStatus.ACTIVE;
        return auth;
    }

    public UUID getId () {return id;}
    public String getEmail () {return email;}
    public String getPassword () {return password;}
    public UserStatus getUserStatus () {return userStatus;}

}
