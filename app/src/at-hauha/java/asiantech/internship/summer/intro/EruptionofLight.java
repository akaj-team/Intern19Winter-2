package asiantech.internship.summer.intro;

import java.nio.charset.Charset;

public class EruptionofLight {
    /**
     * Given a string, check whether it is beautiful.
     */
    boolean isBeautifulString(String inputString) {
        int[] m = new int[26];
<<<<<<< HEAD
        for (int i : inputString.getBytes(Charset.forName ("UTF-8"))) {
=======
        for (int i : inputString.getBytes()) {
>>>>>>> ddccd5e33e373a5e616d13bb322cbdbe175a88a0
            m[i - 97]++;
        }
        for (int i = 1; i < m.length; i++) {
            if (m[i] > m[i - 1]) {
                return false;
            }
        }
        return true;
    }

    /**
     * Given a valid email address, find its domain part
     */
    String findEmailDomain(String address) {
        String s = "";
        for (int i = 0; i < address.length(); i++) {
            if (address.charAt(i) == '@') {
                s = address.substring(i + 1);
            }
        }
        return s;
    }

    /**
     * Given a string, find the shortest possible string which can be achieved by
     * adding characters to the end of initial string to make it a palindrome.
     */
    String buildPalindrome(String st) {
        String reversed = new StringBuilder(st).reverse().toString();
        String result = st;
        int i = 1;
        while (!isPalindrome(result)) {
            result = st + reversed.substring(reversed.length() - i, reversed.length());
            i++;
        }
        return result;
    }

    boolean isPalindrome(String str) {
        return str.equals(new StringBuilder(str).reverse().toString());
    }

    /**
     * Given an array of the numbers of votes given to each of the candidates so far, and an integer
     * k equal to the number of voters who haven't cast their vote yet,
     * find the number of candidates who still have a chance to win the election.
     */
    int electionsWinners(int[] votes, int k) {
        int max = 0;
        for (int i = 0; i < votes.length; i++) {
            max = Math.max(max, votes[i]);
        }
        int winner = 0;
        for (int i = 0; i < votes.length; i++) {
            if (votes[i] + k > max) {
                winner++;
            }
        }
        if (winner == 0) {
            for (int i = 0; i < votes.length; i++) {
                if (votes[i] == max) {
                    winner++;

                }
            }
            if (winner > 1) {
                winner = 0;
            }
        }
        return winner;
    }

    /**
     * Your task is to check by given string inputString whether it corresponds to MAC-48 address or not.
     */
    boolean isMAC48Address(String inputString) {
        String[] str = inputString.split("\\-");
        if (!inputString.matches(".+[0-9A-F]$")) {
            return false;
        }
        if (str.length != 6) {
            return false;
        }
        int counter = 0;
        for (int i = 0; i < str.length; i++) {
            if (str[i].matches("^[0-9A-F][0-9A-F]")) {
                counter++;
            }
        }
        if (counter == str.length) {
            return true;
        }
        return false;
    }

}
