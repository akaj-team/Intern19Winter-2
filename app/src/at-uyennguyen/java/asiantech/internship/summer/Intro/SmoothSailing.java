package asiantech.internship.summer.Intro;
import java.util.ArrayList;
public class SmoothSailing {
    static String[] allLongestStrings(String[] inputArray) {
        ArrayList<String> list = new ArrayList<String>();
        int l=0;
        for(int i=0;i<inputArray.length;i++){
            if(inputArray[i].length()>=l){
                l=inputArray[i].length();
            }
        }
        for(int i=0;i<inputArray.length;i++){
            if(inputArray[i].length()==l){
                list.add(inputArray[i]);
            }
        }
        return list.toArray(new String[0]);

    }
     static int commonCharacterCount(String s1, String s2) {
        boolean[] check=new boolean[s2.length()];
        int dem=0;
        for(int i=0;i<s1.length();i++){
            for(int j=0;j<s2.length();j++){
                if(s1.charAt(i)==s2.charAt(j)&&check[j]==false){
                    check[j]=true;
                    dem++;
                    break;
                }
            }
        }
        return dem;
    }
    static boolean isLucky(int n) {
        int sum1=0;
        int sum2=0;
        String lucky= String.valueOf(n);
        char[] a = lucky.toCharArray();
        for(int i=0;i<a.length/2;i++){
            sum1=sum1+Character.getNumericValue(a[i]) ;
        }
        for(int j=a.length-1;j>=a.length/2;j--){
            sum2=sum2+Character.getNumericValue(a[j]);
        }
        if(sum1==sum2){
            return true;
        }
        return false;
    }

    static int[] sortByHeight(int[] a) {

        for(int i=0;i<a.length;i++){
            if(a[i]==-1){
                continue;
            }
            else{
                for(int j=0;j<a.length;j++){
                    if(a[j]==-1){
                        continue;
                    }
                    else{
                        if(a[i]<a[j]){
                            int phu=a[i];
                            a[i]=a[j];
                            a[j]=phu;
                        }
                    }
                }
            }
        }
        return a;
    }

    static String reverseInParentheses(String inputString) {
        String tmpCh = new String("");
        String tmpChRe = new String("");
        String tmp = new String("");
        int l = inputString.length();
        int n = 0;
        int j =0;
        for(int i = 0;i<l;i++){
            if(inputString.charAt(i)=='(')
                n++;
        }
        int T[] = new int[n];
        for(int i=0;i<l;i++){
            if(inputString.charAt(i)=='('){
                T[j]=i;
                j++;
            }
        }
        j=0;
        while(n>0){
            j = T[n-1] + 1;
            while(inputString.charAt(j)!=')'){
                tmpCh = tmpCh + inputString.charAt(j);
                j++;
            }
            for(int q=tmpCh.length()-1;q>=0;q--)
                tmpChRe = tmpChRe + tmpCh.charAt(q);
            tmp = inputString.substring(0,T[n-1]) + tmpChRe + inputString.substring(T[n-1]+tmpChRe.length()+2);
            inputString = tmp;
            n--;
            tmp = "";
            tmpCh = "";
            tmpChRe = "";
        }
        return inputString ;
    }


}
