@file:Suppress("UNREACHABLE_CODE")

package asiantech.internship.summer.thecore

fun main() {

}

/**
 * 01
 * You are given a two-digit integer n. Return the sum of its digits.
 */
fun addTwoDigits(n: Int): Int {
    val balance: Int
    val s: Int
    var sum: Int = 0
    balance = n % 10
    s = n / 10
    sum = s + balance
    return sum
}

/**
 * 02
 * Given an integer n, return the largest number that contains exactly n digits.
 */
fun largestNumber(n: Int): Int {
    var result = 0.0
    for (i in 0 until n) {
        result += 9 * Math.pow(10.0, i.toDouble())
    }
    return result.toInt()
}

/**
 * 03
 * n children have got m pieces of candy.
 * They want to eat as much candy as they can,
 * but each child must eat exactly the same amount of candy
 * as any other child. Determine how many pieces of candy
 * will be eaten by all the children together. Individual
 * pieces of candy cannot be split.
 */
fun candies(n: Int, m: Int): Int {
    when {
        n > m -> return 0
        n == m -> return n
        else -> return m - (m % n)
    }
    return n
}

/**
 * 04
 * Given the total number of rows and columns in the theater
 * (nRows and nCols, respectively), and the row and column you're sitting in,
 * return the number of people who sit strictly behind you
 * and in your column or to the left, assuming all seats are occupied.
 */
fun seatsInTheater(nCols: Int, nRows: Int, col: Int, row: Int): Int {
    return ((nCols - col + 1) * (nRows - row))
}

/**
 * 05
 * Given a divisor and a bound, find the largest integer N such that:

 * N is divisible by divisor.
 * N is less than or equal to bound.
 * N is greater than 0.*
 * It is guaranteed that such a number exists.
 */
fun maxMultiple(divisor: Int, bound: Int): Int {
    var max: Int = 0
    for (i in divisor..bound) {
        if (i % divisor == 0) {
            max = i
        }
    }
    return max
}

/**
 * 06
 * Consider integer numbers from 0 to n - 1 written down along
 * the circle in such a way that the distance between any
 * two neighboring numbers is equal (note that 0 and n - 1
 * are neighboring, too).

 * Given n and firstNumber, find the number which is written
 * in the radially opposite position to firstNumber.
 */
fun circleOfNumbers(n: Int, firstNumber: Int): Int {
    return (firstNumber + n / 2) % n
}

/**
 * 07
 * Using the bike's timer, calculate the current time.
 * Return an answer as the sum of digits that
 * the digital timer in the format hh:mm would show.
 */
fun lateRide(n: Int): Int {
    val h = n / 60
    val m = n % 60
    val sum = (h / 10) + (h % 10) + (m / 10) + (m % 10)
    return sum
}

/**
 * 08
 * Some phone usage rate may be described as follows:

 * first minute of a call costs min1 cents,
 * each minute from the 2nd up to 10th (inclusive) costs min2_10 cents
 * each minute after 10th costs min11 cents.
 * You have s cents on your account before the call.
 * What is the duration of the longest call
 * (in minutes rounded down to the nearest integer) you can have?
 */
fun phoneCall(min1: Int, min2_10: Int, min11: Int, s: Int): Int {
    if (s < min1) {
        return 0;
    }
    for (i in 2..10) {
        if (s < ((min1) + (min2_10) * (i - 1))) {
            return i - 1
        }
    }
    return 10 + ((s - min1 - (min2_10 * 9)) / min11)
}