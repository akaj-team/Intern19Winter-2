package asiantech.internship.summer.intro;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class ExploringtheWaters {

    public static void main(String[] args) {

    }

    /**
     * 14
     * Several people are standing in a row and need
     * to be divided into two teams. The first person
     * goes into team 1, the second goes into team 2,
     * the third goes into team 1 again, the fourth
     * into team 2, and so on.
     *
     * You are given an array of positive integers -
     * the weights of the people. Return an array of
     * two integers, where the first element is the
     * total weight of team 1, and the second element
     * is the total weight of team 2 after the division is complete.
     */
    static int[] alternatingSums(int[] a) {
        int[] b = new int[2];
        int sum1 = 0;
        int sum2 = 0;
        int size = a.length;
        for(int i = 0; i < size; i++){
            if(i % 2 == 0){
                sum1 += a[i];
            } else {
                sum2 += a[i];
            }
            b[0] = sum1;
            b[1] = sum2;
        }
        return b;
    }

    /**
     * 15
     * Given a rectangular matrix of characters, add a border of asterisks(*) to it.
     */
    static String[] addBorder(String[] imgPicture) {
        int x = imgPicture.length + 2;
        String[] s = new String[x];
        int y = imgPicture[0].length() + 1;
        Arrays.fill(s, "");
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y + 1; j++) {
                if (i == 0 || i == x - 1) {
                    s[i] += "*";
                }
            }
            if (i > 0 && i < x - 1 && i < imgPicture.length+1) {
                s[i] = "*" + imgPicture[i-1] + "*";
            }
        }

        return s;
    }

    /**
     * 16
     * WTwo arrays are called similar if one can be obtained
     * from another by swapping at most one pair of elements
     * in one of the arrays.
     *
     * Given two arrays a and b,
     * check whether they are similar.
     */
    static boolean areSimilar(int[] A, int[] B) {
        if(A.length != B.length){
            return false;
        }
        int countSwap = 0;
        int[] copyA = Arrays.copyOf(A, A.length);
        int[] copyB = Arrays.copyOf(B, B.length);

        Arrays.sort(copyA); Arrays.sort(copyB);
        if(!Arrays.equals(copyA, copyB)) {
            return false;
        }
        for(int i = 0; i < A.length; i++) {
            if(A[i] != B[i]) {
                countSwap++;
            }
        }

        return (countSwap == 0 || countSwap == 2);

    }

    /**
     * 17
     * You are given an array of integers.
     * On each move you are allowed to increase exactly
     * one of its element by one. Find the minimal number of
     * moves required to obtain a strictly increasing
     * sequence from the input.
     */
    static int arrayChange(int[] inputArray) {
        int  size = inputArray.length;
        int count = 0;
        for(int i=0; i<size - 1; i++){
            if(inputArray[i] >= inputArray[i+1]){
                do{
                    inputArray[i+1] += 1;
                    count ++;
                } while(inputArray[i] >= inputArray[i+1]);
            }
        }
        return count;
    }

    /**
     * 18
     * Given a string, find
     * out if its characters can be rearranged to form a palindrome.
     */
    static boolean palindromeRearranging(String inputString) {
        Set<Character> oddLetters = new HashSet<>();
        for (char c : inputString.toCharArray()) {
            if ( ! oddLetters.remove(c) ) {
                oddLetters.add(c);
            }
        }
        if (oddLetters.size() <2){
            return true;
        }
        return false;
    }

}
