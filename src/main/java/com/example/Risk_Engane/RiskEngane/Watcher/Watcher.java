package com.example.Risk_Engane.RiskEngane.Watcher;

import com.example.Risk_Engane.RiskEngane.RiskSession;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UuidGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Entity
@Table(name = "watchers")
public class Watcher {

    private static final Logger logger = LoggerFactory.getLogger(Watcher.class);
    private static final LocalDateTime now = LocalDateTime.now();
    private static final LocalDateTime endGreyWatcher = LocalDateTime.now().minusDays(2);
    private static final LocalDateTime endBlackWatcher = LocalDateTime.now().minusDays(3);

    @Id
    @GeneratedValue(generator = "UUID")
    @UuidGenerator
    private UUID id;

    @Column(name = "auth_id", nullable = false)
    private UUID authId;

    @CreationTimestamp
    @Column(name = "start_watching", nullable = false)
    private LocalDateTime startWatching;

    @Column(name = "end_watching", nullable = false)
    private LocalDateTime endWatching;

    @Column(name = "score", nullable = false)
    private int score;

    @Column(name = "list_type", nullable = false)
    private RiskSession.RiskListsType listsType;

    @Column(name = "daily_mistake", nullable = false)
    private int dailyMistake;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false, updatable = false)
    private LocalDateTime updatedAt;

    public Watcher (UUID id, UUID userId, LocalDateTime startWatching, LocalDateTime endWatching,
                        int score, RiskSession.RiskListsType listsType, int dailyMistake, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.authId = userId;
        this.startWatching = startWatching;
        this.endWatching = endWatching;
        this.score = score;
        this.listsType = listsType;
        this.dailyMistake = dailyMistake;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
    private Watcher () {}

    public static Optional<Watcher> createRiskWatcher (RiskSession.RiskListsType listsType, UUID authId) {
        if (listsType == RiskSession.RiskListsType.WHITE) {
            logger.info("No risk watcher, list type is: {}", listsType);
            return Optional.empty();
        }
        Watcher riskWatcher = new Watcher();
        riskWatcher.authId = authId;
        riskWatcher.startWatching = now;
        if (listsType == RiskSession.RiskListsType.BLACK) {
            riskWatcher.endWatching = endBlackWatcher;
        }
        if (listsType == RiskSession.RiskListsType.GREY) {
            riskWatcher.endWatching = endGreyWatcher;
        }
        riskWatcher.listsType = listsType;
        riskWatcher.score = 0;
        riskWatcher.dailyMistake = 0;
        riskWatcher.updatedAt = now;
        logger.info("Risk watcher created successfully, list type is: {}", listsType);
        return Optional.of(riskWatcher);
    }

    public static Watcher updateRiskWatcher (Watcher riskWatcher) {
        riskWatcher.startWatching = now;
        if (riskWatcher.listsType == RiskSession.RiskListsType.BLACK) {
            riskWatcher.endWatching = endBlackWatcher;
        }
        if (riskWatcher.listsType == RiskSession.RiskListsType.GREY) {
            riskWatcher.endWatching = endGreyWatcher;
        }
        riskWatcher.score = 0;
        riskWatcher.dailyMistake = 0;
        riskWatcher.updatedAt = now;
        logger.info("Risk Watcher updated successfully!");
        return riskWatcher;
    }

    public UUID getId () {return id;}
    public UUID getAuthId () {return authId;}
    public LocalDateTime getStartWatching () {return startWatching;}
    public LocalDateTime getEndWatching () {return endWatching;}
    public int getScore () {return score;}
    public RiskSession.RiskListsType getListsType () {return listsType;}
    public int getDailyMistake () {return dailyMistake;}
    public LocalDateTime getCreatedAt () {return createdAt;}
    public LocalDateTime getUpdatedAt () {return updatedAt;}
}
