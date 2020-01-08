package asiantech.internship.summer.intro;

public class RainbowofClarity {
    public static void main(String[] args) {

    }

    /**
     * Determine if the given character is a digit or not.
     */
    public static boolean isDigit(char symbol) {
        return 48 <= symbol && symbol <= 57;
    }

    /**
     * Given a string, return its encoding defined as follows:
     * First, the string is divided into the least possible number
     * of disjoint substrings consisting of identical characters
     * for example, "aabbbc" is divided into ["aa", "bbb", "c"]
     * Next, each substring with length greater than one is replaced with
     * a concatenation of its length and the repeating character
     * for example, substring "bbb" is replaced by "3b"
     * Finally, all the new strings are concatenated together in the same
     * order and a new string is returned.
     */
    public static String lineEncoding(String s) {
        int count = 1;
        char[] str = new char[s.length()];
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            str[i] = s.charAt(i);
        }
        for (int i = 0; i < s.length() - 1; i++) {
            if (str[i] == str[i + 1]) {
                count++;
            } else {
                if (count > 1) {
                    result.append("").append(count).append(str[i]);
                    count = 1;
                } else {
                    result.append("").append(str[i]);
                }
            }
            if (i == s.length() - 2) {
                if (str[i] == str[i + 1]) {
                    if (count > 1) {
                        result.append("").append(count).append(str[i + 1]);
                        count = 1;
                    } else {
                        result.append("").append(str[i + 1]);
                    }
                } else {
                    if (count > 1) {
                        result.append("").append(count).append(str[i + 1]);
                        count = 1;
                    } else {
                        result.append("").append(str[i + 1]);
                    }
                }
            }
        }
        return result.toString();
    }

    /**
     * Given a position of a knight on the standard chessboard, find the number of
     * different moves the knight can perform.
     * <p>
     * The knight can move to a square that is two squares horizontally and one square
     * vertically, or two squares vertically and one square horizontally away from it.
     * The complete move therefore looks like the letter L. Check out the image below to
     * see all valid moves for a knight piece that is placed on one of the central squares.
     */
    public static int chessKnight(String cell) {
        int moves = 8;
        if (cell.charAt(0) == 'b' || cell.charAt(0) == 'g') {
            moves -= 2;
        }
        if (cell.charAt(1) == '2' || cell.charAt(1) == '7') {
            moves -= 2;
        }
        if (cell.charAt(0) == 'a' || cell.charAt(0) == 'h') {
            moves /= 2;
        }
        if (cell.charAt(1) == '1' || cell.charAt(1) == '8') {
            moves /= 2;
        }
        return moves;
    }

    /**
     * Given some integer, find the maximal number you can obtain by deleting
     * exactly one digit of the given number.
     */
    public static int deleteDigit(int n) {
        String s = "" + n;
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < s.length(); i++) {
            StringBuilder sb = new StringBuilder(s);
            sb.deleteCharAt(i);
            max = Math.max(max, Integer.parseInt(sb.toString()));
        }
        return max;
    }
}
