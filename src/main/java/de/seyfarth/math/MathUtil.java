package de.seyfarth.math;

import java.math.BigDecimal;

public class MathUtil {

    public static BigDecimal TWO = BigDecimal.valueOf(2);

    public static BigDecimal sqrt(BigDecimal A, final int SCALE) {
        BigDecimal x0 = BigDecimal.ZERO;
        BigDecimal x1 = new BigDecimal(Math.sqrt(A.doubleValue()));
        while (!x0.equals(x1)) {
            x0 = x1;
            x1 = A.divide(x0, SCALE, BigDecimal.ROUND_HALF_UP);
            x1 = x1.add(x0);
            x1 = x1.divide(TWO, SCALE, BigDecimal.ROUND_HALF_UP);

        }
        return x1;
    }
    
    public static Vector movingVector(Point from, Point to) {
        Vector fromVector = from.positionVector();
        Vector toVector = to.positionVector();
        return toVector.subtract(fromVector);
    }

    private MathUtil() {
    }
}
