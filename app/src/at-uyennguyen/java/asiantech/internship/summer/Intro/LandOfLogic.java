package asiantech.internship.summer.Intro;

import java.util.HashSet;
import java.util.Set;

public class LandOfLogic {
    /**
     * Define a word as a sequence of consecutive English letters. Find the longest word from the given string.
     */
    static String longestWord(String text) {
        String result = text.replaceAll("[^a-zA-Z0-9 ]", " ");
        String[] n = result.split(" ");
        int maxLength = 0;
        String a = "";
        for (int i = 0; i < n.length; i++) {
            if (n[i].length() > maxLength) {
                maxLength = n[i].length();
                a = n[i];
            }
        }
        return a;
    }

    /**
     * Given a rectangular matrix containing only digits, calculate the number of different 2 × 2 squares in it.
     */
    static boolean validTime(String time) {
        String[] a = time.split(":");
        if (a.length > 2) {
            return false;
        }
        if (Integer.parseInt(a[0]) >= 24 || Integer.parseInt(a[1]) >= 60) {
            return false;
        }
        return true;
    }

    /**
     * Writing a function that returns the sum of numbers that appear in the given inputString.
     */
    static int sumUpNumbers(String inputString) {
        String result = inputString.replaceAll("[^0-9 ]", " ");
        String[] a = result.split(" ");
        int sum = 0;
        for (int i = 0; i < a.length; i++) {
            if (a[i].isEmpty()) {
                continue;
            } else {
                sum = sum + Integer.parseInt(a[i]);
            }
        }
        return sum;
    }

    /**
     * Given a rectangular matrix containing only digits, calculate the number of different 2 × 2 squares in it.
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
                StringBuilder sb = new StringBuilder();
                sb.append(matrix[i][j]).append("\t").append(matrix[i][j + 1]).append("\t");
                sb.append(matrix[i + 1][j]).append("\t").append(matrix[i + 1][j + 1]);
                set.add(sb.toString());
            }
        }
        return set.size();
    }

    /**
     * Given an integer product, find the smallest positive (i.e. greater than 0) integer the product of whose digits is equal to product.
     * If there is no such integer, return -1 instead.
     */
    static int digitsProduct(int product) {
        if (product == 0) {
            return 10;
        }
        if (product == 1) {
            return 1;
        }
        int a = 0;
        String b = "";
        int count = 0;

        for (int i = 9; i > 1; i--) {
            while (product % i == 0) {
                product = product / i;
                b = i + b.toString();
            }
        }
        if (product > 1) {
            return -1;
        }
        return Integer.parseInt(b);
    }

    /**
     * You are given an array of desired filenames in the order of their creation. Since two files cannot have equal names, the one which comes later will have an addition to its name in a form of (k),
     * where k is the smallest positive integer such that the obtained name is not used yet.
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
     * You are taking part in an Escape Room challenge designed specifically for programmers.
     * In your efforts to find a clue, you've found a binary code written on the wall behind a vase,
     * and realized that it must be an encrypted message.
     * After some thought, your first guess is that each consecutive 8 bits of the code stand for the character
     * with the corresponding extended ASCII code.
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
     * Construct a square matrix with a size N × N containing integers from 1 to N * N in a spiral order,
     * starting from top-left and in clockwise direction.
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
