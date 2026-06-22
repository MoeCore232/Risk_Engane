package com.example.Risk_Engane.RiskEngane;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "risk_sessions")
public class RiskSession {

    public enum RiskListsType { WHITE, GREY, BLACK }

    @Id
    @GeneratedValue(generator = "UUID")
    @UuidGenerator
    private UUID id;

    @Column(name = "user_id")
    private UUID userId;

    @Column(name = "risk_list_type")
    private RiskListsType riskListType;

    @Column(name = "otp_sent")
    private boolean otpSent;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "device_id")
    private UUID deviceId;

    private RiskSession (UUID id, UUID userId, RiskListsType riskListType, boolean otpSent,
                         LocalDateTime createdAt, UUID deviceId) {
        this.id = id;
        this.userId = userId;
        this.riskListType = riskListType;
        this.otpSent = otpSent;
        this.createdAt = createdAt;
        this.deviceId = deviceId;
    }
    private RiskSession () {}

    public static RiskSession createRiskSession (UUID userId, RiskListsType riskListType, boolean otpSent,
                                                 UUID deviceId) {
        RiskSession riskSession = new RiskSession();
        riskSession.userId = userId;
        riskSession.riskListType = riskListType;
        riskSession.otpSent = otpSent;
        riskSession.deviceId = deviceId;
        return riskSession;
    }

    public UUID getId () {return id;}
    public UUID getUserId () {return userId;}
    public RiskListsType getRiskListType () {return riskListType;}
    public boolean getOtpSent () {return otpSent;}
    public LocalDateTime getCreatedAt () {return createdAt;}
    public UUID getDeviceId () {return deviceId;}

}

