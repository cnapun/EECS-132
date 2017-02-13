import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Test class for ComplexN
 *
 * @author Nikil Pancha
 */
public class ComplexNTest {

    //maximum allowable error in double values
    private final double doubleDelta = 1e-13;

    /**
     * test real part getter and constructor
     */
    @Test
    public void testGetRealPart() {
        assertEquals("Real part getter", 10., new ComplexN(10., 10.).getRealPart(), doubleDelta);
    }

    /**
     * test imaginary part getter and constructor
     */
    @Test
    public void testGetImaginaryPart() {
        assertEquals("Imaginary part getter", -10., new ComplexN(10., -10.).getImaginaryPart(), doubleDelta);
    }

    /**
     * test toString for both positive and negative imaginary parts
     */
    @Test
    public void testToString() {
        assertEquals("Positive imaginary part to string", "0.0 + 0.0i", new ComplexN(0.0, 0.0).toString());
        assertEquals("Negative imaginary part to string", "0.0 - 1.0i", new ComplexN(0.0, -1.0).toString());
    }

    /**
     * test equals method
     */
    @Test
    public void testEquals() {
        assertEquals("Two identical ComplexN equal", new ComplexN(1, 2), new ComplexN(1, 2));
        assertFalse("Other object not equal", new ComplexN(1, 1).equals(""));
    }
}