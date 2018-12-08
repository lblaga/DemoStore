package com.lblaga.demostore.misc;

import java.math.BigDecimal;

/**
 * Double rounding utility
 */
public class Round {
    public static Double round(Double number, int precision) {
        return (new BigDecimal(Double.toString(number)).setScale(precision, BigDecimal.ROUND_HALF_UP)).doubleValue();

    }
}
