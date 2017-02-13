import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Test class for {@link Arithmetic}
 *
 * @author Nikil Pancha
 */
public class ArithmeticTest {

    /**
     * test all Arithmetic.add methods
     */
    @Test
    public void testAdd() {
        ComplexN complex = new ComplexN(13, 5);     //ComplexN to use for calculations
        RealN real = new RealN(12.34);              //RealN to use for calculations
        RationalN rational = new RationalN(4, 5);   //RationalN to use for calculations
        IntegerN integer = new IntegerN(-2);        //IntegerN to use for calculations
        NaturalN natural = new NaturalN(2);         //NaturalN to use for calculations

        assertEquals("Sum two complex numbers", new ComplexN(26, 10), Arithmetic.add(complex, complex));    //necessary
        assertEquals("Sum real and real", new RealN(12.34 * 2), Arithmetic.add(real, real));                //necessary
        assertEquals("Sum rational and rational", new RationalN(8, 5), Arithmetic.add(rational, rational)); //necessary
        assertEquals("Sum integer and integer", new IntegerN(-4), Arithmetic.add(integer, integer));        //necessary
        assertEquals("Sum natural and natural", new NaturalN(4), Arithmetic.add(natural, natural));         //necessary

        assertEquals("Sum complex and real", new ComplexN(25.34, 5), Arithmetic.add(complex, real));
        assertEquals("Sum complex and rational", new ComplexN(13.8, 5), Arithmetic.add(complex, rational));
        assertEquals("Sum rational and complex", new ComplexN(13.8, 5), Arithmetic.add(rational, complex));
        assertEquals("Sum rational and real", new RealN(13.14), Arithmetic.add(rational, real));
        assertEquals("Sum real and rational", new RealN(13.14), Arithmetic.add(real, rational));
        assertEquals("Sum rational and natural", new RationalN(14, 5), Arithmetic.add(rational, natural));
        assertEquals("Sum natural and rational", new RationalN(14, 5), Arithmetic.add(natural, rational));
        assertEquals("Sum integer and natural", new IntegerN(0), Arithmetic.add(integer, natural));
        assertEquals("Sum natural and integer", new IntegerN(0), Arithmetic.add(natural, integer));
    }

    /**
     * test all Arithmetic.subtract methods
     */
    @Test
    public void testSubtract() {
        ComplexN complex = new ComplexN(13, 5);     //ComplexN to use for calculations
        RealN real = new RealN(12.34);              //RealN to use for calculations
        RationalN rational = new RationalN(4, 5);   //RationalN to use for calculations
        IntegerN integer = new IntegerN(-2);        //IntegerN to use for calculations
        NaturalN natural = new NaturalN(2);         //NaturalN to use for calculations

        assertEquals("Subtract complex and complex", new ComplexN(0, 0), Arithmetic.subtract(complex, complex));            //necessary
        assertEquals("Subtract real and real", new RealN(0.0), Arithmetic.subtract(real, real));                            //necessary
        assertEquals("Subtract rational and rational", new RationalN(0, 11234), Arithmetic.subtract(rational, rational));   //necessary
        assertEquals("Subtract integer and integer", new IntegerN(0), Arithmetic.subtract(integer, integer));               //necessary
        assertEquals("Subtract natural and natural", new NaturalN(0), Arithmetic.subtract(natural, natural));               //necessary

        assertEquals("Subtract complex and rational", new ComplexN(12.2, 5), Arithmetic.subtract(complex, rational));
        assertEquals("Subtract rational and complex", new ComplexN(-12.2, -5), Arithmetic.subtract(rational, complex));
        assertEquals("Subtract rational and real", new RealN(-11.54), Arithmetic.subtract(rational, real));
        assertEquals("Subtract real and rational", new RealN(11.54), Arithmetic.subtract(real, rational));
        assertEquals("Subtract integer and natural", new IntegerN(-4), Arithmetic.subtract(integer, natural));
        assertEquals("Subtract natural and integer", new IntegerN(4), Arithmetic.subtract(natural, integer));
        assertEquals("Subtract rational and natural", new RationalN(-6, 5), Arithmetic.subtract(rational, natural));
        assertEquals("Subtract natural and rational", new RationalN(6, 5), Arithmetic.subtract(natural, rational));

    }

