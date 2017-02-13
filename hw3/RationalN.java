/**
 * Class to represent a Rational number of the form a/b, where a and b are coprime integers
 *
 * @author Nikil Pancha
 */
public class RationalN extends RealN {

    //numerator of the number
    private final int numerator;
    //denominator of the number
    private final int denominator;

    /**
     * creates new rational number with specified numerator and denominator
     *
     * @param numerator   integer numerator of rational number
     * @param denominator integer denominator of rational number
     * @throws ArithmeticException if denominator is equal to zero
     */
    public RationalN(int numerator, int denominator) throws ArithmeticException {
        super(1.0 * numerator / denominator);
        if (denominator == 0) {
            throw new ArithmeticException("Denominator must be nonzero");
        }
        int gcd;
        if (denominator < 0) { //make denominator positive
            numerator *= -1;
            denominator *= -1;
        }
        if (numerator < 0) { //this gcd algorithm only works with positive numbers
            gcd = gcd(-1 * numerator, denominator);
        } else {
            gcd = gcd(numerator, denominator);
        }

        //divide numerator and denominator by gcd to get to simplest form
        this.numerator = numerator / gcd;
        this.denominator = denominator / gcd;
    }

    /**
     * @return integer value of the numerator
     */
    public int getNumerator() {
        return numerator;
    }

    /**
     * @return integer value of the denominator
     */
    public int getDenominator() {
        return denominator;
    }

    /**
     * helper method to find gcd
     *
     * @param p first integer number
     * @param q second integer number
     * @return integer greatest common divisor of the two numbers
     */
    private static int gcd(int p, int q) {
        while (q != 0) {
            int tmp = q; //save q to later store to p
            q = p % q;
            p = tmp;
        }
        return p;
    }

    /**
     * represents the number as a string
     *
     * @return string representation of the number
     */
    public String toString() {
        if (getDenominator() == 1) {
            return getNumerator() + "";
        } else {
            return getNumerator() + "/" + getDenominator();
        }
    }

    /**
     * Works with more accuracy for rational numbers
     *
     * @param o Object to compare instance to
     * @return true if the objects have the same value, otherwise false
     */
    public boolean equals(Object o) {
        return (super.equals(o)) || ((o instanceof RationalN) && getNumerator() == ((RationalN) o).getNumerator() && getDenominator() == ((RationalN) o).getDenominator());
    }
}