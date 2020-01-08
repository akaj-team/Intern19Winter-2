package asiantech.internship.summer.intro;

import java.util.ArrayList;
import java.util.Arrays;

public class SmoothSailing {
    public static void main(String[] args) {

    }

    /**
     * Given an array of strings, return another array
     * containing all of its longest strings.
     */
    public static String[] allLongestStrings(String[] inputArray) {
        int maxLength = 1;
        ArrayList<String> resultArray = new ArrayList<>();

        for (String s : inputArray) {
            if (s.length() > maxLength) {
                maxLength = s.length();
            }
        }
        for (String s : inputArray) {
            if (s.length() == maxLength) {
                resultArray.add(s);
            }
        }
        return resultArray.toArray(new String[0]);
    }

    /**
     * Given two strings, find the number of common characters between them.
     */
    public static int commonCharacterCount(String str1, String str2) {
        int result = 0;
        boolean[] isStatus = new boolean[str2.length()];
        if (str1.length() == 0 || str2.length() == 0) {
            return result;
        } else {
            for (int i = 0; i < str1.length(); i++) {
                for (int j = 0; j < str2.length(); j++) {
                    if (str1.charAt(i) == str2.charAt(j) && !isStatus[j]) {
                        result++;
                        isStatus[j] = true;
                        break;
                    }
                }
            }
        }
        return result;
    }

    /**
     * Ticket numbers usually consist of an even number of digits. A ticket number
     * is considered lucky if the sum of the first half of the digits is equal to
     * the sum of the second half.
     * Given a ticket number n, determine if it's lucky or not.
     */
    public static boolean isLucky(final int n) {

        final String tempStr = Integer.toString(n);
        final char[] charArray = tempStr.toCharArray();
        final int lengthString = charArray.length;
        int firstHalf = 0, secondHalf = 0;
        for (int i = 0, j = lengthString - 1; i < j; i++, j--) {
            firstHalf += Character.getNumericValue(charArray[i]);
            secondHalf += Character.getNumericValue(charArray[j]);
        }
        return firstHalf == secondHalf;
    }

    /**
     * Some people are standing in a row in a park. There are trees between
     * them which cannot be moved. Your task is to rearrange the people by
     * their heights in a non-descending order without moving the trees.
     * People can be very tall!
     */
    public static int[] sortByHeight(int[] a) {

        int[] result = new int[a.length];
        int[] copyArray = new int[a.length];
        System.arraycopy(a, 0, copyArray, 0, a.length);
        Arrays.sort(copyArray);
        for (int i = 0, j = 0; i < a.length; i++) {
            if (a[i] != -1) {
                while (copyArray[j] == -1) {
                    j++;
                }
                result[i] = copyArray[j];
                j++;
            } else {
                result[i] = a[i];
            }
        }
        return result;
    }

    /**
     * Write a function that reverses characters in (possibly nested) parentheses in the input string.
     * Input strings will always be well-formed with matching ()s.
     */
    public static String reverseInParentheses(String inputString) {
        StringBuilder tmpCh = new StringBuilder();
        StringBuilder tmpChRe = new StringBuilder();
        String tmp = "";
        int length = inputString.length();
        int n = 0;
        int j = 0;
        for (int i = 0; i < length; i++) {
            if (inputString.charAt(i) == '(') {
                n++;
            }
        }
        int[] T = new int[n];
        for (int i = 0; i < length; i++) {
            if (inputString.charAt(i) == '(') {
                T[j] = i;
                j++;
            }
        }
        j = 0;
        while (n > 0) {
            j = T[n - 1] + 1;
            while (inputString.charAt(j) != ')') {
                tmpCh.append(inputString.charAt(j));
                j++;
            }
            for (int q = tmpCh.length() - 1; q >= 0; q--) {
                tmpChRe.append(tmpCh.charAt(q));
            }
            tmp = inputString.substring(0, T[n - 1]) + tmpChRe + inputString.substring(T[n - 1] + tmpChRe.length() + 2);
            inputString = tmp;
            n--;
            tmp = "";
            tmpCh = new StringBuilder();
            tmpChRe = new StringBuilder();
        }
        return inputString;
    }
}
