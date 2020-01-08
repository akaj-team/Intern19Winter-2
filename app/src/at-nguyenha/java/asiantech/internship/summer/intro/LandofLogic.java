package asiantech.internship.summer.intro;

import java.util.HashSet;
import java.util.Set;

public class LandofLogic {

    public static void main(String[] args) {

    }

    /**
     * 52
     * Define a word as a sequence of consecutive English letters.
     * Find the longest word from the given string.
     */
    static String longestWord(String text) {
        int max = 0;
        String longestWord = "";
        String result = text.replaceAll("[^a-zA-Z0-9 ]", " ");
        String[] str = result.split(" ");
        for (String s : str) {
            if (max < s.length()) {
                max = s.length();
                longestWord = s;
            }
        }
        return longestWord;
    }

    /**
     * 53
     * Check if the given string is a correct time representation of the 24-hour clock.
     */
    static boolean validTime(String time) {
        String[] str = time.split(":");
        if ((Integer.parseInt(str[0]) >= 0 && Integer.parseInt(str[0]) <= 23)
                && (Integer.parseInt(str[1]) >= 0 && Integer.parseInt(str[1]) <= 59)) {
            return true;
        }
        return false;
    }

    /**
     * 54
     * CodeMaster has just returned from shopping.
     * * He scanned the check of the items he bought and
     * * gave the resulting string to Ratiorg to figure
     * * out the total number of purchased items.
     * * Since Ratiorg is a bot he is definitely going to
     * * automate it, so he needs a program that sums up
     * * all the numbers which appear in the given input.
     * *
     * * Help Ratiorg by writing a function that returns
     * * the sum of numbers that appear in the given inputString.
     */
    static int sumUpNumbers(String inputString) {
        inputString = (inputString.replaceAll("[a-zA-Z,!#_\\-.$%&*+ ]", " "));
        if (inputString.length() == 0) {
            return 0;
        }
        int sum = 0;
        for (String str : inputString.split(" ")) {
            if (str.length() != 0) {
                sum += Integer.parseInt(str.replaceAll("[^0-9]", "").trim());
            }
        }
        return sum;
    }

    /**
     * 55
     * Given a rectangular matrix containing only digits,
     * calculate the number of different 2 × 2 squares in it.
     */
    static int differentSquares(int[][] matrix) {
        Set<String> set = new HashSet<>();
        int m = matrix.length;
        int n = matrix[0].length;
        if (m == 1 || n == 1) {
            return 0;
        }
        for (int i = 0; i < m - 1; ++i) {
            for (int j = 0; j < n - 1; ++j) {
                String sb = matrix[i][j] + "\t" + matrix[i][j + 1] + "\t" +
                        matrix[i + 1][j] + "\t" + matrix[i + 1][j + 1];
                set.add(sb);
            }
        }
        return set.size();
    }

    /**
     * 56z
     * Given an integer product, find the smallest positive
     * (i.e. greater than 0) integer the product of whose digits
     * is equal to product. If there is no such integer, return -1 instead.
     */
    static int digitsProduct(int product) {
        for (int i = 1; i < 99999; i++) {
            String digits = String.valueOf(i);
            int result = (digits.charAt(0) - '0');
            for (int j = 1; j < digits.length(); j++) {
                result *= (digits.charAt(j) - '0');
            }
            if (result == product){
                return Integer.parseInt(digits);
            }
        }
        return -1;
    }

    /**
     * 57
     * You are given an array of desired filenames
     * in the order of their creation. Since two files
     * cannot have equal names, the one which comes later
     * will have an addition to its name in a form of (k),
     * where k is the smallest positive integer such
     * that the obtained name is not used yet.
     * <p>
     * Return an array of names that will be given to the files.
     */
    static String[] fileNaming(String[] names) {
        Set<String> set = new HashSet<>();
        String[] fileNames = new String[names.length];
        for (int i = 0; i < names.length; i++) {
            String name = names[i];
            if (!set.contains(name)) {
                set.add(name);
                fileNames[i] = name;
            } else {
                int k = 0;
                String newName = name;
                while (set.contains(newName)) {
                    k++;
                    newName = name + "(" + k + ")";
                }
                set.add(newName);
                fileNames[i] = newName;
            }
        }
        return fileNames;
    }

    /**
     * 58
     * You are taking part in an Escape Room challenge designed
     * specifically for programmers. In your efforts to find a clue,
     * you've found a binary code written on the wall behind a vase,
     * and realized that it must be an encrypted message.
     * After some thought, your first guess is that each consecutive
     * 8 bits of the code stand for the character with the corresponding extended ASCII code.
     * <p>
     * Assuming that your hunch is correct, decode the message.
     */
    static String messageFromBinaryCode(String code) {
        int bin, div = code.length() / 8, test = 8, test2 = 0;
        StringBuilder s = new StringBuilder();
        while (div-- > 0) {
            bin = Integer.valueOf(code.substring(test2, test), 2);
            s.append(Character.toChars(bin));
            test += 8;
            test2 += 8;
        }
        return s.toString();
    }

    /**
     * 59
     * Construct a square matrix with a size N × N containing integers
     * from 1 to N * N in a spiral order, starting from top-left and
     * in clockwise direction.
     */
    static int[][] spiralNumbers(int n) {
        int value = 1;
        int[][] result = new int[n][n];
        int minColumn = 0;
        int maxColumn = n - 1;
        int minRow = 0;
        int maxRow = n - 1;
        while (value <= n * n) {
            for (int i = minColumn; i <= maxColumn; i++) {
                result[minRow][i] = value;
                value++;
            }
            for (int i = minRow + 1; i <= maxRow; i++) {
                result[i][maxColumn] = value;
                value++;
            }
            for (int i = maxColumn - 1; i >= minColumn; i--) {
                result[maxRow][i] = value;
                value++;
            }
            for (int i = maxRow - 1; i >= minRow + 1; i--) {
                result[i][minColumn] = value;
                value++;
            }
            minColumn++;
            minRow++;
            maxColumn--;
            maxRow--;
        }
        return result;
    }
}
