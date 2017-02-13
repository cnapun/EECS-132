/* Nikil Pancha
* A JUnit test class for HW2.  0, 1, many, first, middle, and last are tested in all places that they are
* applicable, although sometimes a test will encompass multiple cases*/

import org.junit.*;
import static org.junit.Assert.*;

public class HW2Test {

    //JUnit test for alphabeticalOrder
    //test 0 means empty string, as loop won't execute
    @Test
    public void testAlphabeticalOrder0() {
        //always in alphabetical order
        assertEquals("Empty string", true, HW2.alphabeticalOrder(""));
    }

    //only 1 time through loop
    @Test
    public void testAlphabeticalOrder1() {
        //always in alphabetical order
        assertEquals("String of length 1", true, HW2.alphabeticalOrder("a"));
        assertEquals("String of length 1 symbol", true, HW2.alphabeticalOrder("-"));
    }

    //test strings in both alphabetical and nonalphabetical order
    @Test
    public void testAlphabeticalOrderMany() {
        assertEquals("Long string not in alphabetical order", false, HW2.alphabeticalOrder("ost**R)iCHC"));
        assertEquals("Long string in alphabetical order", true, HW2.alphabeticalOrder("aAa-b B-c!"));
    }

    //symbols and capitalization in the first character of the string may affect its handling
    @Test
    public void testAlphabeticalOrderFirst() {
        assertEquals("Test symbol first", true, HW2.alphabeticalOrder("*abcd"));
        assertEquals("Test inversion first (upper)", false, HW2.alphabeticalOrder("Babcde"));
        assertEquals("Test inversion first (lower)", false, HW2.alphabeticalOrder("babcde"));
    }

    //symbols and capitalization in the last character of the string may affect its handling
    @Test
    public void testAlphabeticalOrderLast() {
        assertEquals("Test symbol last", true, HW2.alphabeticalOrder("abcd+"));
        assertEquals("Test inversion last (upper)", false, HW2.alphabeticalOrder("abcdeD"));
        assertEquals("Test inversion last (lower)", false, HW2.alphabeticalOrder("abcded"));
    }

    //symbols and capitalization in the middle character of the string may affect its handling
    @Test
    public void testAlphabeticalOrderMiddle() {
        assertEquals("Test inversion in middle (lower)", false, HW2.alphabeticalOrder("abcdbde"));
        assertEquals("Test inversion in middle (upper)", false, HW2.alphabeticalOrder("abcdBde"));
    }
    //end of alphabeticalOrder test

    //JUnit test for caesarCipher
    //test empty string and test no shift
    @Test
    public void testCaesarCipher0() {
        assertEquals("Caesar shift of empty string", "", HW2.caesarCipher("", 3));
        assertEquals("No shift", "Hello World!", HW2.caesarCipher("Hello World!", 0));
    }

    //test a string of length 1, shifted both positive and negative shifts, making sure that a single character works
    @Test
    public void testCaesarCipher1() {
        assertEquals("String length 1, shifted +1", "b", HW2.caesarCipher("a", 1));
        assertEquals("String length 1, shifted -1", "z", HW2.caesarCipher("a", -1));
    }

    //test the caesar shift of a long string with a shift longer than 26
    @Test
    public void testCaesarCipherMany() {
        assertEquals("Caesar shift of long string", "Avxvy Cnapun vf zl anzr!!9", HW2.caesarCipher("Nikil Pancha is my name!!0", 39));
    }

    //test shift when first character is not shifted
    @Test
    public void testCaesarCipherFirst() {
        assertEquals("First character symbol", "!mhjHk", HW2.caesarCipher("!nikIl", -27));
    }

    //test shift when last character is not shifted
    @Test
    public void testCaesarCipherLast() {
        assertEquals("Last character symbol", "Bwywz!", HW2.caesarCipher("Nikil!", -12));
    }

    //test case where there is one letter in the middle
    @Test
    public void testCaesarCipherMiddle() {
        assertEquals("All but middle characters symbol", "!!!d!", HW2.caesarCipher("!!!r!", -40));
        assertEquals("All but middle characters symbol", "d!dd", HW2.caesarCipher("r!rr", -40));
    }
    //end of caesarCipher test


