import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Test class for NaturalN
 *
 * @author Nikil Pancha
 */
public class NaturalNTest {


    /**
     * test constructor and assignment of variables
     */
    @Test
    public void testConstructorNoError() {
        NaturalN natural = new NaturalN(111);
        assertEquals("NaturalN constructor test", 111, natural.getIntValue());
    }

    /**
     * test constructor for error with negative number
     */
    @Test(expected = ArithmeticException.class)
    public void testConstructorError() {
        new NaturalN(-1);
    }
}