package asiantech.internship.summer.ex2


class IntroGates {

    /**
     * You are given a two-digit integer n. Return the sum of its digits.
     */
    fun addTwoDigits(n: Int): Int {
        return (n / 10 + n % 10)
    }

    /**
     * Given an integer n, return the largest number that contains exactly n digits.
     */
    fun largestNumber(n: Int): Int {
        var result = 0
        for (i in 1..n) {
            result = result * 10 + 9
        }
        return result
    }

    /**
     * n children have got m pieces of candy. They want to eat as much candy as they can,
     * but each child must eat exactly the same amountof candy as any other child.
     * Determine how many pieces of candy will be eaten by all the children together.
     * Individual pieces of candy cannot be split.
     */
    fun candies(n: Int, m: Int): Int {
        return m / n * n;
    }

    /**
     * Given the total number of rows and columns in the theater (nRows and nCols, respectively),
     * and the row and column you're sitting in, return the number of people who sit strictly behind you
     * and in your column or to the left, assuming all seats are occupied.
     */
    fun seatsInTheater(nCols: Int, nRows: Int, col: Int, row: Int): Int {
        return (nCols - col + 1) * (nRows - row)
    }

    /**
     * Given a divisor and a bound, find the largest integer N such that:
     * N is divisible by divisor.
     * N is less than or equal to bound.
     * N is greater than 0.
     * It is guaranteed that such a number exists.
     */
    fun maxMultiple(divisor: Int, bound: Int): Int {
        return bound / divisor * divisor;
    }

    /**
     * Consider integer numbers from 0 to n - 1 written down along the circle in such a way that the distance
     * between any two neighboring numbers is equal (note that 0 and n - 1 are neighboring, too).
     * Given n and firstNumber, find the number which is written in the radially opposite position to firstNumber.
     */
    fun circleOfNumbers(n: Int, firstNumber: Int): Int {
        return (firstNumber + n / 2) % n
    }

    /**
     * One night you go for a ride on your motorcycle. At 00:00 you start your engine,
     * and the built-in timer automatically begins counting the length of your ride, in minutes.
     * Off you go to explore the neighborhood. When you finally decide to head back,
     * you realize there's a chance the bridges on your route home are up, leaving you stranded!
     * Unfortunately, you don't have your watch on you and don't know what time it is.
     * All you know thanks to the bike's timer is that n minutes have passed since 00:00.
     * Using the bike's timer, calculate the current time. Return an answer as the sum of digits
     * that the digital timer in the format hh:mm would show.
     */
    fun lateRide(n: Int): Int {
        return n % 60 % 10 + n % 60 / 10 + n / 60 % 10 + n / 600
    }

    /**
     * Some phone usage rate may be described as follows:
     * first minute of a call costs min1 cents,
     * each minute from the 2nd up to 10th (inclusive) costs min2_10 cents
     * each minute after 10th costs min11 cents.
     * You have s cents on your account before the call.
     * What is the duration of the longest call (in minutes rounded down to the nearest integer) you can have?
     */
    fun phoneCall(min1: Int, min2_10: Int, min11: Int, s: Int): Int {
        var result = 0
        var temp = s
        temp -= min1
        if (temp >= 0) {
            result += 1
        }
        while (temp - min2_10 >= 0 && result < 10) {
            temp -= min2_10
            result += 1
        }
        while (temp - min11 >= 0 && result >= 10) {
            temp -= min11
            result += 1
        }
        return result
    }
}
