package asiantech.internship.summer.intro;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class ExploringtheWaters {

    /**
     * You are given an array of positive integers - the weights of the people.
     * Return an array of two integers, where the first element is the total weight of team 1,
     * and the second element is the total weight of team 2 after the division is complete.
     */
    public static int[] alternatingSums(int[] a) {
        int[] b = new int[2];
        for (int i = 0; i < a.length; i++) {
            if (i % 2 == 0) {
                b[0] += a[i];
            } else {
                b[1] += a[i];
            }
        }
        return b;
    }

    /**
     * Given a rectangular matrix of characters, add a border of asterisks(*) to it.
     */
    public static String[] addBorder(String[] picture) {

        int x = picture.length + 2;
        String[] s = new String[x];
        int y = picture[0].length() + 1;
        Arrays.fill(s, "");
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y + 1; j++) {
                if (i == 0 || i == x - 1) {
                    s[i] += "*";
                }
            }
            if (i > 0 && i < x - 1 && i < picture.length + 1) {
                s[i] = "*" + picture[i - 1] + "*";
            }

        }

        return s;

    }

    /**
     * Given two arrays a and b, check whether they are similar.
     */

    public static boolean areSimilar(int[] a, int[] b) {
        ArrayList<Integer> ids = new ArrayList<>();
        for (int i = 0; i < a.length; i++) {
            if (a[i] != b[i]) {
                ids.add(i);
            }
        }
        if (ids.size() == 0) {
            return true;
        }
        if (ids.size() != 2) {
            return false;
        }
        int id1 = ids.get(0);
        int id2 = ids.get(1);
        if (a[id1] == b[id2] && a[id2] == b[id1]) {
            return true;
        }
        return false;
    }

    /**
     * You are given an array of integers. On each move you are allowed to increase exactly one of its element by one.
     * Find the minimal number of moves required to obtain a strictly increasing sequence from the input.
     */
    public static int arrayChange(int[] inputArray) {
        int[] b = Arrays.copyOf(inputArray, inputArray.length);
        int move = 0;
        int tmp_move = 0;
        for (int i = 0; i < inputArray.length - 1; i++) {
            if (b[i] >= inputArray[i + 1]) {
                tmp_move = b[i] - inputArray[i + 1] + 1;
                b[i + 1] = inputArray[i + 1] + tmp_move;
            }
        }
        for (int i = 0; i < inputArray.length; i++) {
            move += b[i] - inputArray[i];
        }
        return move;
    }

    /**
     * Given a string, find out if its characters can be rearranged to form a palindrome.
     */
    public static boolean palindromeRearranging(String inputString) {
        Set<Character> oddLetters = new HashSet<>();
        for (char c : inputString.toCharArray()) {
            if (!oddLetters.remove(c)) {
                oddLetters.add(c);

            }
        }
        if (oddLetters.size() < 2) {
            return true;
        }
        return false;
    }

    public static void main(String[] args) {

    }

}
