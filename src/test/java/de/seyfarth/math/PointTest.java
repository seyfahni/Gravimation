package de.seyfarth.math;

import java.math.BigDecimal;
import org.junit.Test;
import static org.junit.Assert.*;

public class PointTest {
    
    private static final BigDecimal[] defaultValues = {
        new BigDecimal("-3.5"),
        new BigDecimal("7.2"),
        BigDecimal.ZERO,
        new BigDecimal("5.9"),
    };
    
    public PointTest() {
    }

    @Test
    public void createPointEmpty() {
        Point point = new Point();
    }
    
    @Test(expected = NullPointerException.class)
    public void createPointNull() {
        BigDecimal[] nullArray = null;
        Point point = new Point(nullArray);
    }
    
    @Test
    public void createPointNullValue() {
        Point actual = new Point(new BigDecimal("-3.5"), new BigDecimal("7.2"), null, new BigDecimal("5.9"));
        Point expected = new Point(defaultValues);
        assertEquals(expected, actual);
    }
    
    @Test
    public void createPointValid() {
        Point point = new Point(defaultValues);
    }
    
    @Test(expected = IndexOutOfBoundsException.class)
    public void getValueNegative() {
        final Point point = new Point(defaultValues);
        point.getValue(-1);
    }
    
    @Test
    public void getValueInBounds() {
        final Point point = new Point(defaultValues);
        assertEquals(defaultValues[1], point.getValue(1));
    }
    
    @Test
    public void getValueAboveBounds() {
        final Point point = new Point(defaultValues);
        assertEquals(BigDecimal.ZERO, point.getValue(10));
    }

    @Test
    public void getValuesOfZeroDimension() {
        final Point point = new Point();
        assertArrayEquals(new BigDecimal[] {}, point.getValues());
    }
    
    @Test
    public void getValuesNotZeroDimension() {
        final Point point = new Point(defaultValues);
        assertArrayEquals(defaultValues, point.getValues());
    }
    
    @Test
    public void getDimensionZero() {
        final Point point = new Point();
        assertEquals(0, point.getDimension());
    }
    
    @Test
    public void getDimensionNotZero() {
        final Point point = new Point(defaultValues);
        assertEquals(4, point.getDimension());
    }
    
    @Test
    public void toDimensionSame() {
        final Point point = new Point(defaultValues);
        final Point expected = new Point(defaultValues);
        final Point actual = point.toDimension(defaultValues.length);
        assertEquals(expected, actual);
    }

    @Test
    public void toDimensionSmaller() {
        final Point point = new Point(defaultValues);
        final Point actual = point.toDimension(defaultValues.length - 2);
        assertTrue(actual.getDimension() == defaultValues.length - 2);
    }

    @Test
    public void toDimensionBigger() {
        final Point point = new Point(defaultValues);
        final Point expected = new Point(defaultValues);
        final Point actual = point.toDimension(defaultValues.length + 2);
        assertEquals(expected, actual);
        assertTrue(actual.getDimension() == defaultValues.length + 2);
    }
    
    @Test
    public void moveNot() {
        final Point point = new Point(defaultValues);
        final Point expected = new Point(defaultValues);
        final Point actual = point.move(new Vector());
        assertEquals(expected, actual);
    }
    
    @Test
    public void moveNormally() {
        final Point point = new Point(defaultValues);
        final Vector vector = new Vector(new BigDecimal("2.1"), BigDecimal.ZERO, new BigDecimal("-3.9"), new BigDecimal("5.3"));
        final Point expected = new Point(new BigDecimal("-1.4"), new BigDecimal("7.2"), new BigDecimal("-3.9"), new BigDecimal("11.2"));
        final Point actual = point.move(vector);
        assertEquals(expected, actual);
    }
}
