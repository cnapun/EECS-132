/**
 * Class to do arithmetic operations on Numbers
 *
 * @author Nikil Pancha
 */
public abstract class Arithmetic {
    
    /**
     * Adds two Numbers
     *
     * @param number1 first NaturalN to add
     * @param number2 second NaturalN to add
     * @return NaturalN sum of the two numbers
     */
    public static NaturalN add(NaturalN number1, NaturalN number2) {
        return new NaturalN(number1.getIntValue() + number2.getIntValue());
    }
    
    /**
     * Adds two Numbers
     *
     * @param number1 first IntegerN to add
     * @param number2 second IntegerN to add
     * @return IntegerN sum of the two numbers
     */
    public static IntegerN add(IntegerN number1, IntegerN number2) {
        return new IntegerN(number1.getIntValue() + number2.getIntValue());
    }
    
    /**
     * Adds two Numbers
     *
     * @param number1 first RationalN to add
     * @param number2 second RationalN to add
     * @return RationalN sum of the two numbers
     */
    public static RationalN add(RationalN number1, RationalN number2) {
        return new RationalN(number1.getNumerator() * number2.getDenominator() + number2.getNumerator() * number1.getDenominator(), number1.getDenominator() * number2.getDenominator());
    }
    
    /**
     * Adds two Numbers
     *
     * @param number1 first RealN to add
     * @param number2 second RealN to add
     * @return RealN sum of the two numbers
     */
    public static RealN add(RealN number1, RealN number2) {
        return new RealN(number1.getDoubleValue() + number2.getDoubleValue());
    }
    
    /**
     * Adds two Numbers
     *
     * @param number1 first ComplexN to add
     * @param number2 second ComplexN to add
     * @return ComplexN sum of the two numbers
     */
    public static ComplexN add(ComplexN number1, ComplexN number2) {
        return new ComplexN(number1.getRealPart() + number2.getRealPart(), number1.getImaginaryPart() + number2.getImaginaryPart());
    }
    
    /**
     * Subtracts the second number from the first
     *
     * @param number1 NaturalN that is subtracted from
     * @param number2 NaturalN that is subtracted from number1
     * @return NaturalN difference of the two numbers
     * @throws ArithmeticException if the first number is less than the second number
     */
    public static NaturalN subtract(NaturalN number1, NaturalN number2) throws ArithmeticException {
        if (number2.getIntValue()>number1.getIntValue()){
            throw new ArithmeticException("error in subtraction: NaturalN must be nonnegative");
        }
        return new NaturalN(number1.getIntValue() - number2.getIntValue());
    }
    
    /**
     * Subtracts the second number from the first
     *
     * @param number1 IntegerN that is subtracted from
     * @param number2 IntegerN that is subtracted from number1
     * @return IntegerN difference of the two numbers
     */
    public static IntegerN subtract(IntegerN number1, IntegerN number2) {
        return new IntegerN(number1.getIntValue() - number2.getIntValue());
    }
    
    /**
     * Subtracts the second number from the first
     *
     * @param number1 RationalN that is subtracted from
     * @param number2 RationalN that is subtracted from number1
     * @return RationalN difference of the two numbers
     */
    public static RationalN subtract(RationalN number1, RationalN number2) {
        return new RationalN(number1.getNumerator() * number2.getDenominator() - number2.getNumerator() * number1.getDenominator(), number1.getDenominator() * number2.getDenominator());
    }
    
    /**
     * Subtracts the second number from the first
     *
     * @param number1 RealN that is subtracted from
     * @param number2 RealN that is subtracted from number1
     * @return RealN difference of the two numbers
     */
    public static RealN subtract(RealN number1, RealN number2) {
        return new RealN(number1.getDoubleValue() - number2.getDoubleValue());
    }
    
    /**
     * Subtracts the second number from the first
     *
     * @param number1 ComplexN that is subtracted from
     * @param number2 ComplexN that is subtracted from number1
     * @return ComplexN difference of the two numbers
     */
    public static ComplexN subtract(ComplexN number1, ComplexN number2) {
        return new ComplexN(number1.getRealPart() - number2.getRealPart(), number1.getImaginaryPart() - number2.getImaginaryPart());
    }
    
    /**
     * Multiplies two numbers
     *
     * @param number1 first NaturalN to multiply
     * @param number2 second NaturalN to multiply
     * @return NaturalN product of the two numbers
     */
    public static NaturalN multiply(NaturalN number1, NaturalN number2) {
        return new NaturalN(number1.getIntValue() * number2.getIntValue());
    }
    
    /**
     * Multiplies two numbers
     *
     * @param number1 first IntegerN to multiply
     * @param number2 second IntegerN to multiply
     * @return IntegerN product of the two numbers
     */
    public static IntegerN multiply(IntegerN number1, IntegerN number2) {
        return new IntegerN(number1.getIntValue() * number2.getIntValue());
    }
    
    /**
     * Multiplies two numbers
     *
     * @param number1 first RationalN to multiply
     * @param number2 second RationalN to multiply
     * @return RationalN product of the two numbers
     */
    public static RationalN multiply(RationalN number1, RationalN number2) {
        return new RationalN(number1.getNumerator() * number2.getNumerator(), number1.getDenominator() * number2.getDenominator());
    }
    
