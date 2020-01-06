package asiantech.internship.summer.Intro;

public class DarkWilderness {
    static int growingPlant(int upSpeed, int downSpeed, int desiredHeight) {
        int day=1;
        int night = upSpeed-downSpeed;
        while(upSpeed<desiredHeight){
            upSpeed=upSpeed+night;
            day++;
        }
        return day;
    }
    static int knapsackLight(int value1, int weight1, int value2, int weight2, int maxW) {
        if(weight1+weight2<=maxW){
            return value1+value2;
        }
        if(weight1+weight2>maxW &&weight1<=maxW&&weight2<=maxW&&value1>value2){
            return value1;
        }
        if(weight1+weight2>maxW &&weight1<=maxW&&weight2<=maxW&&value1<value2){
            return value2;
        }
        if(weight1+weight2>maxW &&weight1<=maxW&&weight2<=maxW&&value1==value2){
            return value2;
        }
        if(weight1+weight2>maxW&&weight1<=maxW&&weight2>maxW){
            return value1;
        }
        if(weight1+weight2>maxW&&weight2<=maxW&&weight1>maxW){
            return value2;
        }
        if(weight1+weight2>maxW&&weight2<=maxW&&weight1<=maxW){
            return value2;
        }
        return 0;
    }
    static String longestDigitsPrefix(String inputString) {
        String a="";
        for(int i=0;i<inputString.length();i++){
            if(Character.isDigit(inputString.charAt(i))){
                a+=inputString.charAt(i);
            }
            else break;
        }

        return a;
    }
    static int digitDegree(int n) {
        String a =Integer.toString(n);
        int sum=0;
        int lan=0;
        while(a.length()>1){
            for(int i=0;i<a.length();i++){
                sum=sum+Character.getNumericValue(a.charAt(i));
            }
            a=Integer.toString(sum);
            lan++;
            sum=0;
        }
        return lan;

    }
    static boolean bishopAndPawn(String bishop, String pawn) {
        char[] ngang = new char[] {'a','b','c','d','e','f','g','h'};
        char[] doc = new char[] {'1','2','3','4','5','6','7','8'};
        char[] a = bishop.toCharArray();
        char[] b = pawn.toCharArray();
        int ngang1=0;
        int ngang2=0;
        int doc1=0;
        int doc2=0;
        for(int i=0;i<ngang.length;i++){
            if(ngang[i]==bishop.charAt(0)){
                ngang1=i+1;
            }
        }
        for(int i=0;i<ngang.length;i++){
            if(ngang[i]==pawn.charAt(0)){
                ngang2=i+1;
            }
        }
        for(int i=0;i<doc.length;i++){
            if(doc[i]==bishop.charAt(1)){
                doc1=i+1;
            }
        }
        for(int i=0;i<doc.length;i++){
            if(doc[i]==pawn.charAt(1)){
                doc2=i+1;
            }
        }
        int x=Math.abs(ngang1-ngang2);
        int y=Math.abs(doc1-doc2);
        if(x==y){
            return true;
        }
        return false;
    }

}
