package asiantech.internship.summer.intro;

import java.util.ArrayList;
import java.util.Arrays;

public class SmoothSailing {
    /* Given an array of strings,
     * return another array containing all of its longest strings.
     */
    public static String[] allLongestStrings(String[] inputArray) {
        ArrayList<String> answer = new ArrayList<>(Arrays.asList(inputArray[0]));
        for (int i = 1; i < inputArray.length; i++) {
            if (inputArray[i].length() == answer.get(0).length()) {
                answer.add(inputArray[i]);
            }
            if (inputArray[i].length() > answer.get(0).length()) {
                answer = new ArrayList<>(Arrays.asList(inputArray[i]));
            }
        }
        return answer.toArray(new String[answer.size()]);
    }

    /*
     * Given two strings, find the number of
     * common characters between them.
     * */
    public static int commonCharacterCount(String s1, String s2) {
        int count = 0;
        boolean[] t = new boolean[s2.length()];
        for (int j = 0; j < s2.length(); j++) {
            System.out.println(t[j]);
        }
        for (int i = 0; i < s1.length(); i++) {
            for (int j = 0; j < s2.length(); j++) {
                if (s1.charAt(i) == s2.charAt(j) && !t[j]) {
                    t[j] = true;
                    count++;
                    break;
                }
            }
        }
        return count;
    }

    /*
     *Ticket numbers usually consist of an even number of digits.
     * A ticket number is considered lucky
     * if the sum of the first half of the digits is equal to the sum of the second half.
     *
     * */
    public static boolean isLucky(int n) {
        String str = Integer.toString(n);
        char[] charArray = str.toCharArray();
        int len = charArray.length;
        int firstHalf = 0, secondHalf = 0;
        for (int i = 0, j = len - 1; i < j; i++, j--) {
            firstHalf += Character.getNumericValue(charArray[i]);
            secondHalf += Character.getNumericValue(charArray[j]);
        }
        if (firstHalf == secondHalf) {
            return true;
        }
        return false;
    }

    /*Some people are standing in a row in a park. There are trees between them which cannot be moved.
     *Your task is to rearrange the people by their heights in a non-descending order without moving the trees.
     *People can be very tall!*/
    public static int[] sortByHeight(int[] a) {
        int temp = 0;
        for (int i = 0; i < a.length - 1; i++) {
            for (int j = i + 1; j < a.length; j++) {
                if (a[i] > a[j] && a[i] != -1 && a[j] != -1) {
                    temp = a[i];
                    a[i] = a[j];
                    a[j] = temp;
                }
            }
        }
        return a;
    }
    /*
    *Write a function that reverses characters in (possibly nested) parentheses in the input string.
    *Input strings will always be well-formed with matching ()s
    * */
    public static String reverseInParentheses(String inputString) {
        String tmpCh = new String("");
        String tmpChRe = new String("");
        String tmp = new String("");
        int l = inputString.length();
        int n = 0;
        int j = 0;
        for (int i = 0; i < l; i++) {
            if (inputString.charAt(i) == '(')
                n++;
        }
        System.out.println(n);
        int T[] = new int[n];
        for (int i = 0; i < l; i++) {
            if (inputString.charAt(i) == '(') {
                T[j] = i;
                j++;
            }
        }
        j = 0;
        while (n > 0) {
            j = T[n - 1] + 1;
            System.out.println(j);
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

    public static void main(String[] args) {

    }


}
