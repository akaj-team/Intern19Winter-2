package asiantech.internship.summer.intro;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SmoothSailing {
    public static void main(String[] args) {

    }

    public static String[] allLongestStrings(String[] inputArray) {
        /**
         * Given an array of strings, return another array
         * containing all of its longest strings.
         */
        int maxLength = 1;
        ArrayList<String> resultArray = new ArrayList<>();

        for (int i = 0; i < inputArray.length; i++) {
            if (inputArray[i].length() > maxLength) {
                maxLength = inputArray[i].length();
            }
        }
        for (int j = 0; j < inputArray.length; j++) {
            if (inputArray[j].length() == maxLength) {
                resultArray.add(inputArray[j]);
            }
        }
        return resultArray.toArray(new String[0]);
    }

    public static int commonCharacterCount(String str1, String str2) {
        /**
         * Given two strings, find the number of common characters between them.
         */
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

    public static boolean isLucky(final int n) {
        /**
         * Ticket numbers usually consist of an even number of digits. A ticket number
         * is considered lucky if the sum of the first half of the digits is equal to
         * the sum of the second half.
         *
         * Given a ticket number n, determine if it's lucky or not.
         */
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

    public static int[] sortByHeight(int[] a) {
        /**
         * Some people are standing in a row in a park. There are trees between
         * them which cannot be moved. Your task is to rearrange the people by
         * their heights in a non-descending order without moving the trees.
         * People can be very tall!
         */
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
            } else
                result[i] = a[i];
        }
        return result;
    }

    public static String reverseInParentheses(String inputString) {
        /**
         * Write a function that reverses characters in (possibly nested) parentheses in the input string.
         *
         * Input strings will always be well-formed with matching ()s.
         */
        String tmpCh = "";
        String tmpChRe = "";
        String tmp = "";
        int length = inputString.length();
        int n = 0;
        int j = 0;
        for (int i = 0; i < length; i++) {
            if (inputString.charAt(i) == '(')
                n++;
        }
        int T[] = new int[n];
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
                tmpCh = tmpCh + inputString.charAt(j);
                j++;
            }
            for (int q = tmpCh.length() - 1; q >= 0; q--)
                tmpChRe = tmpChRe + tmpCh.charAt(q);
            tmp = inputString.substring(0, T[n - 1]) + tmpChRe + inputString.substring(T[n - 1] + tmpChRe.length() + 2);
            inputString = tmp;
            n--;
            tmp = "";
            tmpCh = "";
            tmpChRe = "";
        }
        return inputString;
    }

}
