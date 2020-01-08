package asiantech.internship.summer.intro;

public class TheJourneyBegins {
    public static void main(String[] args) {

    }

    /**
     * Write a function that returns the sum of two numbers.
     */
    public static int add(int param1, int param2) {
        return param1 + param2;
    }

    /**
     * Given a year, return the century it is in.
     * The first century spans from the year 1 up to and including the year 100,
     * the second - from the year 101 up to and including the year 200, etc.
     */
    public static int centuryFromYear(int year) {
        int century = year / 100;
        int residual = year % 100;
        if (residual != 0) {
            century = century + 1;
        }
        return (century);
    }

    /**
     * Given the string, check if it is a palindrome.
     */
    public static boolean checkPalindrome(String inputString) {
        int lengthString = inputString.length();
        for (int i = 0; i < inputString.length() / 2; i++) {

            if (inputString.charAt(i) != inputString.charAt(lengthString - i - 1)) {
                return false;
            }
        }
        return true;
    }
}
