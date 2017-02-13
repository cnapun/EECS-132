/**
 * Class to represent a complex number
 *
 * @author Nikil Pancha
 */
public class ComplexN extends Number {

    //maximum double value error
    private final double doubleDelta = 1e-13;

    //real part of of the complex number
    private final double realPart;

    //imaginary part of the complex number
    private final double imaginaryPart;


    /**
     * creates a new complex number from its real and imaginary parts
     *
     * @param rVal double real part of the number
     * @param iVal double imaginary part of the number
     */
    public ComplexN(double rVal, double iVal) {
        this.imaginaryPart = iVal;
        this.realPart = rVal;
    }

    /**
     * getter for the real part of the number
     *
     * @return real part of the number as double
     */
    public double getRealPart() {
        return realPart;
    }

    /**
     * getter for the imaginary part of the number
     *
     * @return imaginary part of the number as double
     */
    public double getImaginaryPart() {
        return imaginaryPart;
    }

    /**
     * outputs a string representation of the object
     *
     * @return string representation of the complex number
     */
    @Override
    public String toString() {
        if (getImaginaryPart() >= 0) {
            return getRealPart() + " + " + getImaginaryPart() + "i";
        } else {
            return getRealPart() + " - " + -1.0 * getImaginaryPart() + "i";
        }
    }

    /**
     * compares the value of this number to another object
     *
     * @param o Object to compare instance to
     * @return true if the objects have the same value, otherwise false
     */
    @Override
    public boolean equals(Object o) {
        return (o instanceof ComplexN) && (getRealPart() - ((ComplexN) o).getRealPart() < doubleDelta) && (getImaginaryPart() - ((ComplexN) o).getImaginaryPart() < doubleDelta);
    }

}