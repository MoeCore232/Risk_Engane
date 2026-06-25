package com.example.Risk_Engane.ResetCode;

import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "trust_codes")
public class TrustCode {

    public enum TrustCodeType { FORGOT_PASSWORD, RISK_ENGINE }
    public enum StatusTrustCodeResult {SUCCESS, FAILED, WHILE_TRYING}

    private static LocalDateTime expiredDate = LocalDateTime.now().plusMinutes(1);

    @Id
    @GeneratedValue(generator = "UUID")
    @UuidGenerator
    private UUID id;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "start_in", nullable = false, updatable = false)
    private LocalDateTime startIn;

    @Column(name = "expired", nullable = false, updatable = false)
    private LocalDateTime expired;

    @Column(name = "code", nullable = false, unique = true)
    private String code;

    @Column(name = "is_success", nullable = false)
    private StatusTrustCodeResult isSuccess;

    @Column(name = "trust_code_type", nullable = false)
    private TrustCodeType trustCodeType;

    public TrustCode(UUID id, String email, LocalDateTime startIn, LocalDateTime expired,
                     String code, StatusTrustCodeResult isSuccess, TrustCodeType trustCodeType) {
        this.id = id;
        this.email = email;
        this.startIn = startIn;
        this.expired = expired;
        this.code = code;
        this.isSuccess = isSuccess;
        this.trustCodeType = trustCodeType;

    }
    private TrustCode() {}

    public static TrustCode createTrustCode (String email, String code, TrustCodeType type,
                                             int plusMinutes) {
        TrustCode trustCode = new TrustCode();
        trustCode.email = email;
        trustCode.startIn = LocalDateTime.now();
        trustCode.expired = LocalDateTime.now().plusMinutes(plusMinutes);
        trustCode.code = code;
        trustCode.isSuccess = StatusTrustCodeResult.WHILE_TRYING;
        trustCode.trustCodeType = type;
        return trustCode;
    }

    public void successTrustCodeResult () {
        this.isSuccess = StatusTrustCodeResult.SUCCESS;
    }

    public void filedTrustCodeResult () {
        this.isSuccess = StatusTrustCodeResult.FAILED;
    }

    public UUID getId () {return id;}
    public String getEmail () {return email;}
    public LocalDateTime getStartIn () {return startIn;}
    public LocalDateTime getExpired () {return expired;}
    public String getCode () {return code;}
    public StatusTrustCodeResult getIsSuccess () {return isSuccess;}
    public TrustCodeType getTrustCodeType () {return trustCodeType;}

}
