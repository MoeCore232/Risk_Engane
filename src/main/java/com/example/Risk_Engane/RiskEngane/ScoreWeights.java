package com.example.Risk_Engane.RiskEngane;

import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ScoreWeights {

    public enum WeightsType {
        NEW_DEVICE,
        NEW_LOCATION,
        NEW_NETWORK,
        NEW_IP,
        NULL_INPUT,
        FAST_INPUT,
        VALID_INPUT
    }

    public final Map<WeightsType, Integer> whiteListWeights = Map.of(
            WeightsType.NEW_DEVICE, 3,
            WeightsType.NEW_LOCATION, 2,
            WeightsType.NEW_NETWORK, 2,
            WeightsType.NEW_IP, 2,

            WeightsType.VALID_INPUT, 5,
            WeightsType.NULL_INPUT, 5,
            WeightsType.FAST_INPUT, 3
    );

    public final Map<WeightsType, Integer> greyListWeights = Map.of(
            WeightsType.NEW_DEVICE, 5,
            WeightsType.NEW_LOCATION, 3,
            WeightsType.NEW_NETWORK, 3,
            WeightsType.NEW_IP, 3,

            WeightsType.VALID_INPUT, 7,
            WeightsType.NULL_INPUT, 7,
            WeightsType.FAST_INPUT, 7
    );

    public final Map<WeightsType, Integer> blackListWeights = Map.of(
            WeightsType.NEW_DEVICE, 7,
            WeightsType.NEW_LOCATION, 5,
            WeightsType.NEW_NETWORK, 5,
            WeightsType.NEW_IP, 5,

            WeightsType.VALID_INPUT, 10,
            WeightsType.NULL_INPUT, 10,
            WeightsType.FAST_INPUT, 10
    );

}