package asiantech.internship.summer.intro;

public class ThroughtheFog {
    public static void main(String[] args) {

    }

    /**
     * Consider integer numbers from 0 to n - 1 written down along the circle in such a way that
     * the distance between any two neighboring numbers is equal (note that 0 and n - 1 are neighboring, too).
     * Given n and firstNumber, find the number which is written in the radially opposite position to firstNumber.
     */
    public static int circleOfNumbers(int n, int firstNumber) {
        int lenght = n / 2;
        for (int i = 0; i < n; i++) {
            if (i == firstNumber && firstNumber < lenght) {
                firstNumber = firstNumber + lenght;
                return firstNumber;
            } else if (firstNumber >= lenght) {
                return (firstNumber - lenght);
            }
        }
        return firstNumber;
    }

    /**
     * You have deposited a specific amount of money into your bank account. Each year your balance
     * increases at the same growth rate. With the assumption that you don't make any additional deposits,
     * find out how long it would take for your balance to pass a specific threshold.
     */
    public static int depositProfit(int deposit, int rate, int threshold) {
        int cout = 0;
        double newDeposit = deposit;
        while (newDeposit < threshold) {
            newDeposit = newDeposit + (newDeposit * rate) / 100;
            cout++;
        }
        return cout;
    }

    /**
     * Given a sorted array of integers a, your task is to determine which element of a is closest to
     * all other values of a. In other words, find the element x in a, which minimizes the following sum:
     */
    public static int absoluteValuesSumMinimization(int[] a) {
        int[] smallLest = new int[a.length];
        int check = Integer.MAX_VALUE, result = 0;
        for (int value : a) {
            for (int j = 0; j < a.length; j++) {
                smallLest[j] = smallLest[j] + Math.abs(a[j] - value);
            }
        }
        for (int i = 0; i < a.length; i++) {
            if (smallLest[i] < check) {
                check = smallLest[i];
                result = a[i];
            }
        }
        return result;
    }
}
