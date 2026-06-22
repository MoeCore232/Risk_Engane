package com.example.Risk_Engane.RiskEngane;

import com.example.Risk_Engane.Auth.Auth;
import com.example.Risk_Engane.Auth.AuthDto;
import com.example.Risk_Engane.Auth.AuthRepo;
import com.example.Risk_Engane.ErrorHandling.CustomResponseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class InputScore {

    private static final Logger logger = LoggerFactory.getLogger(InputScore.class);

    private final AuthRepo authRepo;
    private final ScoreWeights scoreWeights;

    private static final ScoreWeights.WeightsType ValidInput = ScoreWeights.WeightsType.VALID_INPUT;
    private static final ScoreWeights.WeightsType NullInput = ScoreWeights.WeightsType.NULL_INPUT;
    private static final ScoreWeights.WeightsType FastInput = ScoreWeights.WeightsType.FAST_INPUT;

    public InputScore(AuthRepo authRepo, ScoreWeights scoreWeights) {
        this.authRepo = authRepo;
        this.scoreWeights = scoreWeights;
    }

    public int calculateNullInput (AuthDto.Login loginRequest) {
        String email = loginRequest.email();
        String pass = loginRequest.password();
        int nullInputScore = 0;
        if (email == null || email.trim().isEmpty() || pass == null || pass.trim().isEmpty()) {
            nullInputScore += scoreWeights.greyListWeights.get(NullInput);
            logger.info("Input is null, your score added: {}", nullInputScore);
        }
        return nullInputScore;
    }

    public int calculateValidInput (AuthDto.Login loginRequest) {
        int validInputScore = 0;
        Optional<Auth> findAuth = authRepo.findByEmail(loginRequest.email());
        if (findAuth.isEmpty()) {
            validInputScore += scoreWeights.greyListWeights.get(ValidInput);
            logger.info("Input is valid, your score added: {}", validInputScore);
        }
        return validInputScore;
    }

    public int calculateFastInput (AuthDto.Login loginRequest) {
        int fastInputScore = 0;
        int normalTypingSpeed = 4;
        if (loginRequest.loginTypingSpeed() <= normalTypingSpeed) {
            if (loginRequest.loginTypingSpeed() <= 0) {
                logger.info("Input is very fast, cant accept your login");
                throw CustomResponseException.unExpectedErrorOccurred();
            }
            fastInputScore += scoreWeights.greyListWeights.get(FastInput);
            logger.info("Input is very fast, your score added: {}", fastInputScore);
        }
        return fastInputScore;
    }

    public int calculateAllInput (AuthDto.Login loginRequest) {
        int score = 0;

        score += calculateNullInput(loginRequest);
        score += calculateValidInput(loginRequest);
        score += calculateFastInput(loginRequest);

        logger.info("Total before knowing score: {}", score);

        return score;
    }

}
