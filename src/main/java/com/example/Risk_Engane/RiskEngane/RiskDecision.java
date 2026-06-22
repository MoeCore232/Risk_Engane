package com.example.Risk_Engane.RiskEngane;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class RiskDecision {

    private static final Logger logger = LoggerFactory.getLogger(RiskDecision.class);

    private int scoreResult = 0;
    private int blockedSteps = 3;

    private static final int BLOCK_DECISION = 100;
    private static final int TRY_AFTER_12H_DECISION = 70;
    private static final int TRY_AFTER_5M_DECISION = 40;
    private static final int OTP_CODE_DECISION = 20;

    public enum UserStatueResult {
        SUCCESS, OTP_CODE, TRY_AFTER_5M,
        TRY_AFTER_12H, BLOCK_DEVICE
    }

    public UserStatueResult makeDecision (int score, boolean isKnow) {

        scoreResult += score;
        logger.info("Score: {}", scoreResult);
        logger.info("Is know status: {}", isKnow);

        if (scoreResult >= BLOCK_DECISION) {
            this.blockedSteps = 3;
            this.scoreResult = 0;
            return UserStatueResult.BLOCK_DEVICE;
        }

        if (scoreResult >= TRY_AFTER_12H_DECISION && blockedSteps == 2) {
            this.blockedSteps -= 1;
            return UserStatueResult.TRY_AFTER_12H;
        }

        if (scoreResult >= TRY_AFTER_5M_DECISION && blockedSteps == 3) {
            this.blockedSteps -= 1;
            return UserStatueResult.TRY_AFTER_5M;
        }

        if (scoreResult >= OTP_CODE_DECISION && isKnow) {
            return UserStatueResult.OTP_CODE;
        }

        return UserStatueResult.SUCCESS;
    }

}


