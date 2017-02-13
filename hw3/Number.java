/**
 * Abstract class that all numbers are a part of
 * 
 * @author Nikil Pancha
 */
public abstract class Number {

    /**
     * toString method to be overriden
     * @return  String representation of number
     */
    public abstract String toString();

    /**
     * equals method to be overriden
     * @param o     object to be compared to
     * @return      true if o is equal to the number, false otherwise
     */
    public abstract boolean equals(Object o);
}