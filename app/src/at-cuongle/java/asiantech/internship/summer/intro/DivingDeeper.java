package asiantech.internship.summer.intro;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class DivingDeeper {
    public static void main(String[] args) {

    }

    /**
     * Given array of integers, remove each kth element from it.
     */
    public static int[] extractEachKth(int[] inputArray, int k) {
        ArrayList<Integer> coppyInput = new ArrayList<>();
        int cout = 1;
        for (int value : inputArray) {
            if (cout == k) {
                cout = 1;
            } else {
                cout++;
                coppyInput.add(value);
            }
        }
        int[] result = new int[coppyInput.size()];
        for (int i = 0; i < coppyInput.size(); i++) {
            result[i] = coppyInput.get(i);
        }
        return result;
    }

    /**
     * Find the leftmost digit that occurs in a given string.
     */
    public static char firstDigit(String inputString) {
        char result = 'c';
        for (int i = 0; i < inputString.length(); i++) {
            char c = inputString.charAt(i);
            if (Character.isDigit(c)) {
                result = inputString.charAt(i);
                break;
            }
        }
        return result;
    }

    /**
     * Given a string, find the number of different characters in it.
     */
    public static int differentSymbolsNaive(String s) {
        Set<Character> result = new HashSet<Character>();
        for (int i = 0; i < s.length(); i++) {
            result.add(s.charAt(i));
        }
        return result.size();
    }

    /**
     * Given array of integers, find the maximal possible sum of some of its
     * k consecutive elements.
     */
    public static int arrayMaxConsecutiveSum(int[] nums, int k) {
        int result = 0;
        int temp_sum = 0;
        for (int i = 0; i < k - 1; i++) {
            temp_sum += nums[i];
        }
        for (int i = k - 1; i < nums.length; i++) {
            temp_sum += nums[i];
            if (temp_sum > result) {
                result = temp_sum;
            }
            temp_sum -= nums[i - k + 1];
        }
        return result;
    }
}
