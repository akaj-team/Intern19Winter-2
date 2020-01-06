package asiantech.internship.summer.Intro;

public class EruptionOfLight {
    static boolean isBeautifulString(String inputString) {
        int[] m = new int[26];
        for (int i: inputString.getBytes()) {
            m[i - 97]++;
        }
        for (int i=1; i<m.length; i++) {
            if (m[i] > m[i-1]) {
                return false;
            }
        }
        return true;
    }
    static String findEmailDomain(String address) {
        String[]a = address.split("@");
        int i;
        i=a.length-1;
        return a[i];
    }
    static String buildPalindrome(String st) {
        String reversed = new StringBuilder(st).reverse().toString();
        String result = st;
        int i = 1;
        while(!isPalindrome(result)) {
            result = st +  reversed.substring(reversed.length() - i, reversed.length());
            i++;
        }
        return result;
    }
    static boolean isPalindrome(String str) {
        return str.equals(new StringBuilder(str).reverse().toString());
    }

    static int electionsWinners(int[] votes, int k) {
        int max=0;
        int win=0;
        int a=0;
        for(int i=0;i<votes.length;i++){
            if(votes[i]>max){
                max=votes[i];
            }
        }
        for(int i=0;i<votes.length;i++){
            if(votes[i]==max){
                a++;
            }
        }
        if(k==0&&a==1){
            return 1;
        }
        for(int i=0;i<votes.length;i++){
            if(votes[i]+k>max){
                win++;
            }
        }

        return win;
    }

}
