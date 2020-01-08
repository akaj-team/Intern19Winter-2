package asiantech.internship.summer.Intro;

public class EruptionOfLight {
    /**
     * A string is said to be beautiful
     * if each letter in the string appears at most as many times as the previous letter
     * in the alphabet within the string;
     * ie: b occurs no more times than a; c occurs no more times than b; etc.
     * Given a string, check whether it is beautiful.
     */
    static boolean isBeautifulString(String inputString) {
        int[] m = new int[26];
        for (int i : inputString.getBytes()) {
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
     * The domain name part of an email address may only consist of letters, digits, hyphens and dots.
     * The local part, however, also allows a lot of different special characters.
     * Here you can look at several examples of correct and incorrect email addresses.
     * Given a valid email address, find its domain part.
     */
    static String findEmailDomain(String address) {
        String[] a = address.split("@");
        int i;
        i = a.length - 1;
        return a[i];
    }

    /**
     * Given a string,
     * find the shortest possible string which can be achieved by adding characters to the end of initial string to make it a palindrome.
     */
    static String buildPalindrome(String st) {
        String reversed = new StringBuilder(st).reverse().toString();
        String result = st;
        int i = 1;
        while (!isPalindrome(result)) {
            result = st + reversed.substring(reversed.length() - i, reversed.length());
            i++;
        }
        return result;
    }

    static boolean isPalindrome(String str) {
        return str.equals(new StringBuilder(str).reverse().toString());
    }

    /**
     * Given an array of the numbers of votes given to each of the candidates so far,
     * and an integer k equal to the number of voters who haven't cast their vote yet,
     * find the number of candidates who still have a chance to win the election.
     * The winner of the election must secure strictly more votes than any other candidate.
     * If two or more candidates receive the same (maximum) number of votes, assume there is no winner at all.
     */
    static int electionsWinners(int[] votes, int k) {
        int max = 0;
        int win = 0;
        int a = 0;
        for (int i = 0; i < votes.length; i++) {
            if (votes[i] > max) {
                max = votes[i];
            }
        }
        for (int i = 0; i < votes.length; i++) {
            if (votes[i] == max) {
                a++;
            }
        }
        if (k == 0 && a == 1) {
            return 1;
        }
        for (int i = 0; i < votes.length; i++) {
            if (votes[i] + k > max) {
                win++;
            }
        }

        return win;
    }

    /**
     * A media access control address (MAC address) is a unique identifier assigned to network interfaces for communications
     * on the physical network segment.
     * The standard (IEEE 802) format for printing MAC-48 addresses
     * in human-friendly form is six groups of two hexadecimal digits (0 to 9 or A to F), separated by hyphens (e.g. 01-23-45-67-89-AB).
     * Your task is to check by given string inputString whether it corresponds to MAC-48 address or not.
     */
    boolean isMAC48Address(String inputString) {
        if (inputString.length() != 17) {
            return false;
        }
        for (int i = 0; i < inputString.length(); i++) {
            if (i % 3 == 2) {
                if (inputString.charAt(i) != '-') {
                    return false;
                }
            } else {
                char sym = inputString.charAt(i);
                if (!('0' <= sym && sym <= '9' || 'A' <= sym && sym <= 'F')) {
                    return false;
                }
            }
        }

        return true;
    }

}

