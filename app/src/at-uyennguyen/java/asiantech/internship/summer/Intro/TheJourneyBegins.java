package asiantech.internship.summer.Intro;

public class TheJourneyBegins {
    static int Sum(int param1, int param2){
        return  param1+param2;
    }
    static int centuryFromYear(int year) {

        int cen;
        cen=year/100;
        if(year%100>0){
            return cen+1;
        }
        else return cen;
    }
    static boolean checkPalindrome(String inputString) {
        int size = inputString.length();
        for(int i=0;i<size/2;i++){
            if(inputString.charAt(i)!= inputString.charAt(size-i-1)){
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        System.out.println(Sum(2,4));
        System.out.println(centuryFromYear(2019));
        System.out.println(checkPalindrome("abccba"));
    }
}
