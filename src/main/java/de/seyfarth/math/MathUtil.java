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
    
    public static BigDecimal root(BigDecimal A, final int N, final int SCALE) {
        BigDecimal x0 = BigDecimal.ZERO;
        BigDecimal x1 = new BigDecimal(Math.pow(A.doubleValue(), 1.0/N));
        BigDecimal n = new BigDecimal(N);
        BigDecimal nSubOne = n.subtract(BigDecimal.ONE);
        int NSubOne = N-1;
        while (!x0.equals(x1)) {
            x0 = x1;
            x1 = A.divide(x0.pow(NSubOne), SCALE, BigDecimal.ROUND_HALF_UP);
            x1 = x1.add(x0.multiply(nSubOne));
            x1 = x1.divide(n, SCALE, BigDecimal.ROUND_HALF_UP);

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
