import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Test class for RationalN
 *
 * @author Nikil Pancha
 */
public class RationalNTest {

    /**
     * test RationalN for simplifying fraction properly in numerator
     */
    @Test
    public void testGetNumerator() {
        RationalN num1 = new RationalN(12, 5);
        assertEquals("Simplest form already", 12, num1.getNumerator());
        RationalN num2 = new RationalN(12, -4);
        assertEquals("Not simplest form", -3, num2.getNumerator());
    }

    /**
     * test RationalN for simplifying fraction properly in denominator
     */
    @Test
    public void testGetDenominator() {
        RationalN num1 = new RationalN(16, 5);
        assertEquals("Simplest form already", 5, num1.getDenominator());
        RationalN num2 = new RationalN(-12, -8);
        assertEquals("Not simplest form", 2, num2.getDenominator());
    }

    /**
     * test toString method
     */
    @Test
    public void testToString() {
        assertTrue("denominator greater than 1", "-16/5".equals(new RationalN(16, -5).toString()));
        assertTrue("denominator is 1", "5".equals(new RationalN(-15, -3).toString()));
    }

    /**
     * test equals method
     */
    @Test
    public void testEquals() {
        RationalN num1 = new RationalN(16, -5);
        assertTrue("Different negatives", num1.equals(new RationalN(-16, 5)));
        RationalN num2 = new RationalN(10, 2);
        assertTrue("Same simplest form, different starting", num2.equals(new RationalN(100, 20)));
    }

    /**
     * test saving RationalN as double value
     */
    @Test
    public void testGetDoubleValue() {
        assertEquals("Rational Number converted to double", -0.5, new RationalN(4, -8).getDoubleValue(), 1e-13);
    }

    /**
     * test constructor for error when denominator is zero
     */
    @Test(expected = ArithmeticException.class)
    public void testArithmeticException() {
        new RationalN(0xababff, 0);
    }
}