    /**
     * Multiplies two numbers
     *
     * @param number1 first RealN to multiply
     * @param number2 second RealN to multiply
     * @return RealN product of the two numbers
     */
    public static RealN multiply(RealN number1, RealN number2) {
        return new RealN(number1.getDoubleValue() * number2.getDoubleValue());
    }
    
    /**
     * Multiplies two numbers
     *
     * @param number1 first ComplexN to multiply
     * @param number2 second ComplexN to multiply
     * @return ComplexN product of the two numbers
     */
    public static ComplexN multiply(ComplexN number1, ComplexN number2) {
        return new ComplexN(realComplexMultiply(number1, number2), imagComplexMultiply(number1, number2));
    }
    
    /**
     * Divides the first number by the second
     *
     * @param number1 NaturalN to be divided
     * @param number2 NaturalN to divide by
     * @return NaturalN quotient of the two numbers
     * @throws ArithmeticException if the divisor is zero
     */
    public static NaturalN divide(NaturalN number1, NaturalN number2) throws ArithmeticException {
        if (number2.getRealPart() == 0.0 && number2.getImaginaryPart() == 0.0) {
            throw new ArithmeticException("Divide by zero error");
        }
        return new NaturalN(number1.getIntValue() / number2.getIntValue());
    }
    
    /**
     * Divides the first number by the second
     *
     * @param number1 IntegerN to be divided
     * @param number2 IntegerN to divide by
     * @return IntegerN quotient of the two numbers
     * @throws ArithmeticException if the divisor is zero
     */
    public static IntegerN divide(IntegerN number1, IntegerN number2) throws ArithmeticException {
        if (number2.getRealPart() == 0.0 && number2.getImaginaryPart() == 0.0) {
            throw new ArithmeticException("Divide by zero error");
        }
        return new IntegerN(number1.getIntValue() / number2.getIntValue());
    }
    
    /**
     * Divides the first number by the second
     *
     * @param number1 RationalN to be divided
     * @param number2 RationalN to divide by
     * @return RationalN quotient of the two numbers
     * @throws ArithmeticException if the divisor is zero
     */
    public static RationalN divide(RationalN number1, RationalN number2) throws ArithmeticException {
        if (number2.getRealPart() == 0.0 && number2.getImaginaryPart() == 0.0) {
            throw new ArithmeticException("Divide by zero error");
        }
        return new RationalN(number1.getNumerator() * number2.getDenominator(), number1.getDenominator() * number2.getNumerator());
    }
    
    /**
     * Divides the first number by the second
     *
     * @param number1 RealN to be divided
     * @param number2 RealN to divide by
     * @return RealN quotient of the two numbers
     * @throws ArithmeticException if the divisor is zero
     */
    public static RealN divide(RealN number1, RealN number2) throws ArithmeticException {
        if (number2.getRealPart() == 0.0 && number2.getImaginaryPart() == 0.0) {
            throw new ArithmeticException("Divide by zero error");
        }
        return new RealN(number1.getDoubleValue() / number2.getDoubleValue());
    }
    
    /**
     * Divides the first number by the second
     *
     * @param number1 ComplexN to be divided
     * @param number2 ComplexN to divide by
     * @return ComplexN quotient of the two numbers
     * @throws ArithmeticException if the divisor has a magnitude of zero
     */
    public static ComplexN divide(ComplexN number1, ComplexN number2) throws ArithmeticException {
        if (number2.getRealPart() == 0.0 && number2.getImaginaryPart() == 0.0) {
            throw new ArithmeticException("Divide by zero error");
        }
        ComplexN number2Conjugate = complexConjugate(number2);
        double denominator = realComplexMultiply(number2, number2Conjugate);
        return new ComplexN(realComplexMultiply(number1, number2Conjugate) / denominator, imagComplexMultiply(number1, number2Conjugate) / denominator);
    }
    
    /**
     * Finds the value of the real part of the product of two complex numbers
     *
     * @param number1 the first ComplexN to multiply
     * @param number2 the second ComplexN to multiply
     * @return double value of the real part of the product
     */
    private static double realComplexMultiply(ComplexN number1, ComplexN number2) {
        return number1.getRealPart() * number2.getRealPart() - number1.getImaginaryPart() * number2.getImaginaryPart();
    }
    
    /**
     * Finds the value of the imaginary part of the product of two complex numbers
     *
     * @param number1 the first ComplexN to multiply
     * @param number2 the second ComplexN to multiply
     * @return double value of the imaginary part of the product
     */
    private static double imagComplexMultiply(ComplexN number1, ComplexN number2) {
        return number1.getRealPart() * number2.getImaginaryPart() + number2.getRealPart() * number1.getImaginaryPart();
    }
    
    /**
     * Finds the complex conjugate of a ComplexN
     *
     * @param number the ComplexN to find the conjugate of
     * @return ComplexN that is the conjugate of the input
     */
    private static ComplexN complexConjugate(ComplexN number) {
        return new ComplexN(number.getRealPart(), -1.0 * number.getImaginaryPart());
    }
}