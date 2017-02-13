/**
 * Class to represent a real number
 *
 * @author Nikil Pancha
 */
public class RealN extends ComplexN {

    /**
     * creates a new real number
     *
     * @param value double value of the new number
     */
    public RealN(double value) {
        super(value, 0.);
    }

    /**
     * getter method for value of the real number
     *
     * @return double value of the number
     */
    public double getDoubleValue() {
        return super.getRealPart();
    }

    /**
     * displays real number as a string
     *
     * @return string representation of real number
     */
    @Override
    public String toString() {
        return super.getRealPart() + "";
    }

}