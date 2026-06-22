package com.example.Risk_Engane.Auth;

import com.example.Risk_Engane.ErrorHandling.GlobalResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @GetMapping("/get-all-auths")
    public ResponseEntity<GlobalResponse<List<Auth>>> getAllAuths () {
        List<Auth> auths = authService.getAllAuths();
        return new ResponseEntity<>(new GlobalResponse<>(auths), HttpStatus.OK);
    }

    @PostMapping("/sign-up")
    public ResponseEntity<GlobalResponse<Auth>> signUp (@RequestBody AuthDto.SignUp signUp) {
        Auth auth = authService.signUp(signUp);
        return new ResponseEntity<>(new GlobalResponse<>(auth), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<GlobalResponse<String>> login (@RequestBody AuthDto.Login login) {
        String auth = authService.login(login);
        return new ResponseEntity<>(new GlobalResponse<>(auth), HttpStatus.OK);
    }

}
