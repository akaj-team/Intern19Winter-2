package asiantech.internship.summer.intro;

public class RainbowOfClarity {
    /*
    *Determine if the given character is a digit or not.
    * */
    boolean isDigit(char symbol) {
        if(symbol >= 48 && symbol <= 57 ){
            return true;
        }
        return false;
    }
    /*
    * Given a string, return its encoding defined
    * */
    String lineEncoding(String s) {
        int c = 1;
        String encode = "";
        if (s.length() < 2) {
            System.out.println(s);
        }
        for (int i=0; i < s.length() - 1; i++) {
            if (s.charAt(i) == s.charAt(i + 1)) {
                c++;
            } else {
                if (c > 1) {
                    encode += c + "" + s.charAt(i);
                    c = 1;
                } else {
                    encode += "" + s.charAt(i);
                }
            }
            if (i == s.length() - 2) {
                if (c > 1) {
                    encode += c + "" + s.charAt(i + 1);
                } else {
                    encode += "" + s.charAt(i + 1);
                }
            }
        }
        return encode;
    }
    /*
    * Given a position of a knight on the standard chessboard, find the number of different moves the knight can perform.
    * */
    int chessKnight(String cell) {
        int x = cell.charAt(0) % 97, y = cell.charAt(1) - '0' - 1;
        int c = 0;
        for (int dx = -2; dx <= 2; dx++) {
            for (int dy = -2; dy <= 2; dy++) {
                if (Math.abs(dx * dy) == 2) {
                    if (isValid(x + dx, y + dy))
                        c++;
                }
            }
        }
        return c;
    }
    private static boolean isValid(int x, int y) {
        return x >= 0 && x <= 7 && y >= 0 && y <= 7;
    }
    /*
    * Given some integer, find the maximal number you can obtain by deleting exactly one digit of the given number
    * */
    int deleteDigit(int n) {
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
