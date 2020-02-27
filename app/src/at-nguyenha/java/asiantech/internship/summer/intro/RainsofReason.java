package asiantech.internship.summer.intro;

public class RainsofReason {


    public static void main(String[] args) {

    }

    /**
     * 25
     * Given an array of integers, replace all the occurrences of elemToReplace with substitutionElem.
     */
    int[] arrayReplace(int[] inputArray, int elemToReplace, int substitutionElem) {
        int size = inputArray.length;
        for (int i = 0; i < size; i++) {
            if (inputArray[i] == elemToReplace) {
                inputArray[i] = substitutionElem;
            }
        }
        return inputArray;
    }


    /**
     * 26
     * Check if all digits of the given integer are even.
     */
    boolean evenDigitsOnly(int n) {
        int count = 0;
        while (n >= 1) {
            if (n % 2 != 0) {
                count++;
            }
            n /= 10;
        }
        return count < 1;
    }


    /**
     * 27
     * Correct variable names consist only of English letters,
     * digits and underscores and they can't start with a digit.
     * <p>
     * Check if the given string is a correct variable name.
     */
    boolean variableName(String name) {
        return name.matches("[a-zA-Z_][0-9a-zA-Z_]*$");
    }


    /**
     * 28
     * Given a string, your task is to replace each of its
     * characters by the next one in the English alphabet;
     * i.e. replace a with b, replace b with c, etc
     * (z would be replaced by a).
     */
    String alphabeticShift(String inputString) {
        StringBuilder sb = new StringBuilder();
        int size = inputString.length();
        for (int i = 0; i < size; i++) {
            char c = inputString.charAt(i);
            if (c == 'z') {
                sb.append('a');
            } else {
                sb.append((char) (inputString.charAt(i) + 1));
            }
        }
        return sb.toString();
    }

    /**
     * Given two cells on the standard chess board,
     * determine whether they have the same color or not.
     */
    boolean chessBoardCellColor(String cell1, String cell2) {
        return (cell1.charAt(0) + cell1.charAt(1)) % 2 == 0 &&
                (cell2.charAt(0) + cell2.charAt(1)) % 2 == 0 ||
                (cell1.charAt(0) + cell1.charAt(1)) % 2 != 0 &&
                        (cell2.charAt(0) + cell2.charAt(1)) % 2 != 0;
    }


}
