/* Nikil Pancha
* HW2
* Class with a variety of methods for string manipulation and operations
*/

public class HW2 {

    //  returns true if string is alphabetical order, false if not
    public static boolean alphabeticalOrder(String s) {
        int prev = 0; //stores previous alphabetical character

/*
    loops through string, compares current character to previous if current is alphabetic.
    if not at least previous, returns false
*/
        for (int i = 0; i < s.length(); i++) {
            //separate lower and uppercase
            if (s.charAt(i) >= 'A' && s.charAt(i) <= 'Z') {
                if (s.charAt(i) - 'A' < prev) {
                    return false;
                }
                prev = s.charAt(i) - 'A';
            } else if (s.charAt(i) >= 'a' && s.charAt(i) <= 'z') {
                if (s.charAt(i) - 'a' < prev) {
                    return false;
                }
                prev = s.charAt(i) - 'a';
            }
        }
        return true;
    }


    //increases all alphanumeric characters in s by n, wraps around from 'z'/'Z'/'9' and 'a'/'A'/'0'
    public static String caesarCipher(String s, int n) {
        StringBuilder shift = new StringBuilder(); //StringBuilder to store shifted output

/*
    loop to go through all of string s
    appends to shift a shifted character or the same character (if not alphanumeric) each loop
*/
        for (int i = 0; i < s.length(); i++) {
            char shiftedChar; //stores the shifted character
            if ((s.charAt(i) >= 'A') && (s.charAt(i) <= 'Z')) {//is uppercase
                if (s.charAt(i) - 'A' + n >= 0) {
                    shiftedChar = (char) ((s.charAt(i) - 'A' + n) % 26 + 'A'); //adds n to character and takes mod 26 to make sure value is range in [0,25]
                } else {
                    shiftedChar = (char) ((s.charAt(i) - 'A' + n + 26 * (-n / 26 + 2)) % 26 + 'A'); //adds enough alphabets to n to increase past 0, allowing mod 26 to be positive
                }
                shift.append(shiftedChar);
            } else if ((s.charAt(i) >= 'a') && (s.charAt(i) <= 'z')) { //is lowercase
                if (s.charAt(i) - 'a' + n >= 0) {
                    shiftedChar = (char) ((s.charAt(i) - 'a' + n) % 26 + 'a'); //same process as with capital letters
                } else {
                    shiftedChar = (char) ((s.charAt(i) - 'a' + n + 26 * (-n / 26 + 2)) % 26 + 'a'); //same process as with capital letters
                }
                shift.append(shiftedChar);
            } else if ((s.charAt(i) >= '0') && (s.charAt(i) <= '9')) { //is a number
                if (s.charAt(i) + n - '0' >= 0) {
                    shiftedChar = ((char) ((s.charAt(i) + n - '0') % 10 + '0')); //same as with letters, but mod 10 instead
                } else {
                    shiftedChar = ((char) ((s.charAt(i) + n - '0' + 10 * (-n / 10 + 2)) % 10 + '0')); //same as with letters, but mod 10 instead
                }
                shift.append(shiftedChar);
            } else {
                shift.append(s.charAt(i)); //is symbol
            }
        }
        return shift.toString();
    }


    //repeats each character in string s n times
    public static String repeatChars(String s, int n) {
        StringBuilder output = new StringBuilder(); //StringBuilder to store output

        //loop through each character in string
        for (int i = 0; i < s.length(); i++) {
            //append each character to string n times
            for (int j = 0; j < n; j++) {
                output.append(s.charAt(i));
            }
        }
        return output.toString();
    }


    //prints word lengths, keeps non-alphabetic characters the same
    public static String wordLengths(String s) {
        StringBuilder output = new StringBuilder(); //StringBuilder to store output
        int n = 0; //counter to track lengths of words

/*
     loops through string, if non-alphabetical character is at index,
     word counter length is reset and number is added to output
*/
        for (int i = 0; i < s.length(); i++) {
            if (!isAlpha(s, i)) {
                if (n != 0) {
                    output.append(n);
                    n = 0;
                }
                output.append(s.charAt(i));
            } else {
                n++;
            }
        }
        //adds the last word length if s ends with a letter
        if (s.length() != 0 && isAlpha(s, s.length() - 1)) {
            output.append(n);
        }
        return output.toString();
    }


