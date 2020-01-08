package asiantech.internship.summer.intro;

public class ThroughtheFrog {
    /**
     * Given n and firstNumber, find the number which is written in the radially opposite position to firstNumber.
     */
    public static int circleOfNumbers(int n, int firstNumber) {
        return (firstNumber + n / 2) % n;
    }

    /**
     * You have deposited a specific amount of money into your bank account.
     * Each year your balance increases at the same growth rate.
     * With the assumption that you don't make any additional deposits,
     * find out how long it would take for your balance to pass a specific threshold.
     */
    public static int depositProfit(int deposit, int rate, int threshold) {
        double money = deposit;
        double newRate = rate;
        int counter = 0;
        while (money < threshold) {
            money += money * newRate / 100;
            counter++;
        }
        return counter;
    }

    /**
     * Given a sorted array of integers a,
     * your task is to determine which element of a is closest to all other values of a.
     * In other words, find the element x in a, which minimizes the following sum
     * If there are several possible answers, output the smallest one.
     */
    public static int absoluteValuesSumMinimization(int[] a) {
        int sumMin = 0;
        int smallest = a[0];
        for (int i = 0; i < a.length; i++) {
            if (a[i] == 0) {
                smallest = 0;
            }
        }
        for (int i = 0; i < a.length; i++) {
            sumMin += Math.abs(a[i]);
        }
        for (int i = 0; i < a.length; i++) {
            int sum = 0;
            for (int j = 0; j < a.length; j++) {
                if (i != j) {
                    sum += Math.abs(a[j] - a[i]);
                }
            }
            if (sum < sumMin) {
                sumMin = sum;
                smallest = a[i];
            }
        }
        return smallest;
    }

    /**
     * Given an array of equal-length strings, you'd like to know if it's
     * possible to rearrange the order of the elements in such a way that each consecutive
     * pair of strings differ by exactly one character.
     * Return true if it's possible, and false if not.
     */
    public static boolean stringsRearrangement(String[] inputArray) {
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

    public static void main(String[] args) {

    }

}