    //test of repeat characters
    //execute loop zero times, with either empty string or no repeats
    @Test
    public void testRepeatChars0() {
        assertEquals("Repeat empty string", "", HW2.repeatChars("", 1290));
        assertEquals("Repeat string 0 times", "", HW2.repeatChars("lasdo093ro90vja0asd", 0));
    }

    //goes through while loop once, or goes through the for loop once
    @Test
    public void testRepeatChars1() {
        assertEquals("Repeat single character", "sssssssssss", HW2.repeatChars("s", 11));
        assertEquals("Repeat single character", "good afternoon !!", HW2.repeatChars("good afternoon !!", 1));
    }

    //goes through both loops multiple times
    @Test
    public void testRepeatCharsMany() {
        assertEquals("Repeat many characters multiple times", "GGoooodd  " +
                "AAfftteerrnnoooonn!!??!!??!!", HW2.repeatChars("Good Afternoon!?!?!", 2));
    }
    /* In this case, first, middle, and last are encompassed by the first 3 tests. There are no if statements
    *  or stored variables, so nothing will change depending on the character locations*/
    //end of repeat characters test


    //JUnit test for word lengths
    //empty string is a separate case
    @Test
    public void testWordLengths0() {
        assertEquals("Check empty string word lengths", "", HW2.wordLengths(""));
    }

    //string of length 1, both characters and letters, to make sure loop functions properly when it runs once
    @Test
    public void testWordLengths1() {
        assertEquals("Word length of string with 1 letter", "1", HW2.wordLengths("s"));
        assertEquals("Word length of string with 1 non-letter", "!", HW2.wordLengths("!"));
    }

    //go through for loop multiple times, with both symbols and words
    @Test
    public void testWordLengthsMany() {
        assertEquals("Test word lengths of longer string", "34 -!- 8 6 3 5 2 2 2 10 5 " +
                "9!", HW2.wordLengths("Supercalifragilisticexpialidocious -!- Evenalle though the sound of it is soemething quite atrocious!"));
    }

    //first character will not be processed, the rest will (tested letter first in testWordLengthsMany())
    @Test
    public void testWordLengthsFirst() {
        assertEquals("Test symbol first", "!10", HW2.wordLengths("!abcdefghij"));
    }

    //test string ending with symbol, one ending with letter
    @Test
    public void testWordLengthsLast() {
        assertEquals("Test symbol last", "11(", HW2.wordLengths("sabcdefghij("));
        assertEquals("Test letter last", "11", HW2.wordLengths("sabcdefghij"));
    }

    //symbol in middle of words could be handled differently
    @Test
    public void testWordLengthsMiddle() {
        assertEquals("Test symbol in the middle", "1>1", HW2.wordLengths("a>b"));
    }
    //end of word lengths test


    //test repeat words
    //for loop not executed, while loop not executed (respectively)
    @Test
    public void testRepeatWords0() {
        assertEquals("Repeated empty string", "", HW2.repeatWords("", 100));
        assertEquals("String repeated 0 times", "!", HW2.repeatWords("asdj!h", 0));
    }

    //repeat words with one character, repeated once should return the original string
    @Test
    public void testRepeatWords1() {
        assertEquals("Single letter repeated", "s s s s s", HW2.repeatWords("s", 5));
        assertEquals("Symbol not repeated", "!", HW2.repeatWords("!", 30));
        assertEquals("Single letter repeated", "1s4ad?#", HW2.repeatWords("1s4ad?#", 1));
    }

    //repeat multiple words multiple times (all loops multiple times)
    @Test
    public void testRepeatWordsMany() {
        assertEquals("Repeat many words multiple times", "!@#'How How How are are are you you you?', " +
                "I I I asked asked asked.", HW2.repeatWords("!@#'How are you?', I asked.", 3));
    }

