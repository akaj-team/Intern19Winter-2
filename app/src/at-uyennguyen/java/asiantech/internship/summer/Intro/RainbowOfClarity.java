package asiantech.internship.summer.Intro;

public class RainbowOfClarity {
    static boolean isDigit(char symbol) {
        if(Character.isDigit(symbol)){
            return true;
        }
        return false;
    }
    static String lineEncoding(String s) {
        String a="";
        int dem=1;
        for(int i=0;i<s.length()-1;i++){
            if(s.charAt(i)==s.charAt(i+1)){
                dem++;
            }
            else{
                if(dem>1){
                    a=a+dem+"";
                    a=a+s.charAt(i);
                    dem=1;
                }
                else{
                    a=a+s.charAt(i);
                }
            }
            if(i==s.length()-2){
                if(dem>1){
                    a=a+dem+"";
                    a=a+s.charAt(i);
                    dem=1;
                }
                else{
                    a=a+s.charAt(s.length()-1);
                }
            }
        }
        return a;
    }
    static int chessKnight(String cell) {
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

    static int deleteDigit(int n) {
        String number= String.valueOf(n);
        int max=0;
        for(int i=0;i<number.length();i++){
            StringBuilder sb = new StringBuilder(number);
            sb.deleteCharAt(i);
            if(Integer.parseInt(sb.toString())>max){
                max=Integer.parseInt(sb.toString());
            }
        }
        return max;
    }

}
