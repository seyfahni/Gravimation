package de.seyfarth.math;

import java.math.BigDecimal;
import org.junit.Test;
import static org.junit.Assert.*;

public class VectorTest {
    
    private static final BigDecimal[] defaultValues = {
        new BigDecimal("-3.5"),
        new BigDecimal("7.2"),
        BigDecimal.ZERO,
        new BigDecimal("5.9"),
    };
    
    public VectorTest() {
    }

    @Test
    public void createVectorEmpty() {
        Vector vector = new Vector();
    }
    
    @Test(expected = NullPointerException.class)
    public void createVectorNull() {
        BigDecimal[] nullArray = null;
        Vector vector = new Vector(nullArray);
    }
    
    @Test
    public void createVectorNullValue() {
        Vector actual = new Vector(new BigDecimal("-3.5"), new BigDecimal("7.2"), null, new BigDecimal("5.9"));
        Vector expected = new Vector(defaultValues);
        assertEquals(expected, actual);
    }
    
    @Test
    public void createVectorValid() {
        Vector vector = new Vector(defaultValues);
    }
    
    @Test(expected = IndexOutOfBoundsException.class)
    public void getValueNegative() {
        final Vector vector = new Vector(defaultValues);
        vector.getValue(-1);
    }
    
    @Test
    public void getValueInBounds() {
        final Vector vector = new Vector(defaultValues);
        assertEquals(defaultValues[1], vector.getValue(1));
    }
    
    @Test
    public void getValueAboveBounds() {
        final Vector vector = new Vector(defaultValues);
        assertEquals(BigDecimal.ZERO, vector.getValue(10));
    }

    @Test
    public void getValuesOfZeroDimension() {
        final Vector vector = new Vector();
        assertArrayEquals(new BigDecimal[] {}, vector.getValues());
    }
    
    @Test
    public void getValuesNotZeroDimension() {
        final Vector vector = new Vector(defaultValues);
        assertArrayEquals(defaultValues, vector.getValues());
    }
    
    @Test
    public void getDimensionZero() {
        final Vector vector = new Vector();
        assertEquals(0, vector.getDimension());
    }
    
    @Test
    public void getDimensionNotZero() {
        final Vector vector = new Vector(defaultValues);
        assertEquals(4, vector.getDimension());
    }
    
    @Test
    public void addValuesSameDimension() {
        final Vector vector = new Vector(defaultValues);
        final Vector expected = new Vector(new BigDecimal("-3.2"), new BigDecimal("7.2"), new BigDecimal("2.1"), new BigDecimal("-0.3"));
        final Vector actual = vector.add(new BigDecimal("0.3"), BigDecimal.ZERO, new BigDecimal("2.1"), new BigDecimal("-6.2"));
        assertEquals(expected, actual);
    }
    
    @Test
    public void addValuesLowerDimension() {
        final Vector vector = new Vector(defaultValues);
        final Vector expected = new Vector(new BigDecimal("-5.6"), new BigDecimal("8.5"), BigDecimal.ZERO, new BigDecimal("5.9"));
        final Vector actual = vector.add(new BigDecimal("-2.1"), new BigDecimal("1.3"));
        assertEquals(expected, actual);
    }
    
    @Test
    public void addValuesHigherDimension() {
        final Vector vector = new Vector(defaultValues);
        final Vector expected = new Vector(new BigDecimal("-3.2"), new BigDecimal("-0.3"), new BigDecimal("2.1"), new BigDecimal("5.9"), new BigDecimal("3.5"), new BigDecimal("-2.9"));
        final Vector actual = vector.add(new BigDecimal("0.3"), new BigDecimal("-7.5"), new BigDecimal("2.1"), BigDecimal.ZERO, new BigDecimal("3.5"), new BigDecimal("-2.9"));
        assertEquals(expected, actual);
    }
    
    @Test
    public void subtractValuesSameDimension() {
        final Vector vector = new Vector(defaultValues);
        final Vector expected = new Vector(new BigDecimal("-3.2"), new BigDecimal("7.2"), new BigDecimal("2.1"), new BigDecimal("-0.3"));
        final Vector actual = vector.subtract(new BigDecimal("-0.3"), BigDecimal.ZERO, new BigDecimal("-2.1"), new BigDecimal("6.2"));
        assertEquals(expected, actual);
    }
    
    @Test
    public void subtractValuesLowerDimension() {
        final Vector vector = new Vector(defaultValues);
        final Vector expected = new Vector(new BigDecimal("-5.6"), new BigDecimal("8.5"), BigDecimal.ZERO, new BigDecimal("5.9"));
        final Vector actual = vector.subtract(new BigDecimal("2.1"), new BigDecimal("-1.3"));
        assertEquals(expected, actual);
    }
    
