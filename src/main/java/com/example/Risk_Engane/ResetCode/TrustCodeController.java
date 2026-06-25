package com.example.Risk_Engane.ResetCode;

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
import java.util.UUID;

@Controller
@RequestMapping("/api/trust-code")
public class TrustCodeController {

    @Autowired
    private TrustCodeService trustCodeService;

    @PostMapping("/is-code-valid")
    public ResponseEntity<GlobalResponse<UUID>> isCodeValid (@RequestBody TrustCodeDto.Code code) {
        UUID res = trustCodeService.isCodeValid(code);
        return new ResponseEntity<>(new GlobalResponse<>(res), HttpStatus.OK);
    }

    @GetMapping("/get-all-trust-codes")
    public ResponseEntity<GlobalResponse<List<TrustCode>>> getAllTrustCodes () {
        List<TrustCode> trustCodes = trustCodeService.getAllResetPasswords();
        return new ResponseEntity<>(new GlobalResponse<>(trustCodes), HttpStatus.OK);
    }

}
