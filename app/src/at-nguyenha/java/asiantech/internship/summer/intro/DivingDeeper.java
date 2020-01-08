package asiantech.internship.summer.intro;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class DivingDeeper {

    public static void main(String[] args) {

    }

    /**
     * 34
     * Given array of integers, remove each k element from it.
     */
    int[] extractEachKth(int[] inputArray, int k) {
        ArrayList<Integer> ans = new ArrayList<Integer>();
        int j = 1;
        for (int value : inputArray) {
            if (j == k) {
                j = 1;
            } else {
                j++;
                ans.add(value);
            }
        }
        int[] result = new int[ans.size()];
        for (int i = 0; i < ans.size(); i++) {
            result[i] = ans.get(i);
        }
        return result;
    }

    /**
     * 35
     * Find the leftmost digit that occurs in a given string.
     */
    char firstDigit(String inputString) {
        int size = inputString.length();
        for (int i = 0; i < size; i++) {
            if (inputString.charAt(i) >= '0' && inputString.charAt(i) <= '9') {
                return inputString.charAt(i);
            }
        }
        return ' ';
    }

    /**
     * 36
     * Given a string, find the number of different characters in it.
     */
    int differentSymbolsNaive(String s) {
        Set<Character> count = new HashSet<>();
        for (int i = 0; i < s.length(); i++) {
            count.add(s.charAt(i));
        }
        return count.size();
    }

    /**
     * 37
     * Given array of integers, find the maximal
     * possible sum of some of its k consecutive elements.
     */
    int arrayMaxConsecutiveSum(int[] inputArray, int k) {
        int max = 0;
        int Sum = 0;
        for (int i = 0; i < k - 1; i++) {
            Sum += inputArray[i];
        }
        for (int i = k - 1; i < inputArray.length; i++) {
            Sum += inputArray[i];
            if (Sum > max) {
                max = Sum;
            }
            Sum -= inputArray[i - k + 1];
        }
        return max;
    }

}
