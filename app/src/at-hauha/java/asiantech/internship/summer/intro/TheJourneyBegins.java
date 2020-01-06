package asiantech.internship.summer.intro;

public class TheJourneyBegins {
    /**
     * Write a function that returns the sum of two numbers.
     */
    public  static int add (int a, int b){
        return  a+b;
    }
    /**
     * Given a year, return the century it is in.
     */
    public  static int centuryFromYear(int year){
        int c =0;
        int a =  year/100;
        int b = year % 100;
        if(b!=0) {
            c = a+1;
            return c;
        }else
            return a;
    }
    /**
     * Given the string, check if it is a palindrome.
     */
    public static boolean checkPalindrome(String inputString) {
        int n = inputString.length();
        for (int i = 0; i < (n / 2); ++i) {
            if (inputString.charAt(i) != inputString.charAt(n - i - 1)) {
                return false;
            }
        }

        return true;
    }
    public static void main(String[] args) {

    }
}
