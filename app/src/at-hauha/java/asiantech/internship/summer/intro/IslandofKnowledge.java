package asiantech.internship.summer.intro;

public class IslandofKnowledge {
    /**
     * Call two arms equally strong if the heaviest weights they each are able to lift are equal.
     */
    public static boolean areEquallyStrong(int yourLeft, int yourRight, int friendsLeft, int friendsRight) {
        if ((yourLeft + yourRight) == (friendsLeft + friendsRight) && Math.abs(yourLeft - yourRight) == Math.abs(friendsLeft - friendsRight)) {
            return true;
        }
        return false;
    }

    /**
     * Given an array of integers, find the maximal absolute difference between any two of its adjacent elements.
     */

    public static int arrayMaximalAdjacentDifference(int[] inputArray) {
        int max = 0;
        for (int i = 0; i < inputArray.length - 1; i++) {
            if (Math.abs(inputArray[i] - inputArray[i + 1]) > max) {
                max = Math.abs(inputArray[i] - inputArray[i + 1]);
            }
        }
        return max;
    }

    /**
     * Given a string, find out if it satisfies the IPv4 address naming rules.
     */
    public static boolean isIPv4Address(String inputString) {
        String splitparts[] = inputString.split("[.]");
        if (splitparts.length != 4) {
            return false;
        }
        for (String part : splitparts) {
            if (part.isEmpty()) {
                return false;
            }
            if (!part.matches("^[0-9]{1,3}$")) {
                return false;
            }
            if (!(Integer.parseInt(part) >= 0 && Integer.parseInt(part) <= 255)) {
                return false;
            }
        }

        return true;

    }

    /**
     * Find the minimal length of the jump enough to avoid all the obstacles
     */

    public static int avoidObstacles(int[] inputArray) {
        for (int i = 1; ; i++) {
            int count = 0;
            for (int j = 0; j < inputArray.length; j++) {
                if (inputArray[j] % i == 0) {
                    break;
                }
                count++;
                if (count >= inputArray.length) {
                    return i;
                }
            }
        }
    }

    /**
     * Return the blurred image as an integer, with the fractions rounded down.
     */
    public static int[][] boxBlur(int[][] image) {
        int[][] result = new int[image.length - 2][image[0].length - 2];
        for (int i = 1; i < image.length - 1; i++) {
            for (int j = 1; j < image[0].length - 1; j++) {
                int sum = 0;
                for (int x = i - 1; x <= i + 1; x++) {
                    for (int y = j - 1; y <= j + 1; y++) {
                        sum += image[x][y];
                    }
                }
                result[i - 1][j - 1] = sum / 9;
            }
        }
        return result;
    }

    /**
     * In the popular Minesweeper game you have a board with some
     * mines and those cells that don't contain a mine have a number in it that indicates the total
     * number of mines in the neighboring cells.
     * Starting off with some arrangement of mines we want to create a Minesweeper game setup.
     */
    public static int[][] minesweeper(boolean[][] matrix) {
        int[][] result = new int[matrix.length][matrix[0].length];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                int sum = 0;
                for (int x = i - 1; x <= i + 1; x++) {
                    for (int y = j - 1; y <= j + 1; y++) {
                        if (x > -1 && y > -1 && x < matrix.length && y < matrix[0].length) {
                            if (matrix[x][y] == true) {
                                sum += 1;
                            }
                        }
                    }
                }

                if (matrix[i][j] == true) {
                    result[i][j] = sum - 1;
                } else {
                    result[i][j] = sum;
                }
            }
        }
        return result;
    }

    public static void main(String[] args) {

    }
}