    //method that repeats each word in string s n times
    public static String repeatWords(String s, int n) {
        StringBuilder output = new StringBuilder(); //StringBuilder to store output
        int i = 0; //index to track character position

        //handles empty string
        if (s.length() == 0) {
            return s;
        } else if (n == 0) {
            //loops through and adds non-alphabetic characters for the n=0 case
            for (int j = 0; j < s.length(); j++) {
                if (!isAlpha(s, j)) {
                    output.append(s.charAt(j));
                }
            }
            return output.toString();
        }

        //adds characters until a letter is reached
        while (i < s.length() && !isAlpha(s, i)) {
            output.append(s.charAt(i));
            i++;
        }

        //loops through all characters in s
        while (i < s.length()) {
            int j = i; //stores position beginning of word
            //repeats n times
            for (int iter = 0; iter < n; iter++) {
                //continue until end of word
                while (i < s.length() && isAlpha(s, i)) {
                    output.append(s.charAt(i));
                    i++;
                }

                //add space and reset i if not on last iteratin
                if (iter < (n - 1)) {
                    output.append(' ');
                    i = j;
                }
            }

            //adds non-alphabetical characters to output
            while (i < s.length() && !isAlpha(s, i)) {
                output.append(s.charAt(i));
                i++;
            }
        }

        return output.toString();
    }


    /*
      given a string s and int position, returns true if character at position is in the alphabet (to simplify code)
    */
    private static boolean isAlpha(String s, int position) {
        return ((s.charAt(position) >= 'A') && (s.charAt(position) <= 'Z'))
                || ((s.charAt(position) >= 'a') && (s.charAt(position) <= 'z'));
    }


    //returns the position in the alphabet of a character c, independent of character
    private static int alphaNumber(char c) {
        if ((c >= 'A') && (c <= 'Z')) { //is uppercase
            return c - 'A';
        } else if ((c >= 'a') && (c <= 'z')) { //is lowercase
            return c - 'a';
        } else {
            return 26; //out of range
        }
    }


    /*
      replaces letters in s with the letters in originalKey
      ('a' becomes 1st character of originalKey, 'b' 2nd, ... continuing through end of originalKey or the 26th character)
    */
    public static String remap(String s, String key) {
        StringBuilder output = new StringBuilder();//StringBuilder to store output


        int keyLength; //stores length of key or 26, whichever is less
        if (key.length()>26){
            keyLength = 26;
        } else {
            keyLength = key.length();
        }

        //goes through characters in s, and replaces letters with the corresponding letter in the key
        for (int i = 0; i < s.length(); i++) {
            //replaces character in s if it is less than the key length
            if (alphaNumber(s.charAt(i)) < keyLength) {
                output.append(key.charAt(alphaNumber(s.charAt(i))));
            } else {
                output.append(s.charAt(i));
            }
        }
        return output.toString();
    }


    //checks if character c is in string s
    private static boolean inString(String s, char c) {
        //goes through string, and checks if character is at position i
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == c) {
                return true;
            }
        }
        return false;
    }


    //randomly generates an alphabet
    private static String randomAlphabet() {
        String alphabet = "abcdefghijklmnopqrstuvwxyz"; //stores the alphabet to choose from
        StringBuilder output = new StringBuilder(); //StringBuilder to store output
        int i = 0; //index to go through alphabet

        //runs while the new alphabet is not generated
        while (i < alphabet.length()) {
            int rand = (int) (Math.random() * 26); //stores a random integer between 0 and 25, new each loop

            //if the letter corresponding to rand is not already in the output, add to output and increase the index
            if (!inString(output.toString(), alphabet.charAt(rand))) {
                output.append(alphabet.charAt(rand));
                i++;
            }
        }

        return output.toString();
    }


    //changes order of alphabet in s to be randomly generated
    public static String cryptoquip(String s) {
        return remap(s, randomAlphabet());
    }
}
