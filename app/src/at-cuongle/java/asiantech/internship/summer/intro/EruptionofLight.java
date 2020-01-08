package asiantech.internship.summer.intro;

public class EruptionofLight {
    public static void main(String[] args) {

    }

    /**
     * A string is said to be beautiful if each letter in the string appears at
     * most as many times as the previous letter in the alphabet within the string; ie: b
     * occurs no more times than a; c occurs no more times than b; etc.
     * Given a string, check whether it is beautiful.
     */
    public static boolean isBeautifulString(String inputString) {
        int[] check = new int[26];
        for (int i : inputString.getBytes()) {
            check[i - 97]++;
        }
        for (int i = 1; i < check.length; i++) {
            if (check[i] > check[i - 1]) {
                return false;
            }
        }
        return true;
    }

    /**
     * An email address such as "John.Smith@example.com" is made up of a local
     * part ("John.Smith"), an "@" symbol, then a domain part ("example.com").
     * The domain name part of an email address may only consist of letters, digits,
     * hyphens and dots. The local part, however, also allows a lot of different special
     * characters. Here you can look at several examples of correct and incorrect email addresses.
     * Given a valid email address, find its domain part.
     */
    public static String findEmailDomain(String address) {

        StringBuilder result = new StringBuilder();
        for (int i = address.length() - 1; i > 0; i--) {
            if (address.charAt(i) == 64) {
                for (int j = i + 1; j < address.length(); j++) {
                    String check = Character.toString(address.charAt(j));
                    result.append(check);
                }
                break;
            }
        }
        return result.toString();
    }

    /**
     * Given a string, find the shortest possible string which can be achieved by adding
     * characters to the end of initial string to make it a palindrome.
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

    private boolean isPalindrome(String str) {
        return str.equals(new StringBuilder(str).reverse().toString());
    }

    /**
     * Elections are in progress!
     * Given an array of the numbers of votes given to each of the candidates so far,
     * and an integer k equal to the number of voters who haven't cast their vote yet,
     * find the number of candidates who still have a chance to win the election.
     * The winner of the election must secure strictly more votes than any other candidate.
     * If two or more candidates receive the same (maximum) number of votes, assume there is
     * no winner at all.
     */
    public static int electionsWinners(int[] votes, int k) {
        int cout = 0;
        int check = 0;
        int max = 0;
        for (int vote : votes) {
            max = Math.max(max, vote);
        }
        for (int i = 0; i < votes.length; i++) {
            check = 0;
            max = votes[i] + k;
            for (int j = 0; j < votes.length; j++) {
                if (max <= votes[j]) {
                    if (i == j) {
                        continue;
                    }
                    max = votes[j];
                    check++;
                }
            }
            if (max == votes[i] + k && check == 0) {
                cout++;
            }
        }
        return cout;
    }

    /**
     * A media access control address (MAC address) is a unique identifier assigned
     * to network interfaces for communications on the physical network segment.
     * The standard (IEEE 802) format for printing MAC-48 addresses in human-friendly form
     * is six groups of two hexadecimal digits (0 to 9 or A to F), separated by hyphens
     * (e.g. 01-23-45-67-89-AB).
     * Your task is to check by given string inputString whether it corresponds to MAC-48
     * address or not.
     */
    public static boolean isMAC48Address(String inputString) {

        String[] check = inputString.split("-");
        int count = 0;
        if (check.length != 6) {
            return false;
        }
        if (!inputString.matches(".+[0-9A-F]$")) {
            return false;
        }
        for (String s : check) {
            if (s.matches("[0-9A-F][0-9A-F]")) {
                count++;
            }
        }
        return count == check.length;
    }
}
