package com.example.Risk_Engane.ResetCode;

import com.example.Risk_Engane.Auth.AuthDto;
import com.example.Risk_Engane.Auth.AuthRepo;
import com.example.Risk_Engane.ErrorHandling.CustomResponseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class TrustCodeService {

    private static final Logger logger = LoggerFactory.getLogger(TrustCodeService.class);

    @Value("${spring.mail.username}")
    private String from;
    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private TrustCodeRepo trustCodeRepo;
    @Autowired
    private AuthRepo authRepo;
    @Autowired
    private GenerateRandomCode generateRandomCode;

    public List<TrustCode> getAllResetPasswords () {
        List<TrustCode> trustCodes = trustCodeRepo.findAll();
        return trustCodes;
    }

    public void generateTrustCode (AuthDto.Login login, int minutes) {
        String code = generateRandomCode.generateRandomCode();
        sendTokenForResetPassword(login.email(), code);
        TrustCode.TrustCodeType type = TrustCode.TrustCodeType.RISK_ENGINE;
        TrustCode trustCode = TrustCode.createTrustCode(login.email(), code, type, minutes);
        trustCodeRepo.save(trustCode);
        logger.info("Trust Code created successful!");
    }

    public UUID isCodeValid (TrustCodeDto.Code code) {
        TrustCode findCode = trustCodeRepo.findByCode(code.code())
                .orElseThrow(() -> CustomResponseException.badCredentials());

        if (findCode.getExpired().isBefore(LocalDateTime.now())) {
            throw CustomResponseException.expiredCode();
        }

        // محاكاة التوكين في عملية المصادقة
        return UUID.randomUUID();
    }

    public void isHasTrustCode (String email) {
    }

    public void sendTokenForResetPassword (String to, String token) {
        logger.info("From: {}, To: {}", from, to);
        SimpleMailMessage createMessage = new SimpleMailMessage();
        createMessage.setFrom(from);
        createMessage.setTo(to);
        createMessage.setSubject("Forgot Password!");
        createMessage.setText("OTP Code working very good!" + token);
        javaMailSender.send(createMessage);
    }

}
