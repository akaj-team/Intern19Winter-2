package asiantech.internship.summer.intro;

public class EdgeoftheOcean {
    /**
     * Given an array of integers,
     * find the pair of adjacent elements that has the largest
     * product and return that product.
     */
    public static int adjacentElementsProduct(int[] inputArray) {
        int x = 0;
        int y = 0;
        int p = inputArray[0] * inputArray[1];
        for (int i = 1; i < inputArray.length - 1; i++) {
            x = inputArray[i];
            y = inputArray[i + 1];
            if (x * y > p) {
                p = x * y;
            }
        }
        return p;
    }

    /**
     * Below we will define an n-interesting polygon. Your task is to find the area of a polygon for a given n.
     * A 1-interesting polygon is just a square with a side of length 1.
     * An n-interesting polygon is obtained by taking the n - 1-interesting polygon and appending 1-interesting polygons to its rim,
     * side by side. You can see the 1-, 2-, 3- and 4-interesting polygons in the picture below.
     */
    public static int shapeArea(int n) {
        if (n == 0) {
            return 0;
        }
        if (n == 1) {
            return 1;
        }
        return (n * n) + (n - 1) * (n - 1);
    }

    /**
     * Ratiorg got statues of different sizes as a present from CodeMaster for his birthday,
     * each statue having an non-negative integer size. Since he likes to make things perfect,
     * he wants to arrange them from smallest to largest so that each statue will be bigger than the previous one exactly by 1.
     * He may need some additional statues to be able to accomplish that.
     * Help him figure out the minimum number of additional statues needed.
     */
    public static int makeArrayConsecutive2(int[] arr) {
        int temp = arr[0];
        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[i] > arr[j]) {
                    temp = arr[j];
                    arr[j] = arr[i];
                    arr[i] = temp;
                }
            }
        }
        int dem = 0;
        for (int i = 0; i < arr.length - 1; i++) {
            if (arr[i + 1] - arr[i] > 1) {
                dem = dem + (arr[i + 1] - arr[i] - 1);
            }
        }
        return dem;
    }

    /**
     * Given a sequence of integers as an array,
     * determine whether it is possible to obtain a strictly increasing sequence
     * by removing no more than one element from the array.
     */
    public static boolean almostIncreasingSequence(int[] sequence) {
        int check = 0;
        int check2 = 0;
        if (sequence.length == 2) {
            return true;
        }
        for (int i = 0; i < sequence.length - 1; i++) {
            if (sequence[i] >= sequence[i + 1]) {
                check = check + 1;
            }
        }
        for (int i = 0; i < sequence.length - 2; i++) {
            if (sequence[i] >= sequence[i + 2]) {
                check2 += 1;
            }
            if (check > 1) {
                return false;
            } else if (check2 > 1) {
                return false;
            }
        }
        return true;
    }


    /**
     * Given matrix, a rectangular matrix of integers,
     * where each value represents the cost of the room,
     * your task is to return the total sum of all rooms that are suitable for the CodeBots
     * (ie: add up all the values that don't appear below a 0).
     */
    public static int matrixElementsSum(int[][] matrix) {
        int count = 0;
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                if (matrix[i][j] == 0) {
                    for (int k = i; k < matrix.length; k++) {
                        matrix[k][j] = 0;
                    }
                    continue;
                } else {
                    count += matrix[i][j];
                }
            }
        }
        return count;
    }

    public static void main(String[] args) {

    }
}
