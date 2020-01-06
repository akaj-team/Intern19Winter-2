package asiantech.internship.summer.Intro;

public class RainsOfReason {
    static int[] arrayReplace(int[] inputArray, int elemToReplace, int substitutionElem) {
        for(int i=0;i<inputArray.length;i++){
            if(inputArray[i]==elemToReplace){
                inputArray[i]=substitutionElem;
            }
        }
        return inputArray;
    }
    static boolean evenDigitsOnly(int n) {
        String number= String.valueOf(n);
        char[] a = number.toCharArray();
        for(int i=0;i<a.length;i++){
            if(Character.getNumericValue(a[i])%2!=0){
                return false;
            }
        }
        return true;
    }

    static boolean variableName(String name) {
        if(name.matches("[a-zA-Z_][0-9a-zA-Z_]*$")){
             return true;
        }
             return false;
     }

    static String alphabeticShift(String inputString) {
        StringBuilder alpha = new StringBuilder();
        for(int i =0; i < inputString.length(); i++){
            if(inputString.charAt(i) == 'z'){
                alpha.append('a');
            } else{
                alpha.append((char) (inputString.charAt(i) + 1));
            }
        }
        return alpha.toString();
    }
    static boolean chessBoardCellColor(String cell1, String cell2) {
        char[] ngang = new char[] {'A','B','C','D','E','F','G','H'};
        char[] doc = new char[] {'1','2','3','4','5','6','7','8'};
        char[] a = cell1.toCharArray();
        char[] b = cell2.toCharArray();
        int ce1ngang=0;
        int ce2ngang=0;
        int ce1doc=0;
        int ce2doc=0;
        for(int i=0;i<ngang.length;i++){
            if(ngang[i]==cell1.charAt(0)){
                ce1ngang=i;
            }
        }
        for(int i=0;i<ngang.length;i++){
            if(ngang[i]==cell2.charAt(0)){
                ce2ngang=i;
            }
        }
        for(int i=0;i<doc.length;i++){
            if(doc[i]==cell1.charAt(1)){
                ce1doc=i;
            }
        }
        for(int i=0;i<doc.length;i++){
            if(doc[i]==cell2.charAt(1)){
                ce2doc=i;
            }
        }
        if(((ce1ngang+ce1doc)+(ce2ngang+ce2doc))%2==0){
            return true;
        }
        return false;
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

}
