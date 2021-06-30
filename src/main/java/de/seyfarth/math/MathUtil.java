package de.seyfarth.math;

import java.math.BigDecimal;
import java.math.MathContext;

@SuppressWarnings("PMD.AvoidDecimalLiteralsInBigDecimalConstructor")
public class MathUtil {

    public static BigDecimal root(BigDecimal A, final int N, final MathContext mathContext) {
        BigDecimal x0 = BigDecimal.ZERO;
        BigDecimal x1 = BigDecimal.valueOf(Math.pow(A.doubleValue(), 1.0 / N));
        BigDecimal n = new BigDecimal(N);
        BigDecimal nSubOne = n.subtract(BigDecimal.ONE);
        int NSubOne = N - 1;
        while (!x0.equals(x1)) {
            x0 = x1;
            x1 = A.divide(x0.pow(NSubOne), mathContext);
            x1 = x1.add(x0.multiply(nSubOne));
            x1 = x1.divide(n, mathContext);

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
