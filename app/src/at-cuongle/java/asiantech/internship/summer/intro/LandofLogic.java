package asiantech.internship.summer.intro;

import java.util.HashSet;
import java.util.Set;

public class LandofLogic {
    public static void main(String[] args) {

    }

    /**
     * Define a word as a sequence of consecutive English letters. Find the longest
     * word from the given string.
     */
    public static String longestWord(String text) {
        int max = 0;
        String longestWord = "";
        String result = text.replaceAll("[^a-zA-Z0-9 ]", " ");
        String[] str = result.split("\\ ");
        for (String s : str) {
            if (max < s.length()) {
                max = s.length();
                longestWord = s;
            }
        }
        return longestWord;
    }

    /**
     * Check if the given string is a correct time representation of the 24-hour clock.
     */
    public static boolean validTime(String time) {
        String[] check = time.split("\\:");
        System.out.println();
        return Integer.parseInt(check[0]) >= 0 && Integer.parseInt(check[0]) < 24 && Integer.parseInt(check[1]) >= 0 && Integer.parseInt(check[1]) < 60;
    }

    /**
     * CodeMaster has just returned from shopping. He scanned the check of the
     * items he bought and gave the resulting string to Ratiorg to figure out the
     * total number of purchased items. Since Ratiorg is a bot he is definitely going
     * to automate it, so he needs a program that sums up all the numbers which appear
     * in the given input.
     * <p>
     * Help Ratiorg by writing a function that returns the sum of numbers that appear in
     * the given inputString.
     */
    public static int sumUpNumbers(String inputString) {
        int results = 0;
        String result = inputString.replaceAll("[^0-9]", " ");
        String[] str = result.split("\\ ");
        for (String s : str) {
            if (!s.isEmpty()) {
                results = results + Integer.parseInt(s);
            }
        }
        return results;
    }

    /**
     * Given a rectangular matrix containing only digits, calculate the number
     * of different 2 × 2 squares in it.
     */
    public static int differentSquares(int[][] matrix) {
        HashSet<String> uniqueMatrix = new HashSet<>();
        int k = 0;
        int row = matrix.length;
        int column = matrix[0].length;
        String[] matrixToString = new String[row * column];
        for (int i = 0; i < row - 1; i++) {
            for (int j = 0; j < column - 1; j++) {
                matrixToString[k] = matrix[i][j] + Integer.toString(matrix[i][j + 1]) + matrix[i + 1][j] + matrix[i + 1][j + 1];
                uniqueMatrix.add(matrixToString[k]);
                k++;
            }
        }
        return (uniqueMatrix.size());
    }

    /**
     * Given an integer product, find the smallest positive (i.e. greater than 0)
     * integer the product of whose digits is equal to product. If there is no such
     * integer, return -1 instead.
     */
    public static int digitsProduct(int product) {
        for (int i = 1; i < 99999; i++) {
            String digits = String.valueOf(i);
            int result = (digits.charAt(0) - '0');
            for (int j = 1; j < digits.length(); j++) {
                result *= (digits.charAt(j) - '0');
            }
            if (result == product)
                return Integer.parseInt(digits);
        }
        return -1;
    }

    /**
     * You are given an array of desired filenames in the order of their
     * creation. Since two files cannot have equal names, the one which comes
     * later will have an addition to its name in a form of (k), where k is the
     * smallest positive integer such that the obtained name is not used yet.
     * Return an array of names that will be given to the files.
     */
    public static String[] fileNaming(String[] names) {
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
     * You are taking part in an Escape Room challenge designed specifically
     * for programmers. In your efforts to find a clue, you've found a binary
     * code written on the wall behind a vase, and realized that it must be an
     * encrypted message. After some thought, your first guess is that each consecutive
     * 8 bits of the code stand for the character with the corresponding extended ASCII code.
     * Assuming that your hunch is correct, decode the message.
     */
    public static String messageFromBinaryCode(String code) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < code.length(); i += 8) {
            String temp = code.substring(i, i + 8);
            int num = Integer.parseInt(temp, 2);
            char letter = (char) num;
            result.append(letter);
        }
        return result.toString();
    }

    /**
     * Construct a square matrix with a size N × N containing integers from 1 to N * N in
     * a spiral order, starting from top-left and in clockwise direction.
     */
    public static int[][] spiralNumbers(int n) {
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