    @Test
    public void subtractValuesHigherDimension() {
        final Vector vector = new Vector(defaultValues);
        final Vector expected = new Vector(new BigDecimal("-3.2"), new BigDecimal("-0.3"), new BigDecimal("2.1"), new BigDecimal("5.9"), new BigDecimal("3.5"), new BigDecimal("-2.9"));
        final Vector actual = vector.subtract(new BigDecimal("-0.3"), new BigDecimal("7.5"), new BigDecimal("-2.1"), BigDecimal.ZERO, new BigDecimal("-3.5"), new BigDecimal("2.9"));
        assertEquals(expected, actual);
    }
    
    @Test
    public void multiplyByZero() {
        final Vector vector = new Vector(defaultValues);
        final Vector expected = new Vector();
        final Vector actual = vector.multiply(BigDecimal.ZERO);
        assertEquals(expected, actual);
    }

    @Test
    public void multiplyByOne() {
        final Vector vector = new Vector(defaultValues);
        final Vector expected = new Vector(new BigDecimal("-3.5"), new BigDecimal("7.2"), BigDecimal.ZERO, new BigDecimal("5.9"));
        final Vector actual = vector.multiply(BigDecimal.ONE);
        assertEquals(expected, actual);
    }

    @Test
    public void multiplyByTwo() {
        final Vector vector = new Vector(defaultValues);
        final Vector expected = new Vector(new BigDecimal("-7"), new BigDecimal("14.4"), BigDecimal.ZERO, new BigDecimal("11.8"));
        final Vector actual = vector.multiply(new BigDecimal("2"));
        assertEquals(expected, actual);
    }

    @Test
    public void multiplyByOneHalf() {
        final Vector vector = new Vector(defaultValues);
        final Vector expected = new Vector(new BigDecimal("-1.75"), new BigDecimal("3.6"), BigDecimal.ZERO, new BigDecimal("2.95"));
        final Vector actual = vector.multiply(new BigDecimal("0.5"));
        assertEquals(expected, actual);
    }

    @Test(expected = ArithmeticException.class)
    public void divideByZero() {
        final Vector vector = new Vector(defaultValues);
        vector.divide(BigDecimal.ZERO);
    }

    @Test
    public void divideByOne() {
        final Vector vector = new Vector(defaultValues);
        final Vector expected = new Vector(new BigDecimal("-3.5"), new BigDecimal("7.2"), BigDecimal.ZERO, new BigDecimal("5.9"));
        final Vector actual = vector.divide(BigDecimal.ONE);
        assertEquals(expected, actual);
    }

    @Test
    public void divideByTwo() {
        final Vector vector = new Vector(defaultValues);
        final Vector expected = new Vector(new BigDecimal("-1.75"), new BigDecimal("3.6"), BigDecimal.ZERO, new BigDecimal("2.95"));
        final Vector actual = vector.divide(new BigDecimal("2"));
        assertEquals(expected, actual);
    }

    @Test
    public void divideByOneHalf() {
        final Vector vector = new Vector(defaultValues);
        final Vector expected = new Vector(new BigDecimal("-7"), new BigDecimal("14.4"), BigDecimal.ZERO, new BigDecimal("11.8"));
        final Vector actual = vector.divide(new BigDecimal("0.5"));
        assertEquals(expected, actual);
    }

    @Test
    public void lenthIsZero() {
        final Vector vector = new Vector();
        assertTrue(new BigDecimal("0").compareTo(vector.length()) == 0);
    }
    
    @Test
    public void lenthNonZero() {
        final Vector vector = new Vector(new BigDecimal("3"), new BigDecimal("4"));
        assertTrue(new BigDecimal("5").compareTo(vector.length()) == 0);
    }
    
    @Test(expected = ArithmeticException.class)
    public void unitOfZeroVector() {
        final Vector vector = new Vector();
        vector.unit();
    }
    
    @Test
    public void unitOfNonZeroVector() {
        final Vector vector = new Vector(new BigDecimal("6"), new BigDecimal("8"));
        final Vector expected = new Vector(new BigDecimal("0.6"), new BigDecimal("0.8"));
        final Vector actual = vector.unit();
        assertEquals(expected, actual);
    }
    
    @Test
    public void toDimensionSame() {
        final Vector vector = new Vector(defaultValues);
        final Vector expected = new Vector(defaultValues);
        final Vector actual = vector.toDimension(defaultValues.length);
        assertEquals(expected, actual);
    }

    @Test
    public void toDimensionSmaller() {
        final Vector vector = new Vector(defaultValues);
        final Vector actual = vector.toDimension(defaultValues.length - 2);
        assertTrue(actual.getDimension() == defaultValues.length - 2);
    }

    @Test
    public void toDimensionBigger() {
        final Vector vector = new Vector(defaultValues);
        final Vector expected = new Vector(defaultValues);
        final Vector actual = vector.toDimension(defaultValues.length + 2);
        assertEquals(expected, actual);
        assertTrue(actual.getDimension() == defaultValues.length + 2);
    }

}
