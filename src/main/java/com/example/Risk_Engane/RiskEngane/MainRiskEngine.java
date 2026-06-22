package com.example.Risk_Engane.RiskEngane;

import com.example.Risk_Engane.Auth.AuthDto;
import com.example.Risk_Engane.BlockTarget.BlockRepo;
import com.example.Risk_Engane.BlockTarget.BlockService;
import com.example.Risk_Engane.Device.Device;
import com.example.Risk_Engane.ErrorHandling.CustomResponseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MainRiskEngine {

    private static final Logger logger = LoggerFactory.getLogger(MainRiskEngine.class);

    @Autowired
    private RiskDecision riskDecision;
    @Autowired
    private ListTypeDetector listTypeDetector;
    @Autowired
    private Utils utilsRiskEngine;
    @Autowired
    private InputScore inputScore;
    @Autowired
    private DeviceInfoScore deviceInfoScore;
    @Autowired
    private BlockRepo blockRepo;
    @Autowired
    BlockService blockService;

    public int beforeKnowing (AuthDto.Login loginRequest) {
        int score = 0;

        score += inputScore.calculateAllInput(loginRequest);

        RiskDecision.UserStatueResult result = riskDecision.makeDecision(score, false);

        logger.info("Result before knowing: {}", result);
        logger.info("Last result of score before knowing is: " + score);

        if (result == RiskDecision.UserStatueResult.BLOCK_DEVICE) {
            blockService.blockTarget(loginRequest.deviceName(), "Score");
        }

        if (result == RiskDecision.UserStatueResult.TRY_AFTER_5M) {
            blockService.temporaryBlock(loginRequest.deviceName(), "Score", 5);
        }

        if (result == RiskDecision.UserStatueResult.TRY_AFTER_12H) {
            int hour_12 = 60 * 12;
            blockService.temporaryBlock(loginRequest.deviceName(), "Score", hour_12);
        }

        return score;
    }

    public RiskSession.RiskListsType afterKnowing (AuthDto.Login loginRequest, Device device, int lastScore) {
        if (lastScore < 10) {
            lastScore = 0;
        }
        logger.info("Last score before doing after know function: {}", lastScore);
        int score = 0;
        score += lastScore;

        RiskSession.RiskListsType listsType = listTypeDetector.determineListType(loginRequest, device);

        score += deviceInfoScore.calculateAllDeviceInfo(loginRequest, device, listsType);
        logger.info("Total score result after knowing: {}", score);

        RiskDecision.UserStatueResult result = riskDecision.makeDecision(score, true);
        logger.info("Result after knowing: {}", result);

        if (result == RiskDecision.UserStatueResult.BLOCK_DEVICE) {
            blockService.blockTarget(loginRequest.deviceName(), "Score");
        }

        if (result == RiskDecision.UserStatueResult.OTP_CODE) {
            logger.info("We sent to you an OTP, check your email!");
            throw CustomResponseException.publicError("We sent to you an OTP, check your email!", 400);
        }

        return listsType;
    }

}


