package asiantech.internship.summer.Intro;

public class ThroughTheFog {
    static int circleOfNumbers(int n, int firstNumber) {
        int nua= n/2;
        int number;
        if(nua>firstNumber){
            number=nua+firstNumber;
        }
        else{
            number=firstNumber-nua;
        }
        return number;
    }
    static int depositProfit(int deposit, int rate, int threshold) {
        int year=0;
        double de = deposit;
        while(de<threshold){
            de= de+(de*rate/100);
            year++;

        }
        return year;
    }
    static int absoluteValuesSumMinimization(int[] a) {
        if(a.length%2!=0){
            return a[a.length/2];
        }
        else
            return a[a.length/2-1];
    }

    static boolean stringsRearrangement(String[] inputArray) {
        return permute(inputArray, 0);
    }

    static boolean permute(String[] a, int k) {
        boolean achou = false;
        if (k == a.length) {
            achou = true;
            for (int j = 0; j < a.length - 1; j++) {
                if (diff(a[j], a[j + 1]) != 1) {
                    achou = false;
                    break;
                }
            }
            if (achou) {
                return true;
            }
        } else {
            for (int i = k; i < a.length; i++) {
                String temp = a[k];
                a[k] = a[i];
                a[i] = temp;
                if (permute(a, k + 1)) {
                    return true;
                }
                temp = a[k];
                a[k] = a[i];
                a[i] = temp;
            }
        }
        return false;
    }
    static int diff(String w1, String w2) {
        char[] first = w1.toCharArray();
        char[] second = w2.toCharArray();
        int counter = 0;
        int minLength = Math.min(first.length, second.length);
        for (int i = 0; i < minLength; i++) {
            if (first[i] != second[i]) {
                counter++;
            }
        }
        return counter;
    }
}
