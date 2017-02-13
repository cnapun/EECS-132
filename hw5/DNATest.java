import org.junit.Test;
import org.junit.internal.runners.model.EachTestNotifier;

import java.util.IllegalFormatCodePointException;
import java.util.Iterator;

import static org.junit.Assert.*;

/**
 * Test class for DNA
 *
 * @author Nikil Pancha
 */
public class DNATest {

    /**
     * Test enum Base
     */
    @Test(expected = IllegalArgumentException.class)
    public void testBase() {
        assertEquals("Get character for base A", 'A', DNA.Base.A.getChar());
        assertEquals("Get character for base C", 'C', DNA.Base.C.getChar());
        assertEquals("Get character for base G", 'G', DNA.Base.G.getChar());
        assertEquals("Get character for base T", 'T', DNA.Base.T.getChar());
        assertEquals("Get base A lower", DNA.Base.A, DNA.Base.getBase('a'));
        assertEquals("Get base A upper", DNA.Base.A, DNA.Base.getBase('A'));
        assertEquals("Get base C lower", DNA.Base.C, DNA.Base.getBase('c'));
        assertEquals("Get base C upper", DNA.Base.C, DNA.Base.getBase('C'));
        assertEquals("Get base G lower", DNA.Base.G, DNA.Base.getBase('g'));
        assertEquals("Get base G upper", DNA.Base.G, DNA.Base.getBase('G'));
        assertEquals("Get base T lower", DNA.Base.T, DNA.Base.getBase('t'));
        assertEquals("Get base T upper", DNA.Base.T, DNA.Base.getBase('T'));
        DNA.Base.getBase(' ');
    }

    /**
     * Test functionality of compareTo
     */
    @Test
    public void testCompareTo() {
        DNA dna1 = DNA.string2DNA("ACGTCGA");
        DNA dna2 = DNA.string2DNA("ACGTCGT");
        DNA dna3 = DNA.string2DNA("A");
        assertTrue("Equal to", dna1.compareTo(dna1) == 0);
        assertTrue("Greater than", dna2.compareTo(dna1) > 0);
        assertTrue("Less than", dna1.compareTo(dna2) < 0);
        assertTrue("Shorter than", dna1.compareTo(dna3) > 0);
        assertTrue("Longer than", dna3.compareTo(dna2) < 0);
    }

    /**
     * Test functionality of string2DNA method
     */
    @Test
    public void testString2DNA() {
        DNA testDna = DNA.string2DNA("GATTACA");
        DNA.Base[] expected = new DNA.Base[]{DNA.Base.G, DNA.Base.A, DNA.Base.T, DNA.Base.T, DNA.Base.A, DNA.Base.C, DNA.Base.A};
        Iterator<DNA.Base> it = testDna.iterator();
        for (DNA.Base base : expected) {
            assertEquals(base.getChar() + " is expected", base, it.next());
        }
        try {
            DNA.string2DNA("");
            fail("No error thrown");
        } catch (IllegalArgumentException e) {
        }

        testDna = DNA.string2DNA("A");
        it = testDna.iterator();
        assertEquals("One element", DNA.Base.A, it.next());
        try {
            it.next();
            fail("Too many bases in sequence");
        } catch (NullPointerException e) {

        }
        //exception thrown if string is empty
        try {
            DNA.string2DNA("");
            fail("No exception thrown");
        } catch (IllegalArgumentException e) {
        }

        //exception thrown if invalid character in string
        try {
            DNA.string2DNA("ATASAT");
            fail("No exception thrown");
        } catch (IllegalArgumentException e) {
        }
    }

    /**
     * Test functionality of the splice method
     */
    @Test
    public void testSplice() {
        DNA testDna = DNA.string2DNA("GATTACA");
        DNA testDnaToSplice = DNA.string2DNA("GATTACA");
        testDna.splice(testDnaToSplice, 1);
        DNA.Base[] expected = new DNA.Base[]{DNA.Base.G, DNA.Base.A, DNA.Base.T, DNA.Base.T, DNA.Base.A, DNA.Base.C, DNA.Base.A, DNA.Base.A, DNA.Base.T, DNA.Base.T, DNA.Base.A, DNA.Base.C, DNA.Base.A};
        Iterator<DNA.Base> it = testDna.iterator();
        for (DNA.Base base : expected) {
            assertEquals(base.getChar() + " is expected", base, it.next());
        }
        testDnaToSplice = DNA.string2DNA("GATTACA");
        testDna.splice(testDnaToSplice, 100);
        it = testDna.iterator();
        for (DNA.Base base : expected) {
            assertEquals("Test sequence is expected", base, it.next());
        }
        testDnaToSplice = DNA.string2DNA("A");
        testDna.splice(testDnaToSplice, 0);
        it = testDna.iterator();
        for (DNA.Base base : expected) {
            assertEquals("Test sequence is expected", base, it.next());
        }
        assertEquals("Last element added", DNA.Base.A, it.next());
        testDna.splice(new DNA(), 0);
        it = testDna.iterator();
        for (DNA.Base base : expected) {
            assertEquals("Test sequence is expected", base, it.next());
        }
        it.next();
        assertFalse("No next element", it.hasNext());
    }

    /**
     * Test functionality of overlaps method
     */
    @Test
    public void testOverlaps() {
        assertTrue("Length 1", DNA.overlaps(DNA.string2DNA("A"), DNA.string2DNA("A"), 1));
        assertTrue("No overlap always true", DNA.overlaps(DNA.string2DNA("ATTA"), DNA.string2DNA("CCCCGCT"), 0));
        assertTrue("Single overlap", DNA.overlaps(DNA.string2DNA("ATTA"), DNA.string2DNA("AC"), 1));
        assertTrue("Full overlap", DNA.overlaps(DNA.string2DNA("AATTAc"), DNA.string2DNA("AATTAcC"), 6));
        assertTrue("Identical strings", DNA.overlaps(DNA.string2DNA("AATTAcc"), DNA.string2DNA("AATTAcC"), 7));
        assertTrue("Empty only overlaps to 0", DNA.overlaps(DNA.string2DNA("AATTAcc"), new DNA(), 0));
        assertFalse("Empty only overlaps to 0", DNA.overlaps(DNA.string2DNA("AATTAcc"), new DNA(), 10));
        assertFalse("Opposite directions", DNA.overlaps(DNA.string2DNA("AAGCTCGA"), DNA.string2DNA("AGCTacacac"), 4));
        assertFalse("Not valid overlap", DNA.overlaps(DNA.string2DNA("ATTA"), DNA.string2DNA("ATTCA"), 4));
        assertFalse("Longer than the length of the DNA", DNA.overlaps(DNA.string2DNA("ATTA"), DNA.string2DNA("ATTA"), 5));
    }

    /**
     * Test functionality of toString method
     */
    @Test
    public void testToString() {
        DNA testDna = DNA.string2DNA("GATTACA");
        assertEquals("Same string as input expected", "GATTACA", testDna.toString());
        testDna = DNA.string2DNA("T");
        assertEquals("Length 1", "T", testDna.toString());
        assertEquals("Length 0", "", new DNA().toString());
    }

}