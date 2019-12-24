package asiantech.internship.summer.intro;

public class TheJourneyBegins {
    public static void main(String[] args) {
        add(1, 2);
        centuryFromYear(2019);
        checkPalindrome("hello");
    }

    public static int add(int param1, int param2) {
        /**
         * Write a function that returns the sum of two numbers.
         */
        return param1 + param2;
    }

    public static int centuryFromYear(int year) {
        /**
         * Given a year, return the century it is in.
         The first century spans from the year 1 up to and including the year 100,
         the second - from the year 101 up to and including the year 200, etc.
         */
        int century = year / 100;
        int residual = year % 100;
        if (residual != 0) {
            century = century + 1;
        }
        return (century);
    }

    public static boolean checkPalindrome(String inputString) {
        /**
         * Given the string, check if it is a palindrome.
         */
        int lengthString = inputString.length();
        for (int i = 0; i < inputString.length() / 2; i++) {

            if (inputString.charAt(i) != inputString.charAt(lengthString - i - 1)) {
                return false;
            }
        }
        return true;
    }
}
