package com.example.Risk_Engane.BlockTarget;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "blocks")
public class Block {

    @Id
    @GeneratedValue(generator = "UUID")
    @UuidGenerator
    private UUID id;

    @Column(name = "target_type", nullable = false)
    private String targetType;

    @Column(name = "target_value", nullable = false)
    private String targetValue;

    @Column(name = "auth_id")
    private UUID authId;

    @Column(name = "reason", nullable = false)
    private String reason;

    @Column(name = "is_permanent", nullable = false)
    private boolean isPermanent;

    @CreationTimestamp
    @Column(name = "blocked_at", nullable = false, updatable = false)
    private LocalDateTime blockedAt;

    @Column(name = "expired_at", updatable = false)
    private LocalDateTime expiredAt;

    @Column(name = "how_minutes", nullable = false)
    private int howMinutes;

    private Block (UUID id, String targetType, String targetValue, UUID authId, String reason,
                           boolean isPermanent, LocalDateTime blockedAt, LocalDateTime expiredAt,
                           int howMinutes) {
        this.id = id;
        this.targetType = targetType;
        this.targetValue = targetValue;
        this.authId = authId;
        this.reason = reason;
        this.isPermanent = isPermanent;
        this.blockedAt = blockedAt;
        this.expiredAt = expiredAt;
        this.howMinutes = howMinutes;
    }
    private Block () {}

    public static Block blockDevice (UUID authId, String targetValue, String reason, LocalDateTime eAt,
                                             boolean isPermanent, int howMinutes) {
        Block blockedTarget = new Block();
        blockedTarget.targetType = "Device";
        blockedTarget.targetValue = targetValue;
        blockedTarget.authId = authId;
        blockedTarget.reason = reason;
        blockedTarget.expiredAt = eAt;
        blockedTarget.isPermanent = isPermanent;
        blockedTarget.howMinutes = howMinutes;
        return blockedTarget;
    }

    public void permanentBlock () {
        this.isPermanent = true;
    }

    public UUID getId () {return id;}
    public String getTargetType () {return targetType;}
    public String getTargetValue () {return targetValue;}
    public UUID getAuthId () {return authId;}
    public String getReason () {return reason;}
    public boolean getIsPermanent () {return isPermanent;}
    public LocalDateTime getBlockedAt () {return blockedAt;}
    public LocalDateTime getExpiredAt () {return expiredAt;}
    public int getHowMinutes () {return howMinutes;}
}
