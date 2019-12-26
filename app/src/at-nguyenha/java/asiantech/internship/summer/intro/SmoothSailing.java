package asiantech.internship.summer.intro;

import java.util.ArrayList;

public class SmoothSailing {

    public static void main(String[] args) {

    }

    /**
     * Given an array of strings, return another
     * array containing all of its longest strings.
     */
    static String[] allLongestStrings(String[] inputArray) {
        int maxLength = 1;
        ArrayList<String> answer = new ArrayList<String>();
        for (int i = 0; i < inputArray.length; i++) {
            if (inputArray[i].length() > maxLength) {
                maxLength = inputArray[i].length();
            }
        }
        for (int j = 0; j < inputArray.length; j++){
            if(inputArray[j].length() == maxLength){
                answer.add(inputArray[j]);
            }
        }
        return answer.toArray(new String[0]);
    }

    /**
     * Given two strings, find the number of common characters between them.
     */
    static int commonCharacterCount(String s1, String s2) {

        int count = 0;
        boolean[] c = new boolean[s2.length()];
        for(int i=0; i<s1.length(); i++){
            for(int j=0; j < s2.length(); j++){
                if(s1.charAt(i) == s2.charAt(j) && !c[j]){
                    count += 1;
                    c[j] = true;
                    break;
                }
            }
        }
        return count;
    }

    /**
     * Ticket numbers usually consist of an even number of digits.
     * A ticket number is considered lucky if the sum of the first half of
     * the digits is equal to the sum of the second half.
     *
     * Given a ticket number n, determine if it's lucky or not.
     */
    static boolean isLucky(int n) {
        String tempStr = Integer.toString(n);
        char[] charArray = tempStr.toCharArray();
        int len = charArray.length;
        int firstHalf = 0, secondHalf = 0;
        for(int i = 0, j=len-1; i<j; i++,j-- ){
            firstHalf += Character.getNumericValue(charArray[i]);
            secondHalf +=Character.getNumericValue(charArray[j]);
        }
        return firstHalf == secondHalf;
    }

    /**
     * ome people are standing in a row in a park.
     * There are trees between them which cannot be moved.
     * Your task is to rearrange the people by their heights
     * in a non-descending order without moving the trees. People can be very tall!
     */
    static int[] sortByHeight(int[] a) {
        boolean swapped = true;
        int j = 0;
        int tmp;
        while (swapped) {
            swapped = false;
            j++;
            for (int i = 0, k = 0; i < a.length - j; i++){
                if (a[i] == -1) {
                    k = 0;
                    continue;
                }
                while (i + 1 + k < a.length && a[i + 1 + k] == -1) {
                    k++;
                }
                if (i + 1 + k < a.length && a[i] > a[i + 1 + k]) {
                    tmp = a[i];
                    a[i] = a[i + 1 + k];
                    a[i + 1 + k] = tmp;
                    k = 0;
                    swapped = true;
                }
            }
        }
        return a;
    }

    /**
     * Write a function that reverses characters in
     * (possibly nested) parentheses in the input string.
     */
    static String reverseInParentheses(String inputString) {
        String tmpCh = new String("");
        String tmpChRe = new String("");
        String tmp = new String("");
        int l = inputString.length();
        int n = 0;
        int j =0;
        for(int i = 0;i<l;i++){
            if(inputString.charAt(i)=='('){
                n++;
            }
        }
        int T[] = new int[n];
        for(int i=0;i<l;i++){
            if(inputString.charAt(i)=='('){
                T[j]=i;
                j++;
            }
        }
        j=0;
        while(n>0){
            j = T[n-1] + 1;
            while(inputString.charAt(j)!=')'){
                tmpCh = tmpCh + inputString.charAt(j);
                j++;
            }
            for(int q=tmpCh.length()-1;q>=0;q--){
                tmpChRe = tmpChRe + tmpCh.charAt(q);
            }
            tmp = inputString.substring(0,T[n-1]) + tmpChRe +
                    inputString.substring(T[n-1]+tmpChRe.length()+2);
            inputString = tmp;
            n--;
            tmp = "";
            tmpCh = "";
            tmpChRe = "";
        }
        return inputString ;
    }

}
