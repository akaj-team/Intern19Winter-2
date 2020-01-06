package asiantech.internship.summer.Intro;

import java.util.HashSet;
import java.util.Set;

public class LandOfLogic {
    static String longestWord(String text) {
        String result = text.replaceAll("[^a-zA-Z0-9 ]", " ");
        String[] n = result.split(" ");
        int maxLength=0;
        String a="";
        for(int i=0;i<n.length;i++){
            if(n[i].length()>maxLength){
                maxLength=n[i].length();
                a=n[i];
            }
        }
        return a;
    }
    static boolean validTime(String time) {
        String[] a= time.split(":");
        if(a.length>2){
            return false;
        }
        if(Integer.parseInt(a[0])>=24||Integer.parseInt(a[1])>=60){
            return false;
        }
        return true;
    }
    static int sumUpNumbers(String inputString) {
        String result = inputString.replaceAll("[^0-9 ]", " ");
        String[] a= result.split(" ");
        int sum=0;
        for(int i=0;i<a.length;i++){
            if(a[i].isEmpty()){
                continue;
            }
            else{
                sum=sum+Integer.parseInt(a[i]);
            }
        }
        return sum;
    }

    static int differentSquares(int[][] matrix) {
        Set<String> set = new HashSet<>();
        int m = matrix.length;
        int n = matrix[0].length;
        if (m == 1 || n == 1) {
            return 0;
        }
        for (int i=0; i < m-1; ++i) {
            for (int j=0; j < n-1; ++j) {
                StringBuilder sb = new StringBuilder();
                sb.append(matrix[i][j]).append("\t").append(matrix[i][j+1]).append("\t");
                sb.append(matrix[i+1][j]).append("\t").append(matrix[i+1][j+1]);
                set.add(sb.toString());
            }
        }
        return set.size();
    }

    static int digitsProduct(int product) {
        if(product == 0){
            return 10;
        }
        if(product == 1){
            return 1;
        }
        int a=0;
        String b="";
        int count=0;

        for(int i = 9; i > 1; i--) {
            while(product % i == 0) {
                product=product/i;
                b = i+b.toString();
            }
        }
        if(product>1){
            return -1;
        }
        return Integer.parseInt(b);
    }

    static String messageFromBinaryCode(String code) {
        int bin, div = code.length() / 8, test = 8, test2 = 0;
        StringBuilder s = new StringBuilder();
        while(div-- > 0){
            bin = Integer.valueOf(code.substring(test2,test), 2);
            s.append(Character.toChars(bin));
            test += 8;
            test2 += 8;
        }
        return s.toString();
    }

    int[][] spiralNumbers(int n) {
        int value = 1;
        int[][] result = new int[n][n];
        int minColumn = 0;
        int maxColumn = n-1;
        int minRow = 0;
        int maxRow = n-1;
        while (value <= n*n){
            for (int i = minColumn; i <= maxColumn; i++){
                result[minRow][i] = value;
                value++;
            }
            for (int i = minRow+1; i <= maxRow; i++)
            {
                result[i][maxColumn] = value;
                value++;
            }
            for (int i = maxColumn-1; i >= minColumn; i--)
            {
                result[maxRow][i] = value;
                value++;
            }
            for (int i = maxRow-1; i >= minRow+1; i--)
            {
                result[i][minColumn] = value;
                value++;
            }
            minColumn++;
            minRow++;
            maxColumn--;
            maxRow--;
        }
        return result;
    }


}
