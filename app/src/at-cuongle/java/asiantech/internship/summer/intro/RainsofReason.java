package asiantech.internship.summer.intro;

public class RainsofReason {
    public static void main(String[] args) {

    }

    /**
     * Given an array of integers, replace all the occurrences of elemToReplace with substitutionElem.
     */
    public static int[] arrayReplace(int[] inputArray, int elemToReplace, int substitutionElem) {
        for (int i = 0; i < inputArray.length; i++) {
            if (inputArray[i] == elemToReplace) {
                inputArray[i] = substitutionElem;
            }
        }
        return inputArray;
    }

    /**
     * Check if all digits of the given integer are even.
     */
    public static boolean evenDigitsOnly(int n) {
        int getEachNumber = 0;
        int coppyNumber = n;
        do {
            getEachNumber = coppyNumber % 10;
            coppyNumber = coppyNumber / 10;
            if (getEachNumber % 2 != 0) {
                return false;
            }

        } while (coppyNumber >= 1);
        return true;
    }

    /**
     * Correct variable names consist only of English letters, digits
     * and underscores and they can't start with a digit.
     * <p>
     * Check if the given string is a correct variable name.
     */
    public static boolean variableName(String name) {
        name.length();
        return name.matches("[A-Za-z_][a-z_A-Z0-9]*$");

    }

    /**
     * Given a string, your task is to replace each of its characters by the next one
     * in the English alphabet; i.e. replace a with b, replace b with c, etc (z would be replaced by a).
     */
    public static String alphabeticShift(String inputString) {
        char[] toCharArray = inputString.toCharArray();
        for (int i = 0; i < inputString.length(); i++) {
            int toAscii = toCharArray[i];
            if (toAscii == 122) {
                toAscii = 122 - 25;
            } else {
                toAscii = toAscii + 1;
            }
            toCharArray[i] = (char) toAscii;
        }
        return String.valueOf(toCharArray);
    }

    /**
     * Given two cells on the standard chess board, determine whether they have the same color or not.
     */
    public static boolean chessBoardCellColor(String cell1, String cell2) {
        return (cell1.charAt(0) + cell1.charAt(1)) % 2 == 0 &&
                (cell2.charAt(0) + cell2.charAt(1)) % 2 == 0 ||
                (cell1.charAt(0) + cell1.charAt(1)) % 2 != 0 &&
                        (cell2.charAt(0) + cell2.charAt(1)) % 2 != 0;
    }
}
