package asiantech.internship.summer.intro;

public class ThroughtheFog {

    public static void main(String[] args) {

    }

    /**
     * 30
     * Consider integer numbers from 0 to n - 1
     * written down along the circle in such a
     * way that the distance between any two neighboring
     * numbers is equal (note that 0 and n - 1 are neighboring, too).
     * <p>
     * Given n and firstNumber, find the number which is
     * written in the radially opposite position to firstNumber.
     */
    static int circleOfNumbers(int n, int firstNumber) {
        return (firstNumber + n / 2) % n;
    }


    /**
     * 31
     * Given a sorted array of integers a, your task is
     * to determine which element of a is closest to all
     * other values of a. In other words, find the element
     * x in a, which minimizes the following sum:
     */
    static int depositProfit(int deposit, int rate, int threshold) {
        int years = 0;
        double newDepo = deposit;
        while (newDepo < threshold) {
            newDepo += newDepo * rate / 100.0;
            years++;
        }
        return years;
    }

    /**
     * 32
     * Given a sorted array of integers a,
     * your task is to determine which element of a
     * is closest to all other values of a. In other
     * words, find the element x in a
     */
    static int absoluteValuesSumMinimization(int[] a) {
        if (a.length % 2 == 0) {
            return (a[a.length / 2]) > (a[a.length / 2 - 1]) ? a[a.length / 2 - 1] : a[a.length / 2];
        } else {
            return a[a.length / 2];
        }
    }
}
