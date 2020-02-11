package asiantech.internship.summer.Intro;

public class EdgeOfTheOcean {
    /**
     * Given an array of integers,
     * find the pair of adjacent elements that has the largest product and return that product.
     */
    static int adjacentElementsProduct(int[] inputArray) {
        int product;
        product = inputArray[0] * inputArray[1];
        int tich = 0;
        int a = 0;
        int b = 0;
        for (int i = 0; i < inputArray.length - 1; i++) {
            a = inputArray[i];
            b = inputArray[i + 1];
            tich = a * b;
            if (tich > product) {
                product = tich;
            }
        }
        return product;
    }

    /**
     * Below we will define an n-interesting polygon.
     * Your task is to find the area of a polygon for a given n.
     */
    static int ShapeArea(int n) {
        int area = 0;
        area = (n * n) + ((n - 1) * (n - 1));
        return area;
    }

    /**
     * Ratiorg got statues of different sizes as a present from CodeMaster for his birthday,
     * each statue having an non-negative integer size. Since he likes to make things perfect,
     * he wants to arrange them from smallest to largest so that each statue will be bigger than the previous one exactly by 1.
     * He may need some additional statues to be able to accomplish that.
     * Help him figure out the minimum number of additional statues needed.
     */
    static int makeArrayConsecutive2(int[] statues) {

        int min = statues[0];
        int max = statues[0];
        int need;
        for (int i = 0; i < statues.length; i++) {
            if (statues[i] < min) {
                min = statues[i];
            }
        }
        for (int i = 0; i < statues.length; i++) {
            if (statues[i] > min) {
                max = statues[i];
            }
        }
        need = (max - min) - (statues.length) + 1;
        return need;
    }

    /**
     * Given a sequence of integers as an array,
     * determine whether it is possible to obtain a strictly increasing sequence by removing no more than one element from the array.
     */
    static boolean almostIncreasingSequence(int[] sequence) {

        int a = 0;
        int b = 0;
        if (sequence.length == 2) {
            return true;
        }
        for (int i = 0; i < sequence.length - 1; i++) {
            if (sequence[i] >= sequence[i + 1]) {
                a += 1;
            }
        }
        for (int i = 0; i < sequence.length - 2; i++) {
            if (sequence[i] >= sequence[i + 2]) {
                b += 1;
            }
        }
        if (a > 1) {
            return false;
        } else if (b > 1) {
            return false;
        } else return true;
    }

    /**
     * Given matrix, a rectangular matrix of integers,
     * where each value represents the cost of the room,
     * your task is to return the total sum of all rooms that are suitable for the CodeBots
     * (ie: add up all the values that don't appear below a 0).
     */
    static int matrixElementsSum(int[][] matrix) {

        int tong = 0;
        for (int a = 0; a < matrix.length; a++) {
            for (int b = 0; b < matrix[0].length; b++) {
                if (matrix[a][b] == 0) {
                    for (int c = a; c < matrix.length; c++) {
                        matrix[c][b] = 0;
                    }

                }
                tong = tong + matrix[a][b];
            }
        }
        return tong;
    }


    public static void main(String[] args) {

    }
}
