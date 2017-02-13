/**
 * Class to represent an integer
 * 
 * @author Nikil Pancha
 */
public class IntegerN extends RationalN {

    /**
     * create a new integer with the specified value
     * @param value     value of new integer
     */
    public IntegerN(int value) {
        super(value, 1);
    }

    /**
     * method to get the value, more natural than getNumerator
     * @return  value of the integer
     */
    public int getIntValue() {
        return super.getNumerator();
    }
}