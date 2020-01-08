package asiantech.internship.summer.Intro;

public class TheJourneyBegins {
    /**
     * Write a function that returns the sum of two numbers.
     */
    static int Sum(int param1, int param2) {
        return param1 + param2;
    }

    /**
     * Given a year, return the century it is in.
     * The first century spans from the year 1 up to and including the year 100,
     * the second - from the year 101 up to and including the year 200, etc.
     */
    static int centuryFromYear(int year) {

        int cen;
        cen = year / 100;
        if (year % 100 > 0) {
            return cen + 1;
        } else return cen;
    }

    /**
     * Given the string, check if it is a palindrome.
     */
    static boolean checkPalindrome(String inputString) {
        int size = inputString.length();
        for (int i = 0; i < size / 2; i++) {
            if (inputString.charAt(i) != inputString.charAt(size - i - 1)) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {

    }
}
