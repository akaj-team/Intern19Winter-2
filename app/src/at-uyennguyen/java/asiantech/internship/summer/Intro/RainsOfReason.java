package asiantech.internship.summer.Intro;

public class RainsOfReason {
    /**
     * Given an array of integers, replace all the occurrences of elemToReplace with substitutionElem.
     */
    static int[] arrayReplace(int[] inputArray, int elemToReplace, int substitutionElem) {
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
    static boolean evenDigitsOnly(int n) {
        String number = String.valueOf(n);
        char[] a = number.toCharArray();
        for (int i = 0; i < a.length; i++) {
            if (Character.getNumericValue(a[i]) % 2 != 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * Correct variable names consist only of English letters, digits and underscores and they can't start with a digit.
     * Check if the given string is a correct variable name.
     */
    static boolean variableName(String name) {
        if (name.matches("[a-zA-Z_][0-9a-zA-Z_]*$")) {
            return true;
        }
        return false;
    }

    /**
     * Given a string, your task is to replace each of its characters by the ic_next one in the English alphabet;
     * i.e. replace a with b, replace b with c, etc (z would be replaced by a).
     */
    static String alphabeticShift(String inputString) {
        StringBuilder alpha = new StringBuilder();
        for (int i = 0; i < inputString.length(); i++) {
            if (inputString.charAt(i) == 'z') {
                alpha.append('a');
            } else {
                alpha.append((char) (inputString.charAt(i) + 1));
            }
        }
        return alpha.toString();
    }

    /**
     * Given two cells on the standard chess board, determine whether they have the same color or not.
     */
    static boolean chessBoardCellColor(String cell1, String cell2) {
        char[] ngang = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H'};
        char[] doc = new char[]{'1', '2', '3', '4', '5', '6', '7', '8'};
        char[] a = cell1.toCharArray();
        char[] b = cell2.toCharArray();
        int ce1ngang = 0;
        int ce2ngang = 0;
        int ce1doc = 0;
        int ce2doc = 0;
        for (int i = 0; i < ngang.length; i++) {
            if (ngang[i] == cell1.charAt(0)) {
                ce1ngang = i;
            }
        }
        for (int i = 0; i < ngang.length; i++) {
            if (ngang[i] == cell2.charAt(0)) {
                ce2ngang = i;
            }
        }
        for (int i = 0; i < doc.length; i++) {
            if (doc[i] == cell1.charAt(1)) {
                ce1doc = i;
            }
        }
        for (int i = 0; i < doc.length; i++) {
            if (doc[i] == cell2.charAt(1)) {
                ce2doc = i;
            }
        }
        if (((ce1ngang + ce1doc) + (ce2ngang + ce2doc)) % 2 == 0) {
            return true;
        }
        return false;
    }

    boolean isMAC48Address(String inputString) {
        if (inputString.length() != 17) {
            return false;
        }
        for (int i = 0; i < inputString.length(); i++) {
            if (i % 3 == 2) {
                if (inputString.charAt(i) != '-') {
                    return false;
                }
            } else {
                char sym = inputString.charAt(i);
                if (!('0' <= sym && sym <= '9' || 'A' <= sym && sym <= 'F')) {
                    return false;
                }
            }
        }

        return true;
    }


}
