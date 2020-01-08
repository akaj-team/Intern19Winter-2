package asiantech.internship.summer.intro;

import java.util.HashSet;

public class IslandofKnowledge {
    public static void main(String[] args) {

    }

    /**
     * Call two arms equally strong if the heaviest weights they each are able to lift are equal.
     * Call two people equally strong if their strongest arms are equally strong (the strongest
     * arm can be both the right and the left), and so are their weakest arms.
     * Given your and your friend's arms' lifting capabilities find out if you two are equally strong.
     */
    public static boolean areEquallyStrong(int yourLeft, int yourRight, int friendsLeft, int friendsRight) {
        int minMe = yourLeft;
        int maxMe = yourRight;
        int minFriends = friendsLeft;
        int maxFriends = friendsRight;
        if (maxMe < minMe) {
            maxMe = yourLeft;
            minMe = yourRight;
        }
        if (maxFriends < minFriends) {
            maxFriends = friendsLeft;
            minFriends = friendsRight;
        }
        return (maxMe == maxFriends && minMe == minFriends);
    }

    /**
     * Given an array of integers, find the maximal absolute difference between
     * any two of its adjacent elements.
     */
    public static int arrayMaximalAdjacentDifference(int[] inputArray) {
        int max = Math.abs(inputArray[1] - inputArray[0]);
        for (int i = 1; i < inputArray.length - 1; i++) {
            int a = Math.abs(inputArray[i + 1] - inputArray[i]);
            if (a > max) {
                max = a;
            }
        }
        return max;
    }

    /**
     * An IP address is a numerical label assigned to each device (e.g., computer,
     * printer) participating in a computer network that uses the Internet Protocol for communication.
     * There are two versions of the Internet protocol, and thus two versions of addresses.
     * One of them is the IPv4 address.
     * Given a string, find out if it satisfies the IPv4 address naming rules.
     */
    public static boolean isIPv4Address(String inputString) {
        String[] getNumber = inputString.split("\\.");
        System.out.println(getNumber.length);
        if (getNumber.length != 4) {
            return false;
        }
        for (String partNumber : getNumber) {

            if (partNumber.isEmpty()) {
                return false;
            }
            if (!partNumber.matches("[0-9]{1,3}")) {
                return false;
            }
            int toInt = Integer.parseInt(partNumber);
            if (toInt > 255 || toInt < 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * You are given an array of integers representing coordinates of obstacles
     * situated on a straight line.
     * <p>
     * Assume that you are jumping from the point with coordinate 0 to the right.
     * You are allowed only to make jumps of the same length represented by some integer.
     * <p>
     * Find the minimal length of the jump enough to avoid all the obstacles.
     */
    public static int avoidObstacles(int[] inputArray) {
        HashSet<Integer> hs = new HashSet<>();
        int minimalLength = inputArray[0];
        for (int value : inputArray) {
            hs.add(value);
            minimalLength = Math.max(minimalLength, value);
        }
        for (int i = 1; i <= minimalLength; i++) {
            int j;
            for (j = i; j <= minimalLength; j = j + i) {
                if (hs.contains(j))
                    break;
            }
            if (j > minimalLength) {
                return i;
            }
        }
        return minimalLength + 1;
    }

    /**
     * Last night you partied a little too hard. Now there's a black and white
     * photo of you that's about to go viral! You can't let this ruin your reputation,
     * so you want to apply the box blur algorithm to the photo to hide its content.
     * <p>
     * The pixels in the input image are represented as integers. The algorithm distorts
     * the input image in the following way: Every pixel x in the output image has a value
     * equal to the average value of the pixel values from the 3 × 3 square that has its
     * center at x, including x itself. All the pixels on the border of x are then removed.
     * <p>
     * Return the blurred image as an integer, with the fractions rounded down.
     */
    public static int[][] boxBlur(int[][] image) {
        int[][] result = new int[image.length - 2][image[0].length - 2];
        for (int i = 1; i < image.length - 1; i++) {
            for (int j = 1; j < image[0].length - 1; j++) {
                int sumElement = 0;
                for (int ii = i - 1; ii <= i + 1; ii++) {
                    for (int jj = j - 1; jj <= j + 1; jj++) {
                        sumElement += image[ii][jj];
                    }
                }
                result[i - 1][j - 1] = sumElement / 9;
            }
        }
        return result;
    }

    /**
     * In the popular Minesweeper game you have a board with some mines and those cells
     * that don't contain a mine have a number in it that indicates the total number of mines
     * in the neighboring cells. Starting off with some arrangement of mines we want to create
     * a Minesweeper game setup.
     */
    public static int[][] minesweeper(boolean[][] matrix) {
        int[][] result = new int[matrix.length][matrix[0].length];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                int sum = 0;

                for (int x = i - 1; x <= i + 1; x++) {
                    for (int y = j - 1; y <= j + 1; y++) {

                        if (x > -1 && y > -1 && x < matrix.length && y < matrix[0].length) {
                            if (matrix[x][y]) {
                                sum += 1;
                            }
                        }
                    }
                }
                if (matrix[i][j]) {
                    result[i][j] = sum - 1;
                } else {
                    result[i][j] = sum;
                }
            }
        }
        return result;
    }
}
