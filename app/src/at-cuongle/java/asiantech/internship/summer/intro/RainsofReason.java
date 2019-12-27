package asiantech.internship.summer.intro;

public class RainsofReason {
    public static void main(String[] args) {

    }

    public static int[] arrayReplace(int[] inputArray, int elemToReplace, int substitutionElem) {
        for (int i = 0; i < inputArray.length; i++) {
            if (inputArray[i] == elemToReplace) {
                inputArray[i] = substitutionElem;
            }
        }
        return inputArray;
    }

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

    public static boolean variableName(String name) {
        if (name.length() < 0) {
            return false;
        }
        return name.matches("[A-Za-z_][a-z_A-Z0-9]*$");

    }

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

    public static boolean chessBoardCellColor(String cell1, String cell2) {
        return (cell1.charAt(0) + cell1.charAt(1)) % 2 == 0 &&
                (cell2.charAt(0) + cell2.charAt(1)) % 2 == 0 ||
                (cell1.charAt(0) + cell1.charAt(1)) % 2 != 0 &&
                        (cell2.charAt(0) + cell2.charAt(1)) % 2 != 0;
    }
}
