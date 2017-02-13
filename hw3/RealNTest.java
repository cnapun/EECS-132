import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Test class for RealN
 *
 * @author Nikil Pancha
 */
public class RealNTest {

    //maximum allowable error in double values
    private final double doubleDelta = 1e-13;

    /**
     * test RealN constructor and value getter
     */
    @Test
    public void testGetDoubleValue() {
        assertEquals("Test getter", 1.0, new RealN(1.0).getDoubleValue(), doubleDelta);
        assertEquals("Test constructor", 0.0, new RealN(1.0).getImaginaryPart(), doubleDelta);
    }

    /**
     * test RealN toString method
     */
    @Test
    public void testToString() {
        assertEquals("Negative RealN to string", "-1234.0", new RealN(-1234).toString());
        assertEquals("Positive RealN to string", "1234.0", new RealN(1234).toString());
    }
}