    /**
     * test all Arithmetic.multiply methods
     */
    @Test
    public void testMultiply() {
        ComplexN complex = new ComplexN(4, -3);     //ComplexN to use for calculations
        RealN real = new RealN(0.5);                //RealN to use for calculations
        RationalN rational = new RationalN(1, 2);   //RationalN to use for calculations
        IntegerN integer = new IntegerN(-2);        //IntegerN to use for calculations
        NaturalN natural = new NaturalN(1);         //NaturalN to use for calculations

        assertEquals("Multiply complex and complex", new ComplexN(7, -24), Arithmetic.multiply(complex, complex));      //necessary
        assertEquals("Multiply real and real", new RealN(0.25), Arithmetic.multiply(real, real));                       //necessary
        assertEquals("Multiply rational and rational", new RationalN(1, 4), Arithmetic.multiply(rational, rational));   //necessary
        assertEquals("Multiply integer and integer", new IntegerN(4), Arithmetic.multiply(integer, integer));           //necessary
        assertEquals("Multiply natural and natural", new NaturalN(1), Arithmetic.multiply(natural, natural));           //necessary

        assertEquals("Multiply complex and real", new ComplexN(2, -1.5), Arithmetic.multiply(complex, real));
        assertEquals("Multiply complex and rational", new ComplexN(2, -1.5), Arithmetic.multiply(complex, rational));
        assertEquals("Multiply rational and real", new RealN(0.25), Arithmetic.multiply(rational, real));
        assertEquals("Multiply rational and natural", new RationalN(2, 4), Arithmetic.multiply(rational, natural));
        assertEquals("Multiply integer and natural", new IntegerN(-2), Arithmetic.multiply(integer, natural));

    }

    /**
     * test all Arithmetic.divide methods
     */
    @Test
    public void testDivide() {
        ComplexN complex = new ComplexN(1, 2);      //ComplexN to use for calculations
        RealN real = new RealN(0.5);                //RealN to use for calculations
        RationalN rational = new RationalN(1, 2);   //RationalN to use for calculations
        IntegerN integer = new IntegerN(-2);        //IntegerN to use for calculations
        NaturalN natural = new NaturalN(1);         //NaturalN to use for calculations

        assertEquals("Divide complex by complex", new ComplexN(0.5, 1.0), Arithmetic.divide(complex, new ComplexN(2, 0)));  //necessary
        assertEquals("Divide real by real", new RealN(1), Arithmetic.divide(real, real));                                   //necessary
        assertEquals("Divide rational by rational", new RationalN(12, 12), Arithmetic.divide(rational, rational));          //necessary
        assertEquals("Divide integer by integer", new IntegerN(1), Arithmetic.divide(integer, integer));                    //necessary
        assertEquals("Divide natural by natural", new NaturalN(1), Arithmetic.divide(natural, natural));                    //necessary

        assertEquals("Divide complex by real", new ComplexN(2, 4), Arithmetic.divide(complex, real));
        assertEquals("Divide real by complex", new ComplexN(0.1, -0.2), Arithmetic.divide(real, complex));
        assertEquals("Divide complex by rational", new ComplexN(2, 4), Arithmetic.divide(complex, rational));
        assertEquals("Divide rational by real", new RealN(1.0), Arithmetic.divide(rational, real));
        assertEquals("Divide rational by natural", new RationalN(1, 2), Arithmetic.divide(rational, natural));
        assertEquals("Divide integer by natural", new IntegerN(-2), Arithmetic.divide(integer, natural));

    }

    /**
     * test for divide by zero exception
     */
    @Test(expected = ArithmeticException.class)
    public void testDivideByZero() throws ArithmeticException {
        Arithmetic.divide(new ComplexN(50,-124), new NaturalN(0));
    }
    
    /**
     * test subtraction of natural numbers for exception
     */
    @Test(expected = ArithmeticException.class)
    public void testNegativeNaturalNSubtraction() {
        Arithmetic.subtract(new NaturalN(1), new NaturalN(100));
    }

}