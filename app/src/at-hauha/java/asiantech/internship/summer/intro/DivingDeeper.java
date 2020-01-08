package asiantech.internship.summer.intro;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class DivingDeeper {
    /**
     * Given array of integers, remove each kth element from it.
     */
    public static int[] extractEachKth(int[] inputArray, int k) {
        ArrayList<Integer> ans = new ArrayList<Integer>();
        int j = 1;
        for (int i = 0; i < inputArray.length; i++) {
            if (j == k) {
                j = 1;
                continue;
            } else {
                j++;
                ans.add(inputArray[i]);
            }
        }
        int[] result = new int[ans.size()];
        for (int i = 0; i < ans.size(); i++) {
            result[i] = ans.get(i);
        }
        return result;
    }

    /**
     * Find the leftmost digit that occurs in a given string.
     */
    public static char firstDigit(String inputString) {
        char c = ' ';
        for (int i = 0; i < inputString.length(); i++) {
            String str = Character.toString(inputString.charAt(i));
            if (str.matches("[0-9]")) {
                c = inputString.charAt(i);
                break;
            }
        }
        return c;
    }

    /**
     * Given a string, find the number of different characters in it
     */
    public static int differentSymbolsNaive(String s) {
        Set<Character> set = new HashSet<Character>();
        for (int i = 0; i < s.length(); i++) {
            set.add(s.charAt(i));
        }
        return set.size();
    }

    /**
     * Given array of integers, find the maximal possible sum of some of its k consecutive elements.
     */
    public static int arrayMaxConsecutiveSum(int[] inputArray, int k) {
        int sum = 0;
        int max = 0;
        for (int i = 0; i < k - 1; i++) {
            sum += inputArray[i];
        }
        for (int i = k - 1; i < inputArray.length; i++) {
            sum += inputArray[i];
            if (sum > max) {
                max = sum;
            }
            sum -= inputArray[i - k + 1];
        }
        return max;
    }


    public static void main(String[] args) {

    }
}
