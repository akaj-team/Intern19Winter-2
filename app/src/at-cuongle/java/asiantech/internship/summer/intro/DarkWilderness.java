package asiantech.internship.summer.intro;

public class DarkWilderness {
    public static void main(String[] args) {

    }

    /**
     * Caring for a plant can be hard work, but since you tend to it regularly, you
     * have a plant that grows consistently. Each day, its height increases by a fixed
     * amount represented by the integer upSpeed. But due to lack of sunlight, the plant
     * decreases in height every night, by an amount represented by downSpeed.
     * <p>
     * Since you grew the plant from a seed, it started at height 0 initially. Given
     * an integer desiredHeight, your task is to find how many days it'll take for the
     * plant to reach this height.
     */
    public static int growingPlant(int upSpeed, int downSpeed, int desiredHeight) {
        int cout = 0;
        int value = 0;
        do {
            value = value + upSpeed - downSpeed;
            cout++;
        } while (value < desiredHeight);
        return (cout - 1);
    }

    /**
     * You found two items in a treasure chest! The first item weighs weight1 and is worth
     * value1, and the second item weighs weight2 and is worth value2. What is the total
     * maximum value of the items you can take with you, assuming that your max weight capacity
     * is maxW and you can't come back for the items later?
     * <p>
     * Note that there are only two items and you can't bring more than one item of each
     * type, i.e. you can't take two first items or two second items.
     */
    public static int knapsackLight(int value1, int weight1, int value2, int weight2, int maxW) {
        if (weight1 > maxW && weight2 > maxW) return 0;
        if ((weight1 + weight2) <= maxW) return (value1 + value2);
        if (weight1 > maxW) return value2;
        if (weight2 > maxW) return value1;
        return Math.max(value1, value2);
    }

    /**
     * Given a string, output its longest prefix which contains only digits.
     */
    public static String longestDigitsPrefix(String inputString) {
        String result = "";
        for (int i = 0; i < inputString.length(); i++) {
            String check = Character.toString(inputString.charAt(i));
            if (check.matches("[0-9]")) {
                result += check;
            } else {
                break;
            }
        }
        return result;
    }

    /**
     * Let's define digit degree of some positive integer as the number of times
     * we need to replace this number with the sum of its digits until we get to a one
     * digit number.
     * <p>
     * Given an integer, find its digit degree.
     */
    public static int digitDegree(int n) {
        String str = Integer.toString(n);
        int sum = 0, count = 0;
        while (str.length() > 1) {
            sum = 0;
            for (int i = 0; i < str.length(); i++) {
                sum += (str.charAt(i) - '0');
            }
            str = sum + "";
            count++;
        }
        return count;
    }

    /**
     * Given the positions of a white bishop and a black pawn on the standard chess
     * board, determine whether the bishop can capture the pawn in one move.
     * The bishop has no restrictions in distance for each move, but is limited to
     * diagonal movement. Check out the example below to see how it can move:
     */
    public static boolean bishopAndPawn(String bishop, String pawn) {
        return Math.abs(bishop.charAt(0) - pawn.charAt(0)) == Math.abs(pawn.charAt(1) - bishop.charAt(1));
    }
}