    //string can either start with symbol or letter, checks both
    @Test
    public void testRepeatWordsFirst() {
        assertEquals("Start with symbol", "!Test Test this this", HW2.repeatWords("!Test this", 2));
        assertEquals("Start with letter", "Test Test this this", HW2.repeatWords("Test this", 2));
    }

    //string that ends with symbol will bo through a different process than strings that end with characters
    @Test
    public void testRepeatWordsLast() {
        assertEquals("End with symbol", "Test Test this this?", HW2.repeatWords("Test this?", 2));
        assertEquals("Start with symbol", "Tesst Tesst this this", HW2.repeatWords("Tesst this", 2));
    }

    //symbol in middle of string may be differently treated
    @Test
    public void testRepeatWordsMiddle() {
        assertEquals("symbol in middle", "Test Test!this this", HW2.repeatWords("Test!this", 2));
    }
    //end of repeat words test


    //test of remap
    //empty string remapped with non-empty key should return an empty string
    //non-empty string remapped with empty key should return the same string
    @Test
    public void testRemap0() {
        assertEquals("Remap empty string with non-empty key", "", HW2.remap("", "asghje"));
        assertEquals("Remap non-empty string with empty key", "asdfasdf", HW2.remap("asdfasdf", ""));
    }

    //single character key, single character string executes loop once
    @Test
    public void testRemap1() {
        assertEquals("Remap single letter", "!", HW2.remap("a", "!"));
        assertEquals("Remap no letters", "?", HW2.remap("?", "!"));
    }

    //key of nonzero length, long string, and key longer than 26 characters
    @Test
    public void testRemapMany() {
        assertEquals("Remap long string with long key", "XYrX0X!XYrX, sXi! the mXgi0iXn",
                HW2.remap("Abracadabra, said the magician", "XY0!"));
        assertEquals("Remap with key longer than 26 characters", "??avxvy vf zl anzr??",
                HW2.remap("??nikil is my name??", "nopqrstuvwxyzabcdefghijklm!)(*()@!*()*!)"));
    }

    //don't remap the first character, then only remap the first character
    //try symbol in first character
    @Test
    public void testRemapFirst() {
        assertEquals("First character not remapped", "qabcabcabc", HW2.remap("qBcdbcdbcd", "zabc"));
        assertEquals("Only first character remapped", "zireotlsdkfj", HW2.remap("zireotlsdkfj", "z"));
        assertEquals("Symbol first", "!!!zireotlsdkfj", HW2.remap("!!!zireotlsdkfj", "z"));
    }

    //remap the last character, test remap when last character is a symbol
    @Test
    public void testRemapLast() {
        assertEquals("Last character only one remapped", "bbbbbbbbx", HW2.remap("bbbbbbbba", "x"));
        assertEquals("Last character only one remapped", "bbbbbbbbx?", HW2.remap("bbbbbbbba?", "x"));
    }
    //the test of middle is contained within the other, as symbols are in the middle
    // of the word, which could be the only thing to mess up the remap
    //end of remap test


    //test of cryptoquip
    //empty string input should return empty string
    @Test
    public void testCryptoQuip0() {
        assertEquals("Empty cryptoquip", "", HW2.cryptoquip(""));
    }

    //single character should be remapped to a random letter
    @Test
    public void testCryptoQuip1() {
        assertEquals("Single character cryptoquip", true, 'a' <= HW2.cryptoquip("s").charAt(0) && 'z' >= HW2.cryptoquip("s").charAt(0));
    }

    //remap method works, so by demonstrating that a scrambled alphabet is generated when making
    //a cryptoquip of the alphabet, this is shown to be a working method
    //this is the equivalent of running the private randomAlphabet method method in HW2
    @Test
    public void testCryptoQuipMany() {
        String s = HW2.cryptoquip("abcdefghijklmnopqrstuvwxyz");
        for (char c : s.toCharArray()) {
            int count = 0; //track number of times each letter appears
            for (int i = 0; i < s.length(); i++) {
                if (s.charAt(i) == c) {
                    count++;
                }
            }
            //test fails if any letter is repeated
            if (count > 1) {
                fail("Letter appeared more than once, not unique alphabet");
            }
        }
    }
}

