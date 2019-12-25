package asiantech.internship.summer.intro;

public class EdgeoftheOcean {
    public static void main(String[] args) {

    }

    public static int adjacentElementsProduct(int[] inputArray) {
        /**
         * Given an array of integers, find the pair of adjacent elements that
         * has the largest product and return that product.
         */
        int sum;
        int max = inputArray[0] * inputArray[1];
        for (int i = 1; i < inputArray.length - 1; i++) {
            sum = (inputArray[i] * inputArray[i + 1]);
            if (sum > max) {
                max = sum;
            }
        }
        return max;
    }

    public static int shapeArea(int n) {
        int area = 1;
        while (n > 1) {
            area += (n-- - 1) * 4;
        }
        return area;
    }

    public static int makeArrayConsecutive2(int[] statues) {
        /**
         * Ratiorg got statues of different sizes as a present from CodeMaster for his birthday,
         * each statue having an non-negative integer size. Since he likes to make things perfect,
         * he wants to arrange them from smallest to largest so that each statue will be bigger
         * than the previous one exactly by 1. He may need some additional statues to be able to
         * accomplish that. Help him figure out the minimum number of additional statues needed.
         * @return
         */
        int minNumber = statues[0];
        int maxNumber = statues[0];
        int addNumber;
        for (int statue : statues) {
            if (maxNumber < statue) {
                maxNumber = statue;
            }
            if (minNumber > statue) {
                minNumber = statue;
            }
        }
        addNumber = (maxNumber - minNumber) + 1;
        if (addNumber != statues.length) {
            addNumber = addNumber - statues.length;
        } else {
            addNumber = 0;
        }
        return addNumber;
    }

    public static boolean almostIncreasingSequence(int[] sequence) {
        /**
         * Given a sequence of integers as an array, determine whether it is possible to
         * obtain a strictly increasing sequence by removing no more than one element
         * from the array.
         */
        int checkFirst = 0;
        int checkSecond = 0;
        for (int i = 0; i < sequence.length - 1; i++) {
            if (sequence[i] >= sequence[i + 1]) {
                checkFirst = checkFirst + 1;
            }
        }
        for (int i = 0; i < sequence.length - 2; i++) {
            if (sequence[i] >= sequence[i + 2]) {
                checkSecond = checkSecond + 1;
            }
        }
        if (checkFirst > 1) {
            return false;
        } else return checkSecond <= 1;
    }

    public static int matrixElementsSum(int[][] matrix) {
        /**
         * Given matrix, a rectangular matrix of integers, where each value
         * represents the cost of the room, your task is to return the total
         * sum of all rooms that are suitable for the CodeBots (ie: add up all the
         * values that don't appear below a 0).
         */
        int result = 0;
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                if (matrix[i][j] == 0) {
                    for (int k = i; k < matrix.length; k++) {
                        matrix[k][j] = 0;
                    }
                }
                result = result + matrix[i][j];
            }
        }
        return result;
    }

}
