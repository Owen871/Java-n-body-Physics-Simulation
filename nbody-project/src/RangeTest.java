/**
 * Owen Gagnon 9/11/2025
 */

import org.junit.*;
import static org.junit.Assert.*;

/**
 * Tests each public method of the Range class.
 */
public class RangeTest {

    private final Range uninitRange = new Range();
    private final Range singleValueRange = new Range(5.0);
    private final Range normalRange = new Range(2.0, 8.0);
    private final Range negativeRange = new Range(-10.0, -2.0);
    private final Range singletonRange = new Range(10.0, 10.0);
    private final Range spanRange = new Range(-30.5, 21.5);

    @Test
    public void testAddValue() {
        // Add to uninitialized
        assertEquals(new Range(7.0, 7.0), uninitRange.addValue(7.0));

        // Add value less than min
        assertEquals(new Range(0.0, 8.0), normalRange.addValue(0.0));

        // Add value greater than max
        assertEquals(new Range(2.0, 10.0), normalRange.addValue(10.0));

        // Add value within range
        assertEquals(new Range(2.0, 8.0), normalRange.addValue(5.0));
    }

    @Test
    public void testGetSpan() {
        assertEquals(0.0, uninitRange.getSpan(), 0.01);
        assertEquals(0.0, singleValueRange.getSpan(), 0.01);
        assertEquals(6.0, normalRange.getSpan(), 0.01);
        assertEquals(8.0, negativeRange.getSpan(), 0.01);
        assertEquals(0.0, singletonRange.getSpan(), 0.01);
        assertEquals(52.0, spanRange.getSpan(), 0.01);
    }

    @Test
    public void testContains() {
        // Uninitialized always false
        assertFalse(uninitRange.contains(0.0));
        assertFalse(uninitRange.contains(-2.0));
        assertFalse(uninitRange.contains(21.0));

        // Single value
        assertTrue(singleValueRange.contains(5.0));
        assertFalse(singleValueRange.contains(4.99));

        // Normal range
        assertTrue(normalRange.contains(2.0));
        assertTrue(normalRange.contains(8.0));
        assertTrue(normalRange.contains(5.0));
        assertFalse(normalRange.contains(1.99));
        assertFalse(normalRange.contains(8.01));

        // Negative range
        assertTrue(negativeRange.contains(-10.0));
        assertTrue(negativeRange.contains(-2.0));
        assertFalse(negativeRange.contains(-11.0));
        assertFalse(negativeRange.contains(0.0));
    }

    @Test
    public void testGetters() {
        // uninitRange
        assertFalse(uninitRange.isInitialized());

        // singleValueRange
        assertEquals(5.0, singleValueRange.getMin(), 0.01);
        assertEquals(5.0, singleValueRange.getMax(), 0.01);
        assertTrue(singleValueRange.isInitialized());

        // normalRange
        assertEquals(2.0, normalRange.getMin(), 0.01);
        assertEquals(8.0, normalRange.getMax(), 0.01);
        assertTrue(normalRange.isInitialized());

    }
}