package asiantech.internship.summer.Intro;

public class ThroughTheFog {
    /**
     * Consider integer numbers from 0 to n - 1 written down along the circle in such a way that
     * the distance between any two neighboring numbers is equal (note that 0 and n - 1 are neighboring, too).
     * Given n and firstNumber, find the number which is written in the radially opposite position to firstNumber.
     */
    static int circleOfNumbers(int n, int firstNumber) {
        int nua = n / 2;
        int number;
        if (nua > firstNumber) {
            number = nua + firstNumber;
        } else {
            number = firstNumber - nua;
        }
        return number;
    }

    /**
     * You have deposited a specific amount of money into your bank account.
     * Each year your balance increases at the same growth rate.
     * With the assumption that you don't make any additional deposits,
     * find out how long it would take for your balance to pass a specific threshold.
     */
    static int depositProfit(int deposit, int rate, int threshold) {
        int year = 0;
        double de = deposit;
        while (de < threshold) {
            de = de + (de * rate / 100);
            year++;

        }
        return year;
    }

    /**
     * which minimizes the following sum:
     * abs(a[0] - x) + abs(a[1] - x) + ... + abs(a[a.length - 1] - x)
     */
    static int absoluteValuesSumMinimization(int[] a) {
        if (a.length % 2 != 0) {
            return a[a.length / 2];
        } else
            return a[a.length / 2 - 1];
    }

    /**
     * Given an array of equal-length strings,
     * you'd like to know if it's possible to rearrange the order of the elements
     * in such a way that each consecutive pair of strings differ by exactly one character.
     * Return true if it's possible, and false if not.
     */
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
