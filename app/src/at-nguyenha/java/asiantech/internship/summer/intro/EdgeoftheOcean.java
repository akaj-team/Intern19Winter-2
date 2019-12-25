package asiantech.internship.summer.intro;

public class EdgeoftheOcean {

    public static void main(String[] args) {

    }

    int adjacentElementsProduct(int[] inputArray) {

        /**
         * Given an array of integers, find the pair of adjacent
         * elements that has the largest product and return that product.
         */

        int size = inputArray.length;
        int sum = 0;
        int max = inputArray[0]*inputArray[1];

        for(int i=1;i<size-1;i++){
            sum =(inputArray[i]*inputArray[i+1]);
            if(sum > max ){
                max = sum;
            }
        }
        return max;

    }

    int shapeArea(int n) {

        /**
         * Below we will define an n-interesting polygon.
         * Your task is to find the area of a polygon for a given n.
         */

        return (n*n) + (n-1)*(n-1);
    }

    int makeArrayConsecutive2(int[] statues) {

        /**
         * atiorg got statues of different sizes as
         * a present from CodeMaster for his birthday,
         * each statue having an non-negative integer size.
         * Since he likes to make things perfect, he wants to
         * arrange them from smallest to largest so that each
         * statue will be bigger than the previous one exactly by 1.
         * He may need some additional statues to be able to accomplish that.
         * Help him figure out the minimum number of additional statues needed.
         */

        int size = statues.length;
        int count = 0;
        int sort = statues[0];
        for(int i = 0 ;  i < size - 1;i++){
            for(int j = i+1 ; j < size ; j++){
                if(statues[i]>statues[j]){
                    sort = statues[j];
                    statues[j] = statues[i];
                    statues[i] = sort;
                }

            }

        }
        for(int i = 0 ;  i< size - 1;i++){
            if(statues[i+1] -statues[i] > 1){
                count += (statues[i+1] - statues[i] -1);
            }
        }
        return count;
    }

    boolean almostIncreasingSequence(int[] sequence) {

        /**
         * Given a sequence of integers as an array,
         * determine whether it is possible to obtain a
         * strictly increasing sequence by removing no more
         * than one element from the array.
         */

        int size = sequence.length;
        int check = 0;
        int check2 = 0;
        if(size ==2 ) {
            return true;
        }
        for(int i = 0; i < size - 1 ; i++){
            if (sequence[i] >= sequence[i+1]){
                check += 1;
            }
        }
        for(int i = 0; i < size - 2; i++){
            if (sequence[i] >= sequence[i+2]){
                check2 += 1;
            }
        }
        if(check > 1){
            return false;
        }
        else if(check2 > 1){
            return false;
        }
        else return true;
    }


    int matrixElementsSum(int[][] matrix) {

        /**
         * Given matrix, a rectangular matrix of integers,
         * where each value represents the cost of the room,
         * your task is to return the total sum of all rooms
         * that are suitable for the CodeBots (ie: add up all
         * the values that don't appear below a 0).
         */

        int sum=0;
        for(int i=0;i<matrix.length;i++){
            for(int j=0; j<matrix[0].length; j++){
                if(matrix[i][j]==0){
                    for(int k=i; k<matrix.length; k++){
                        matrix[k][j]=0;
                    }
                    continue;
                }
                else
                    sum += matrix[i][j];
            }
        }
        return sum;

    }

}
