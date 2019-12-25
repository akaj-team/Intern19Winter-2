package asiantech.internship.summer.Intro;

import java.util.Arrays;

public class EdgeOfTheOcean {
    static int adjacentElementsProduct(int[] inputArray) {
        int product;
        product=inputArray[0]*inputArray[1];
        int tich=0;
        int a=0;
        int b=0;
        for(int i=0;i<inputArray.length-1;i++){
            a=inputArray[i];
            b=inputArray[i+1];
            tich = a*b;
            if(tich>product){
                product=tich;
            }
        }
        return product;
    }
    static  int ShapeArea(int n) {
        int area=0;
        area=(n*n)+((n-1)*(n-1));
        return area;
    }
    int makeArrayConsecutive2(int[] statues) {

        int min=statues[0];
        int max=statues[0];
        int need;
        for(int i=0;i<statues.length;i++){
            if(statues[i]<min){
                min=statues[i];
            }
        }
        for(int i=0;i<statues.length;i++){
            if(statues[i]>min){
                max=statues[i];
            }
        }
        need=(max-min)-(statues.length)+1;
        return need;
    }
     static boolean almostIncreasingSequence(int[] sequence) {

        int a = 0;
        int b = 0;
        if(sequence.length ==2 ) {
            return true;
        }
        for(int i = 0; i < sequence.length - 1 ; i++){
            if (sequence[i] >= sequence[i+1]){
                a += 1;
            }
        }
        for(int i = 0; i < sequence.length - 2; i++){
            if (sequence[i] >= sequence[i+2]){
                b += 1;
            }
        }
        if(a > 1){
            return false;
        }
        else if(b > 1){
            return false;
        }
        else return true;
    }


    public static void main(String[] args) {

    }
}
