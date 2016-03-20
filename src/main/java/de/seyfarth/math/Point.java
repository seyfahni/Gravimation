package de.seyfarth.math;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Objects;

public final class Point {
    
    private final BigDecimal[] values;

    public Point(BigDecimal... values) {
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
    
    public BigDecimal[] getValues() {
        return Arrays.copyOf(values, values.length);
    }
    
    public Vector positionVector() {
        return new Vector(getValues());
    }
    
    public Point move(Vector vector) {
        final int maxDimension = Math.max(getDimension(), vector.getDimension());
        BigDecimal[] moved = new BigDecimal[maxDimension];
        for (int index = 0; index < moved.length; index++) {
            moved[index] = getValue(index).add(vector.getValue(index));
        }
        return new Point(moved);
    }
    
    public Point toDimension(int targetDimension) {
        return new Point(Arrays.copyOf(getValues(), targetDimension));
    }
    
    @Override
    public boolean equals(Object obj) {
        return obj instanceof Point
            && obj.hashCode() == this.hashCode()
            && valuesEquals((Point) obj);
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 61 * hash + positionVector().hashCode();
        return hash;
    }

    private boolean valuesEquals(Point point) {
        final int maxDimension = Math.max(getDimension(), point.getDimension());
        for (int index = 0; index < maxDimension; index++) {
            if (getValue(index).compareTo(point.getValue(index)) != 0)
                return false;
        }
        return true;
    }
    
    @Override
    public String toString() {
        return "Point: " + Arrays.toString(getValues());
    }
    
}
