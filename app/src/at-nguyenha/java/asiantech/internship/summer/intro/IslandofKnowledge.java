package asiantech.internship.summer.intro;

public class IslandofKnowledge {

    public static void main(String[] args) {

    }

    /**
     * Call two arms equally strong if the heaviest
     * weights they each are able to lift are equal.
     * <p>
     * Call two people equally strong if their strongest
     * arms are equally strong (the strongest arm can be
     * both the right and the left), and so are their weakest arms.
     */
    static boolean areEquallyStrong(int yourLeft, int yourRight, int friendsLeft, int friendsRight) {
        if ((yourLeft + yourRight) == (friendsLeft + friendsRight)) {
            return yourLeft == friendsLeft || yourLeft == friendsRight;
        }
        return false;
    }

    /**
     * Given an array of integers, find the maximal
     * absolute difference between any two of its adjacent elements.
     */
    static int arrayMaximalAdjacentDifference(int[] inputArray) {
        int size = inputArray.length;
        int max = 0;
        int minus = 0;
        if (inputArray.length > 1) {
            for (int i = 0; i < size - 1; i++) {
                minus = Math.abs(inputArray[i] - inputArray[i + 1]);
                if (minus > max) {
                    max = minus;
                }
            }
        }
        return max;
    }

    /**
     * Given a string, find out if it satisfies the IPv4 address naming rules.
     */
    static boolean isIPv4Address(String inputString) {
        String[] splitparts = inputString.split("[.]");
        if (splitparts.length != 4) {
            return false;
        }
        for (String part : splitparts) {
            if (part.isEmpty()) {
                return false;
            }
            if (!part.matches("[0-9]{1,3}")) {
                return false;
            }
            if (!(Integer.parseInt(part) >= 0 && Integer.parseInt(part) <= 255)) {
                return false;
            }
        }
        return true;
    }

    /**
     * You are given an array of integers representing
     * coordinates of obstacles situated on a line line.
     * <p>
     * Assume that you are jumping from the point with
     * coordinate 0 to the right. You are allowed only
     * to make jumps of the same length represented by some integer.
     * <p>
     * Find the minimal length of the jump enough to
     * avoid all the obstacles.
     */
    static int avoidObstacles(int[] inputArray) {
        boolean found = false;
        int i = 1;
        while (true) {
            found = true;
            for (int value : inputArray) {
                if (value % i == 0) {
                    found = false;
                }
            }
            if (found) {
                return i;
            }
            i++;
        }
    }

    /**
     * The pixels in the input image are represented as integers.
     * The algorithm distorts the input image in the following way:
     * Every pixel x in the output image has a value equal to the
     * average value of the pixel values from the 3 × 3 square that
     * has its center at x, including x itself. All the pixels on the
     * border of x are then removed.
     * <p>
     * Return the blurred image as an integer, with the fractions rounded down.
     */
    static int[][] boxBlur(int[][] image) {
        int[][] result = new int[image.length - 2][image[0].length - 2];
        for (int i = 1; i < image.length - 1; i++) {
            for (int j = 1; j < image[0].length - 1; j++) {
                int sum = 0;
                for (int ii = i - 1; ii <= i + 1; ii++) {
                    for (int jj = j - 1; jj <= j + 1; jj++) {
                        sum += image[ii][jj];
                    }
                }
                result[i - 1][j - 1] = sum / 9;
            }
        }
        return result;
    }

    /**
     * In the popular Minesweeper game you have a board with
     * some mines and those cells that don't contain a mine
     * have a number in it that indicates the total number of
     * mines in the neighboring cells. Starting off with some
     * arrangement of mines we want to create a Minesweeper game setup.
     */
    static int[][] minesweeper(boolean[][] matrix) {
        int[][] sol = new int[matrix.length][matrix[0].length];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                for (int ii = Math.max(0, i - 1); ii <=
                        Math.min(i + 1, matrix.length - 1); ii++) {
                    for (int jj = Math.max(0, j - 1); jj <=
                            Math.min(j + 1, matrix[0].length - 1); jj++) {
                        if (matrix[ii][jj] && (i != ii || jj != j)) {
                            sol[i][j]++;
                        }
                    }
                }
            }
        }
        return sol;
    }


}
