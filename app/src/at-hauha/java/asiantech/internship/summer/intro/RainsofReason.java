package asiantech.internship.summer.intro;

public class RainsofReason {
    /*
    *Given an array of integers, replace all the occurrences of elemToReplace with substitutionElem.
    *
    * */
    public static int[] arrayReplace(int[] inputArray, int elemToReplace, int substitutionElem) {
        for(int i = 0; i < inputArray.length;i++){
            if(inputArray[i] == elemToReplace){
                inputArray[i] = substitutionElem;
            }
        }
        return inputArray;
    }
    /**
     * Check if all digits of the given integer are even.
     *
     *
     * */
    public static boolean evenDigitsOnly(int n) {
        while (n > 0) {
            int temp = n % 10;
            if (temp % 2 == 1)
                return false;
            n /= 10;
        }
        return true;
    }

    /*
    *Check if the given string is a correct variable name.
    *
    * */
    public static boolean variableName(String name) {
        if(name.length()<0){
            return false;
        }
        if (name.matches("[a-zA-Z_][A-Za-z0-9_]*$")) {
            return true;
        }
        return false;
    }
    /*
    *Given a string, your task is to replace each of its characters by the next one in the English alphabet
    *
    * */
    public static String alphabeticShift(String inputString) {
        char[] c = new char[inputString.length()];
        int[] a = new int[inputString.length()];
        for (int i = 0; i < inputString.length(); i++) {
            a[i] = inputString.charAt(i)+1;
            if(a[i] > 122) {
                a[i] = 97;
            }
        }
        String str = "";
        for (int i=0;i<inputString.length();i++) {
            str += (char) a[i];
        }
        return str;
    }
    /*
    *Given two cells on the standard chess board, determine whether they have the same color or not.
    *
    * */
    public static boolean chessBoardCellColor(String cell1, String cell2) {
        if(cell1.matches("[BDFH][2468]")&&cell2.matches("[BDFH][2468]")){
            return true;
        }

        if(cell1.matches("[BDFH][1357]")&&cell2.matches("[BDFH][1357]")){
            return true;
        }

        if(cell1.matches("[ACEG][1357]")&&cell2.matches("[ACEG][1357]")){
            return true;
        }
        if(cell1.matches("[ACEG][2468]")&&cell2.matches("[ACEG][2468]")){
            return true;
        }

        if(cell1.matches("[ACEG][2468]")&&cell2.matches("[BDFH][1357]")){
            return true;
        }
        if(cell1.matches("[ACEG][1357]")&&cell2.matches("[BDFH][2468]")){
            return true;
        }

        return false;
    }


    public static void main(String[] args) {

    }
}
