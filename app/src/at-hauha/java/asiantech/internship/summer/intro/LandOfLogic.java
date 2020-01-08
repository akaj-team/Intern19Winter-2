package asiantech.internship.summer.intro;

import java.util.HashSet;
import java.util.Set;

public class LandOfLogic {
    /**
     * Define a word as a sequence of consecutive English letters. Find the longest word from the given string.
     */
    public static String longestWord(String text) {
        int max = 0;
        String longest = "";
        String result = text.replaceAll("[^a-zA-Z0-9 ]", " ");
        String[] str = result.split("\\ ");
        for (int i = 0; i < str.length; i++) {
            if (max < str[i].length()) {
                max = str[i].length();
                longest = str[i];
            }
        }
        return longest;
    }

    /**
     * Check if the given string is a correct time representation of the 24-hour clock.
     */
    public static boolean validTime(String time) {
        String[] str = time.split("\\:");
        int h = Integer.parseInt(str[0]);
        int m = Integer.parseInt(str[1]);
        if (h >= 0 && h < 24 && m >= 0 && m < 60) {
            return true;
        }
        return false;
    }

    /**
     * Help Ratiorg by writing a function that returns the sum of numbers that appear in the given inputString.
     */
    public static int sumUpNumbers(String inputString) {
        String s = inputString.replaceAll("[^0-9]", " ");
        String[] str = s.split("\\ ");
        int sum = 0;
        for (int i = 0; i < str.length; i++) {
            if (!"".equals(str[i])) {
                sum += Integer.parseInt(str[i]);
            }
        }
        return sum;
    }

    /**
     * Given an integer product, find the smallest positive (i.e. greater than 0) integer the product of whose digits is equal to product. If there is no such integer, return -1 instead.
     */
    public static int digitsProduct(int product) {
        if (product == 0) {
            return 10;
        }
        if (product == 1) {
            return 1;
        }
        String digit = "";
        for (int i = 9; i > 1; i--) {
            while (product % i == 0) {
                product /= i;
                digit = Integer.toString(i) + digit;
            }
        }
        if (product > 1) {
            return -1;
        }

        return Integer.parseInt(digit);
    }

    /**
     * You are given an array of desired filenames in the order of their creation. Since two files cannot have equal names, the one which comes later will have an addition to its name in a form of (k), where k is the smallest positive integer such that the obtained name is not used yet.
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
     * You are taking part in an Escape Room challenge designed specifically for programmers. In your efforts to find a clue, you've found a binary code written on the wall behind a vase, and realized that it must be an encrypted message. After some thought, your first guess is that each consecutive 8 bits of the code stand for the character with the corresponding extended ASCII code.
     */
    public static String messageFromBinaryCode(String code) {
        String result = "";
        for (int i = 0; i < code.length(); i++) {
            if ((i - 7) % 8 == 0) {
                String bit = code.substring(i - 7, i + 1);
                int decimal2 = Integer.parseInt(bit, 2);
                char letter = (char) decimal2;
                result += letter;
            }
        }
        return result;
    }

    /**
     * Construct a square matrix with a size N × N containing integers from 1 to N * N in a spiral order, starting from top-left and in clockwise direction.
     */
    public static int[][] spiralNumbers(int n) {
        int[][] spiral = new int[n][n];
        int value = 1;
        int minCol = 0;
        int maxCol = n - 1;
        int minRow = 0;
        int maxRow = n - 1;
        while (value <= n * n) {
            for (int i = minCol; i <= maxCol; i++) {
                spiral[minRow][i] = value;
                value++;
            }
            for (int i = minRow + 1; i <= maxRow; i++) {
                spiral[i][maxCol] = value;
                value++;
            }
            for (int i = maxCol - 1; i >= minCol; i--) {
                spiral[maxRow][i] = value;
                value++;
            }
            for (int i = maxRow - 1; i >= minRow + 1; i--) {
                spiral[i][minCol] = value;
                value++;
            }
            minCol++;
            minRow++;
            maxCol--;
            maxRow--;
        }
        return spiral;
    }

    public static void main(String[] args) {

    }
}
