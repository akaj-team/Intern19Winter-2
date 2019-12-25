package asiantech.internship.summer.intro;

public class TheJourneyBegin {

    static int add(int param1, int param2) {
        /**
         * Write a function that returns the sum of two numbers.
         */
        return param1+param2;
    }

    static int centuryFromYear(int year) {
        /**
         * Given a year, return the century it is in. The first
         * century spans from the year 1 up to and including the year 100,
         * the second - from the year 101 up to and including the year 200,
         * etc.
         */
        int resident = year/100;
        if(year%100 != 0){
            resident += 1;
        }
        return resident;
    }

    static boolean checkPalindrome(String inputString) {
        /**
         * check if it is a palindrome.
         */
        int s = inputString.length();
        for (int i=0;i<s/2;i++){
            if(inputString.charAt(i) != inputString.charAt(s-i-1)){
                return false;
            }
        }
        return true;
    }


    public static void main(String[] args) {
        System.out.println("Sum : " + add(1,2));

        System.out.println("Century is : " + centuryFromYear(2001));

        if(!checkPalindrome("abcddcba")){
            System.out.println("It is a palindrome");
        }
        else {
            System.out.println("It is not a palindrome");
        }

    }
}
