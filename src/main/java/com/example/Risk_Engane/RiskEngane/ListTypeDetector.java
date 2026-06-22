package com.example.Risk_Engane.RiskEngane;

import com.example.Risk_Engane.Auth.AuthDto;
import com.example.Risk_Engane.Device.Device;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ListTypeDetector {

    private static final Logger logger = LoggerFactory.getLogger(ListTypeDetector.class);

    private static final RiskSession.RiskListsType WHITE = RiskSession.RiskListsType.WHITE;
    private static final RiskSession.RiskListsType GREY = RiskSession.RiskListsType.GREY;
    private static final RiskSession.RiskListsType BLACK = RiskSession.RiskListsType.BLACK;

    public RiskSession.RiskListsType determineListType (AuthDto.Login loginRequest, Device lastDevice) {

        String newDevDev = loginRequest.deviceName();
        String newDevLoc = loginRequest.location();
        String newDevNet = loginRequest.network();
        String newDevIp = loginRequest.ipAddress();

        String lastDevDev = lastDevice.getDeviceName();
        String lastDevLoc = lastDevice.getLocation();
        String lastDevNet = lastDevice.getNetwork();
        String lastDevIp = lastDevice.getIpAddress();

        if (newDevDev.equals(lastDevDev) && newDevLoc.equals(lastDevLoc)
                && newDevNet.equals(lastDevNet) && newDevIp.equals(lastDevIp)) {
            logger.info("You got white list info did not change");
            return WHITE;
        }
        if (!newDevDev.equals(lastDevDev) && !newDevLoc.equals(lastDevLoc)
                && !newDevNet.equals(lastDevNet) && !newDevIp.equals(lastDevIp)) {
            logger.info("You got black list info has been changed");
            return BLACK;
        }

        if (!newDevDev.equals(lastDevDev)) {
            logger.info("You got black list device is not the same");
            return BLACK;
        }
        if (!newDevLoc.equals(lastDevLoc)) {
            logger.info("You got grey list location is not the same");
            return GREY;
        }
        if (!newDevNet.equals(lastDevNet)) {
            logger.info("You got grey list network is not the same");
            return GREY;
        }
        if (!newDevIp.equals(lastDevIp)) {
            logger.info("You got grey list ip is not the same");
            return GREY;
        }
        else {
            logger.info("You got black list in else");
            return BLACK;
        }
    }

}
