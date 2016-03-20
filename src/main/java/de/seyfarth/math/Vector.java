package de.seyfarth.math;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.Objects;
import java.util.function.BiFunction;

public final class Vector {
    
    private final BigDecimal[] values;

    public Vector(BigDecimal... values) {
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
        return add(new Vector(values));
    }

    public Vector add(Vector vector) {
        return combine(vector, (a,b) -> a.add(b));
    }
    
    public Vector subtract(BigDecimal... values) {
        return subtract(new Vector(values));
    }

    public Vector subtract(Vector vector) {
        return combine(vector, (a,b) -> a.subtract(b));
    }
    
    public Vector multiply(BigDecimal scalar) {
        BigDecimal[] modified = new BigDecimal[getDimension()];
        for (int index = 0; index < modified.length; index++) {
            modified[index] = getValue(index).multiply(scalar);
        }
        return new Vector(modified);
    }

    public Vector divide(BigDecimal scalar) {
        if (BigDecimal.ZERO.compareTo(scalar) == 0)
            throw new ArithmeticException("divide by zero");
        BigDecimal[] modified = new BigDecimal[getDimension()];
        for (int index = 0; index < modified.length; index++) {
            modified[index] = getValue(index).divide(scalar, 1024, RoundingMode.HALF_UP);
        }
        return new Vector(modified);
    }
    
    public BigDecimal length() {
        BigDecimal sum = BigDecimal.ZERO;
        for(BigDecimal value : getValues()) {
            sum = sum.add(value.multiply(value));
        }
        return MathUtil.sqrt(sum, 1024);
    }
    
    public Vector unit() {
        return divide(length());
    }
    
    public Vector invert() {
        return multiply(new BigDecimal("-1"));
    }
    
    public Vector toDimension(int targetDimension) {
        return new Vector(Arrays.copyOf(getValues(), targetDimension));
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
        return new Vector(combined);
    }
}
