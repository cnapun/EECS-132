import java.util.ListIterator;

/**
 * Class to represent and do operations on a strand of DNA
 *
 * @author Nikil Pancha
 */
public class DNA extends DoubleLinkedList<DNA.Base> implements Comparable<DNA> {
    /**
     * Enum to store the four possible values of a DNA Base
     */
    public enum Base {
        A('A'), C('C'), G('G'), T('T'); //four types of base possible

        public final char charRep; //character representation

        /**
         * Initialize the class with a character representation
         *
         * @param c character of the base
         */
        Base(char c) {
            this.charRep = c;
        }

        /**
         * Gets the character representation of the base in uppercase
         *
         * @return character representation of the base
         */
        public char getChar() {
            return charRep;
        }

        /**
         * Retrieves a base given a character representation
         *
         * @param c character of base to retrieve
         * @return Base that is the character input
         * @throws IllegalArgumentException if the character is not one of a,charRep,g,t,A,C,G,T
         */
        public static Base getBase(char c) throws IllegalArgumentException {
            if (c == 'A' || c == 'a') {
                return A;
            } else if (c == 'C' || c == 'c') {
                return C;
            } else if (c == 'G' || c == 'g') {
                return G;
            } else if (c == 'T' || c == 't') {
                return T;
            } else {
                throw new IllegalArgumentException(c + " is not a valid DNA base");
            }
        }

    }

    /**
     * Converts a string to DNA
     *
     * @param s string to convert
     * @return DNA with bases as given by the string
     * @throws IllegalArgumentException if there is an invalid character in the input string or the input is empty
     */
    public static DNA string2DNA(String s) throws IllegalArgumentException {
        if (s.length() == 0) {
            throw new IllegalArgumentException("Input may not be empty");
        } else {
            DNA output = new DNA();
            //add each character to output DNA
            for (char c : s.toCharArray()) {
                output.addToBack(Base.getBase(c));
            }
            return output;
        }
    }

    /**
     * Compares this DNA to another DNA
     *
     * @param dna DNA to compare to
     * @return -1 if this is less than the other, 0 if they are equal, 1 if one is greater than the other
     */
    @Override
    public int compareTo(DNA dna) {
        if (size() > dna.size()) {
            return 1;
        } else if (size() < dna.size()) {
            return -1;
        } else {
            ListIterator<Base> thisIt = this.iterator();
            ListIterator<Base> dnaIt = dna.iterator();
            //same length, only one needs to be checked
            while (thisIt.hasNext()) {
                Base thisBase = thisIt.next();
                Base dnaBase = dnaIt.next();
                if (thisBase.getChar() > dnaBase.getChar()) {
                    return 1;
                } else if (thisBase.getChar() < dnaBase.getChar()) {
                    return -1;
                }
            }
            return 0;
        }
    }

    /**
     * Remove numbases from the front of the input dna, and attach the remainder to this DNA
     *
     * @param dna      DNA to attach
     * @param numbases Number of bases to remove from the beginning of DNA
     */
    public void splice(DNA dna, int numbases) {
        //remove bases from the front of dna until numbases is reached or the list is empty
        for (int i = 0; i < numbases && !dna.isEmpty(); i++) {
            dna.removeFromFront();
        }
        this.append(dna);
    }

    /**
     * Produces a string representation of this DNA
     *
     * @return string representation of this DNA
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        //append each character
        for (Base base : this) {
            sb.append(base.getChar());
        }
        return sb.toString();
    }

    /**
     * Increase the position of an iterator by a given number of steps
     * <p>Does not check for bounds, and will fail if an invalid number of steps are given</p>
     *
     * @param iterator iterator to traverse
     * @param steps    number of steps to increase
     */
    private static void iteratorTraverse(ListIterator<?> iterator, int steps) {
        int i = 0;
        //continue until enough steps have been taken
        while (i++ < steps) {
            iterator.next();
        }
    }

    /**
     * Check if the two strands of dna overlap by the given amount in the given order
     *
     * @param dna1 dna to put on the left to compare
     * @param dna2 dna to put on the right to compare
     * @param n    number of bases to check
     * @return true if the rightmost n bases of dna1 are equal to the leftmost n bases of dna2, false otherwise
     */
    public static boolean overlaps(DNA dna1, DNA dna2, int n) {
        if (dna1.size() < n || dna2.size() < n) {
            return false;
        }
        ListIterator<Base> it1 = dna1.iterator();
        iteratorTraverse(it1, dna1.size() - n); //size was checked above, known to be a valid position
        ListIterator<Base> it2 = dna2.iterator();
        //if there is a point without overlap, return false
        while (it1.hasNext() && it2.hasNext()) {
            if (it1.next() != it2.next()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Get the DNA strand with the maximum overlap possible (either order)
     *
     * @param dna1 first dna to check
     * @param dna2 second dna to check
     * @return DNA that is the two previous DNA strands combined in the way that makes the returned DNA as short as possible
     */
    private static DNA maxOverlap(DNA dna1, DNA dna2) {
        int minSize = dna1.size(); //length of shorter sequence
        if (dna2.size() < minSize) {
            minSize = dna2.size();
        }
        boolean overlapFound = false;
        int oneTwo = minSize; //counter for overlap
        //count overlap with dna1 on left
        while (!overlapFound && oneTwo >= 0) {
            overlapFound = overlaps(dna1, dna2, oneTwo--);
        }
        overlapFound = false;
        int twoOne = minSize; //counter for overlap the other way
        //count overlap with dna1 on right
        while (!overlapFound && twoOne >= 0) {
            overlapFound = overlaps(dna2, dna1, twoOne--);
        }
        if (oneTwo > twoOne) {
            dna1.splice(dna2, oneTwo + 1);
            return dna1;
        } else {
            dna2.splice(dna1, twoOne + 1);
            return dna2;
        }

    }

    /**
     * Takes exactly two Strings that represent DNA, converts them to DNA, and calculates and prints the maxiumum overlap possible
     *
     * @param args two strings representing strands of DNA
     */
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Incorrect length of arguments");
        } else {
            try {
                DNA dna1 = DNA.string2DNA(args[0]);
                DNA dna2 = DNA.string2DNA(args[1]);
                System.out.println(maxOverlap(dna1, dna2).toString());
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
