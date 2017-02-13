
/**
 * Class to represent a positive integer
 * 
 * @author Nikil Pancha
 */
public class NaturalN extends IntegerN {
    /**
     * constructor for natural number
     *
     * @param n     int to be made into NaturalN
     * @throws ArithmeticException  if a negative number is input
     */
    public NaturalN(int n) throws ArithmeticException {
        super(n);
        if (n < 0) {
            throw new ArithmeticException("Natural Number must be non-negative");
        }
    }
}
