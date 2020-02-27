package asiantech.internship.summer.Intro;

public class DarkWilderness {
    /**
     * Each day, its height increases by a fixed amount represented by the integer upSpeed.
     * But due to lack of sunlight, the plant decreases in height every night, by an amount represented by downSpeed.
     * Since you grew the plant from a seed, it started at height 0 initially.
     * Given an integer desiredHeight, your task is to find how many days it'll take for the plant to reach this height.
     */
    static int growingPlant(int upSpeed, int downSpeed, int desiredHeight) {
        int day = 1;
        int night = upSpeed - downSpeed;
        while (upSpeed < desiredHeight) {
            upSpeed += night;
            day++;
        }
        return day;
    }

    /**
     * You found two items in a treasure chest!
     * The first item weighs weight1 and is worth value1, and the second item weighs weight2 and is worth value2.
     * What is the total maximum value of the items you can take with you,
     * assuming that your max weight capacity is maxW and you can't come back
     * for the items later?
     */
    static int knapsackLight(int value1, int weight1, int value2, int weight2, int maxW) {
        if (weight1 + weight2 <= maxW) {
            return value1 + value2;
        }
        if (weight1 + weight2 > maxW && weight1 <= maxW && weight2 <= maxW && value1 > value2) {
            return value1;
        }
        if (weight1 + weight2 > maxW && weight1 <= maxW && weight2 <= maxW && value1 < value2) {
            return value2;
        }
        if (weight1 + weight2 > maxW && weight1 <= maxW && weight2 <= maxW && value1 == value2) {
            return value2;
        }
        if (weight1 + weight2 > maxW && weight1 <= maxW && weight2 > maxW) {
            return value1;
        }
        if (weight1 + weight2 > maxW && weight2 <= maxW && weight1 > maxW) {
            return value2;
        }
        if (weight1 + weight2 > maxW && weight2 <= maxW && weight1 <= maxW) {
            return value2;
        }
        return 0;
    }

    /**
     * Given a string, output its longest prefix which contains only digits.
     */
    static String longestDigitsPrefix(String inputString) {
        String a = "";
        for (int i = 0; i < inputString.length(); i++) {
            if (Character.isDigit(inputString.charAt(i))) {
                a += inputString.charAt(i);
            } else break;
        }

        return a;
    }

    /**
     * Let's define digit degree of some positive integer as the number of times we need to replace this number
     * with the sum of its digits until we get to a one digit number.
     * Given an integer, find its digit degree.
     */
    static int digitDegree(int n) {
        String a = Integer.toString(n);
        int sum = 0;
        int lan = 0;
        while (a.length() > 1) {
            for (int i = 0; i < a.length(); i++) {
                sum = sum + Character.getNumericValue(a.charAt(i));
            }
            a = Integer.toString(sum);
            lan++;
            sum = 0;
        }
        return lan;

    }

    /**
     * Given the positions of a white bishop and a black pawn on the standard chess board,
     * determine whether the bishop can capture the pawn in one move.
     * The bishop has no restrictions in distance for each move, but is limited to diagonal movement.
     */
    static boolean bishopAndPawn(String bishop, String pawn) {
        char[] ngang = new char[]{'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h'};
        char[] doc = new char[]{'1', '2', '3', '4', '5', '6', '7', '8'};
        char[] a = bishop.toCharArray();
        char[] b = pawn.toCharArray();
        int ngang1 = 0;
        int ngang2 = 0;
        int doc1 = 0;
        int doc2 = 0;
        for (int i = 0; i < ngang.length; i++) {
            if (ngang[i] == bishop.charAt(0)) {
                ngang1 = i + 1;
            }
        }
        for (int i = 0; i < ngang.length; i++) {
            if (ngang[i] == pawn.charAt(0)) {
                ngang2 = i + 1;
            }
        }
        for (int i = 0; i < doc.length; i++) {
            if (doc[i] == bishop.charAt(1)) {
                doc1 = i + 1;
            }
        }
        for (int i = 0; i < doc.length; i++) {
            if (doc[i] == pawn.charAt(1)) {
                doc2 = i + 1;
            }
        }
        int x = Math.abs(ngang1 - ngang2);
        int y = Math.abs(doc1 - doc2);
        if (x == y) {
            return true;
        }
        return false;
    }

}
