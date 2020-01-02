package asiantech.internship.summer.intro;

public class RainbowofClarity {

    public static void main(String[] args) {

    }

    /**
     * 48
     * Determine if the given character is a digit or not.
     */
    boolean isDigit(char symbol) {
        if(symbol >= '0' && symbol <= '9'){
            return true;
        }
        return false;
    }

    /**
     * 49
     * Given a string, return its encoding defined as follows:
     *
     * First, the string is divided into the least possible number of
     * disjoint substrings consisting of identical characters
     * for example, "aabbbc" is divided into ["aa", "bbb", "c"]
     *
     * Next, each substring with length greater than one
     * is replaced with a concatenation of its length and the repeating character
     * for example, substring "bbb" is replaced by "3b"
     *
     * Finally, all the new strings are concatenated
     * together in the same order and a new string is returned.
     */
    String lineEncoding(String s) {
        int count=1;
        String n="";
        if(s.length()<2){
            return "1"+s;
        }
        for(int i = 0; i < s.length()-1; i++){
            if(s.charAt(i) == s.charAt(i+1)){
                count++;
            }else{
                if(count>1){
                    n += count+ "" + s.charAt(i);
                    count=1;
                }else{
                    n += "" + s.charAt(i);
                }
            }
            if(i==s.length()-2){
                if(count>1){
                    n+= count+ ""+ s.charAt(i+1) ;
                }else{
                    n+= ""+ s.charAt(i+1) ;
                }
            }
        }
        return n;
    }

    /**
     * 50
     * Given a position of a knight on the standard chessboard,
     * find the number of different moves the knight can perform.
     */
    int chessKnight(String cell) {
        int move = 8;
        if(cell.charAt(0) == 'b' || cell.charAt(0) == 'g'){
            move -= 2;
        }
        if(cell.charAt(1) == '2' || cell.charAt(1) == '7'){
            move -= 2;
        }
        if(cell.charAt(0) == 'a' || cell.charAt(0) == 'h'){
            move /= 2;
        }
        if(cell.charAt(1) == '1' || cell.charAt(1) == '8'){
            move /= 2;
        }
        return move;
    }

    /**
     * 51
     * Given some integer, find the maximal number you can
     * obtain by deleting exactly one digit of the given number.
     */
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
