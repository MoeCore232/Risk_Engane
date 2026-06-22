package com.example.Risk_Engane.RiskEngane;

import com.example.Risk_Engane.Auth.AuthRepo;
import com.example.Risk_Engane.ErrorHandling.CustomResponseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Utils {

    private static final Logger logger = LoggerFactory.getLogger(Utils.class);

    @Autowired
    private  AuthRepo authRepo;
    @Autowired
    private  RiskSessionRepo riskSessionsRepo;

    private static final RiskSession.RiskListsType WHITE = RiskSession.RiskListsType.WHITE;
    private static final RiskSession.RiskListsType GREY = RiskSession.RiskListsType.GREY;
    private static final RiskSession.RiskListsType BLACK = RiskSession.RiskListsType.BLACK;

    public int resultByListType (RiskSession.RiskListsType type,
                                  int whiteScore, int greyScore, int blackScore) {
        if (type == WHITE) return whiteScore;
        if (type == GREY) return greyScore;
        if (type == BLACK) return blackScore;
        else throw CustomResponseException.unExpectedErrorOccurred();
    }

}
