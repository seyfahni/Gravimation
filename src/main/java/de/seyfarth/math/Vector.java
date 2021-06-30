package de.seyfarth.math;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.Objects;
import java.util.function.BiFunction;

public final class Vector {

    private static final MathContext DEFAULT_MATH_CONTEXT = new MathContext(1024, RoundingMode.HALF_UP);

    private final BigDecimal[] values;
    private final MathContext mathContext;

    public Vector(BigDecimal... values) {
        this(DEFAULT_MATH_CONTEXT, values);
    }

    public Vector(MathContext mathContext, BigDecimal... values) {
        this.mathContext = Objects.requireNonNull(mathContext);
        Objects.requireNonNull(values);
        this.values = new BigDecimal[values.length];
        for (int index = 0; index < values.length; index++) {
            if (values[index] != null) {
                this.values[index] = values[index];
            } else {
                this.values[index] = BigDecimal.ZERO;
            }
        }
    }

    public BigDecimal getValue(int dimension) {
        if (dimension < 0) {
            throw new IndexOutOfBoundsException("smaller than zero");
        } else if (dimension < getDimension()) {
            return getValues()[dimension];
        } else {
            return BigDecimal.ZERO;
        }
    }

    public int getDimension() {
        return getValues().length;
    }

    BigDecimal[] getValues() {
        return Arrays.copyOf(values, values.length);
    }

    public Vector add(BigDecimal... values) {
        return add(new Vector(mathContext, values));
    }

    public Vector add(Vector vector) {
        return combine(vector, BigDecimal::add);
    }

    public Vector subtract(BigDecimal... values) {
        return subtract(new Vector(mathContext, values));
    }

    public Vector subtract(Vector vector) {
        return combine(vector, BigDecimal::subtract);
    }

    public Vector multiply(BigDecimal scalar) {
        BigDecimal[] modified = new BigDecimal[getDimension()];
        for (int index = 0; index < modified.length; index++) {
            modified[index] = getValue(index).multiply(scalar);
        }
        return new Vector(mathContext, modified);
    }

    public Vector divide(BigDecimal scalar) {
        if (BigDecimal.ZERO.compareTo(scalar) == 0)
            throw new ArithmeticException("divide by zero");
        BigDecimal[] modified = new BigDecimal[getDimension()];
        for (int index = 0; index < modified.length; index++) {
            modified[index] = getValue(index).divide(scalar, mathContext);
        }
        return new Vector(mathContext, modified);
    }

    public BigDecimal length() {
        BigDecimal sum = BigDecimal.ZERO;
        for (BigDecimal value : getValues()) {
            sum = sum.add(value.multiply(value));
        }
        return sum.sqrt(mathContext);
    }

    public Vector unit() {
        return divide(length());
    }

    public Vector invert() {
        return multiply(new BigDecimal("-1"));
    }

    public Vector toDimension(int targetDimension) {
        return new Vector(mathContext, Arrays.copyOf(getValues(), targetDimension));
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Vector
                && obj.hashCode() == this.hashCode()
                && valuesEquals((Vector) obj);
    }

    private boolean valuesEquals(Vector vector) {
        final int maxDimension = Math.max(getDimension(), vector.getDimension());
        for (int index = 0; index < maxDimension; index++) {
            if (getValue(index).compareTo(vector.getValue(index)) != 0)
                return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + length().intValue();
        return hash;
    }

    @Override
    public String toString() {
        return "Vector: " + Arrays.toString(getValues());
    }

    private Vector combine(Vector vector, BiFunction<BigDecimal, BigDecimal, BigDecimal> function) {
        final int maxDimension = Math.max(getDimension(), vector.getDimension());
        BigDecimal[] combined = new BigDecimal[maxDimension];
        for (int index = 0; index < combined.length; index++) {
            combined[index] = function.apply(getValue(index), vector.getValue(index));
        }
        return new Vector(mathContext, combined);
    }
}
