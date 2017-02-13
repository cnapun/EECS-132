import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Test class for IntegerN
 *
 * @author Nikil Pancha
 */
public class IntegerNTest {

    @Test
    public void testGetIntValue() throws Exception {
        assertEquals("Test IntegerN getter", 123, new IntegerN(123).getIntValue());
        assertEquals("Test IntegerN inherited getNumerator", 123, new IntegerN(123).getNumerator());
    }
}