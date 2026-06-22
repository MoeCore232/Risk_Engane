package com.example.Risk_Engane.RiskEngane;

import com.example.Risk_Engane.Auth.AuthDto;
import com.example.Risk_Engane.Device.Device;
import com.example.Risk_Engane.ErrorHandling.CustomResponseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DeviceInfoScore {

    private static final Logger logger = LoggerFactory.getLogger(DeviceInfoScore.class);

    @Autowired
    private ScoreWeights scoreWeights;
    @Autowired
    private Utils utilsRiskEngine;

    private static final RiskSession.RiskListsType WHITE = RiskSession.RiskListsType.WHITE;
    private static final RiskSession.RiskListsType GREY = RiskSession.RiskListsType.GREY;
    private static final RiskSession.RiskListsType BLACK = RiskSession.RiskListsType.BLACK;
    private static final ScoreWeights.WeightsType NewDevice = ScoreWeights.WeightsType.NEW_DEVICE;
    private static final ScoreWeights.WeightsType NewLocation = ScoreWeights.WeightsType.NEW_LOCATION;
    private static final ScoreWeights.WeightsType NewNetwork = ScoreWeights.WeightsType.NEW_NETWORK;
    private static final ScoreWeights.WeightsType NewIP = ScoreWeights.WeightsType.NEW_IP;

    public int calculateDevice (AuthDto.Login login, Device device, RiskSession.RiskListsType listsType) {
        int score = 0;
        if (!login.deviceName().equals(device.getDeviceName())) {
            int WS = scoreWeights.whiteListWeights.get(NewDevice);
            int GS = scoreWeights.greyListWeights.get(NewDevice);
            int BS = scoreWeights.blackListWeights.get(NewDevice);
            score += utilsRiskEngine.resultByListType(listsType, WS, GS, BS);
            logger.info("Device is not the same, your score added: {}", score);
        }
        return score;
    }

    public int calculateLocation (AuthDto.Login login, Device device, RiskSession.RiskListsType listsType) {
        int score = 0;
        if (!login.location().equals(device.getLocation())) {
            int WS = scoreWeights.whiteListWeights.get(NewLocation);
            int GS = scoreWeights.greyListWeights.get(NewLocation);
            int BS = scoreWeights.blackListWeights.get(NewLocation);
            score += utilsRiskEngine.resultByListType(listsType, WS, GS, BS);
            logger.info("Location is not the same, your score added: {}", score);
        }
        return score;
    }

    public int calculateNetwork (AuthDto.Login login, Device device, RiskSession.RiskListsType listsType) {
        int score = 0;
        if (!login.network().equals(device.getNetwork())) {
            int WS = scoreWeights.whiteListWeights.get(NewNetwork);
            int GS = scoreWeights.greyListWeights.get(NewNetwork);
            int BS = scoreWeights.blackListWeights.get(NewNetwork);
            score += utilsRiskEngine.resultByListType(listsType, WS, GS, BS);
            logger.info("Network is not the same, your score added: {}", score);
        }
        return score;
    }

    public int calculateIp (AuthDto.Login login, Device device, RiskSession.RiskListsType listsType) {
        int score = 0;
        if (!login.ipAddress().equals(device.getIpAddress())) {
            int WS = scoreWeights.whiteListWeights.get(NewIP);
            int GS = scoreWeights.greyListWeights.get(NewIP);
            int BS = scoreWeights.blackListWeights.get(NewIP);
            score += utilsRiskEngine.resultByListType(listsType, WS, GS, BS);
            logger.info("IP is not the same, your score added: {}", score);
        }
        return score;
    }

    public int calculateAllDeviceInfo (AuthDto.Login loginRequest, Device device, RiskSession.RiskListsType listsType) {
        int score = 0;

        score += calculateDevice(loginRequest, device, listsType);
        score += calculateLocation(loginRequest, device, listsType);
        score += calculateNetwork(loginRequest, device, listsType);
        score += calculateIp(loginRequest, device, listsType);

        logger.info("Total score before list type filter: {}", score);

        if (listsType == BLACK) {
            score += 10;
        } else if (listsType == WHITE) {
            score -= 10;
        } else if (listsType == GREY) {
            return score;
        } else {
            throw CustomResponseException.unExpectedErrorOccurred();
        }
        logger.info("Total score after list type filter: {}", score);
        return score;
    }
}