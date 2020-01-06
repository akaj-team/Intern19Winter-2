package asiantech.internship.summer.Intro;
import java.util.Arrays;
public class ExploringTheWaters {
    static int[] alternatingSums(int[] a) {

        int[]sum= new int[2];
        int sum1=0;
        int sum2=0;

        for(int i=0;i<a.length;i+=2){
            sum1=sum1+a[i];
        }
        for(int j=1;j<a.length;j+=2){
            sum2=sum2+a[j];
        }
        sum[0]=sum1;
        sum[1]=sum2;

        return sum;
    }

    static String[] addBorder(String[] picture) {
        String[]a= new String[picture.length+2];
        String b="";

        for(int i=0;i<picture[0].length()+2;i++){
            b=b+"*";
        }
        a[0]=b;
        a[picture.length+1]=b;
        for(int j=0;j<picture.length;j++){
            a[j+1]=("*"+picture[j]+"*");
        }

        return a;
    }
    static boolean areSimilar(int[] a, int[] b) {
        int dem=0;
        int[] samea = Arrays.copyOf(a, a.length);
        int[] sameb = Arrays.copyOf(b, b.length);
        if(a.length!= b.length){
            return false;
        }
        Arrays.sort(a);
        Arrays.sort(b);
        if(Arrays.equals(a,b)!=true){
            return false;
        }
        else{
            for(int i=0;i<samea.length;i++){
                if (samea[i]!=sameb[i]){
                    dem++;
                }
            }
        }
        if(dem==2||dem==0){
            return true;
        }
        return false;
    }
    static int arrayChange(int[] inputArray) {
        int change=0;
        for(int i=0;i<inputArray.length-1;i++){
            if(inputArray[i]<inputArray[i+1]){
                continue;
            }
            else{
                change=change+(inputArray[i]-inputArray[i+1]+1);
                inputArray[i+1]=inputArray[i+1]+(inputArray[i]-inputArray[i+1]+1);
            }
        }
        return change;
    }
    static boolean palindromeRearranging(String inputString) {
        int dem=0;
        int b=0;
        int phu=0;
        for(int i=0;i<inputString.length();i++){
            for(int j=0;j<inputString.length();j++){
                if(inputString.charAt(i)==inputString.charAt(j)){
                    dem++;
                }
            }
            if(dem%2!=0){
                b++;
                phu=dem;
            }
            dem=0;
        }
        if(b==0||b==phu){
            return true;
        }
        return false;
    }

}
