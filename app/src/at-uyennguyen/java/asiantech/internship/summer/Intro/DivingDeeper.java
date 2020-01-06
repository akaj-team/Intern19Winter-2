package asiantech.internship.summer.Intro;

import java.util.HashSet;
import java.util.Set;

public class DivingDeeper {
    int[] extractEachKth(int[] inputArray, int k) {
        int dai=inputArray.length-(inputArray.length/k);
        int[]array= new int[dai];
        int j=0;
        if(j<dai){
            for(int i=0;i<inputArray.length;i++){
                if((i+1)%k!=0){
                    array[j]=inputArray[i];
                    j++;
                }
            }
        }
        return array;
    }

    static char firstDigit(String inputString) {
        char a='a';
        char[] ch = inputString.toCharArray();
        for(int i=0;i<ch.length;i++){
            if(Character.isDigit(ch[i])){
                a= ch[i];
                break;
            }
        }
        return a;

    }
    static int differentSymbolsNaive(String s) {
        Set<Character> array = new HashSet<>();
        for(int i=0;i<s.length();i++){
            array.add(s.charAt(i));
        }
        return array.size();
    }

    static int arrayMaxConsecutiveSum(int[] inputArray, int k) {
        int max=0;
        int tong=0;
        int dem=1;
        for(int i=0;i<=inputArray.length-k;i++){
            tong=inputArray[i];
            while(dem<k){
                tong= tong+inputArray[i+dem];
                dem++;
            }
            dem=1;
            if(tong>max){
                max=tong;
            }
        }
        return max;
    }

}
