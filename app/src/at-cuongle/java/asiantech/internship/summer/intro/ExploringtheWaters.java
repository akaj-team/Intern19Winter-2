package asiantech.internship.summer.intro;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class ExploringtheWaters {
    public static void main(String[] args) {

    }

    public static int[] alternatingSums(int[] a) {
        /**
         * Several people are standing in a row and need to be divided into two teams.
         * The first person goes into team 1, the second goes into team 2, the third goes into
         * team 1 again, the fourth into team 2, and so on.
         *
         * You are given an array of positive integers - the weights of the people.
         * Return an array of two integers, where the first element is the total
         * weight of team 1, and the second element is the total weight of team 2 after
         * the division is complete.
         */
        int[] result = new int[2];
        for (int i = 0; i < a.length; i++) {
            if ((i % 2) == 0) {
                result[0] = result[0] + a[i];
            } else {
                result[1] = result[1] + a[i];
            }
        }
        return result;
    }

    public static String[] addBorder(String[] picture) {
        /**
         * Given a rectangular matrix of characters, add a border of asterisks(*) to it.
         */
        int lengthRow = picture.length + 2;
        int lengthColumn = picture[0].length() + 2;
        String[] result = new String[lengthRow];
        Arrays.fill(result, "");
        for (int i = 0; i < lengthRow; i++) {
            for (int j = 0; j < lengthColumn; j++) {
                if (i == 0 || i == lengthRow - 1) {
                    result[i] += "*";
                } else if (i != 0 && i < lengthRow - 1 && i < picture.length + 1) {
                    result[i] = "*" + picture[i - 1] + "*";
                }
            }
        }
        return result;
    }

    public static boolean areSimilar(int[] A, int[] B) {
        /**
         * Two arrays are called similar if one can be obtained from another by
         * swapping at most one pair of elements in one of the arrays.
         *
         * Given two arrays a and b, check whether they are similar.
         */
        if (A.length != B.length) return false;

        int countSwap = 0;
        int[] copyA = Arrays.copyOf(A, A.length);
        int[] copyB = Arrays.copyOf(B, B.length);

        // checking both contain the same elements...
        Arrays.sort(copyA);
        Arrays.sort(copyB);
        if (!Arrays.equals(copyA, copyB)) return false;

        // checking for min 2 swaps using original arrays...
        for (int i = 0; i < A.length; i++) {
            if (A[i] != B[i]) countSwap++;
        }

        return (countSwap == 2 || countSwap == 0);
    }

    public static int arrayChange(int[] inputArray) {
        /**
         * You are given an array of integers. On each move you are allowed
         * to increase exactly one of its element by one. Find the minimal number
         * of moves required to obtain a strictly increasing sequence from the input.
         */
        int length = inputArray.length;
        int result = 0;
        for (int i = 1; i < length; i++) {
            while (inputArray[i - 1] >= inputArray[i]) {
                ++result;
                ++inputArray[i];
            }
        }
        return result;
    }

    public static boolean palindromeRearranging(String inputString) {
        /**
         * Given a string, find out if its characters can be rearranged to form a palindrome.
         */
        Set<Character> oddChars = new HashSet<>();
        for (char c : inputString.toCharArray()) {
            if (!oddChars.add(c)) {
                oddChars.remove(c);
            }
        }
        return oddChars.size() <= 1;
    }
}
