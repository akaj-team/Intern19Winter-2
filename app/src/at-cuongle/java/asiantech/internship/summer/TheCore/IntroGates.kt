package asiantech.internship.summer.TheCore

import android.util.Log

class IntroGates {

}

fun main() {
}

fun addTwoDigits(n: Int): Int {
    /**
     * You are given a two-digit integer n. Return the sum of its digits.
     */
    return (n / 10 + n % 10)
}

fun largestNumber(n: Int): Int {
    /**
     * Given an integer n, return the largest number that contains exactly n digits.
     */
    var result = 0.0
    for (i in 0 until n) {
        result += 9 * Math.pow(10.0, i.toDouble())
    }
    return result.toInt()
}

fun candies(n: Int, m: Int): Int {
    /**
     * n children have got m pieces of candy. They want to eat as much candy as they can,
     * but each child must eat exactly the same amount of candy as any other child. Determine
     * how many pieces of candy will be eaten by all the children together. Individual pieces of
     * candy cannot be split.
     */
    return when {
        n > m -> 0
        n == m -> n
        else -> m - (m % n)

    }
    return 0
}

fun seatsInTheater(nCols: Int, nRows: Int, col: Int, row: Int): Int {
    /**
     * Your friend advised you to see a new performance in the most popular theater in the city. He knows
     * a lot about art and his advice is usually good, but not this time: the performance turned out to be
     * awfully dull. It's so bad you want to sneak out, which is quite simple, especially since the exit is
     * located right behind your row to the left. All you need to do is climb over your seat and make your
     * way to the exit.

    The main problem is your shyness: you're afraid that you'll end up blocking the view (even if only for a
    couple of seconds) of all the people who sit behind you and in your column or the columns to your left.
    To gain some courage, you decide to calculate the number of such people and see if you can possibly make
    it to the exit without disturbing too many people.

    Given the total number of rows and columns in the theater (nRows and nCols, respectively), and the row
    and column you're sitting in, return the number of people who sit strictly behind you and in your column
    or to the left, assuming all seats are occupied.
     */
    return ((nCols - col + 1) * (nRows - row))
}

fun maxMultiple(divisor: Int, bound: Int): Int {
    /**
     * Given a divisor and a bound, find the largest integer N such that:

    N is divisible by divisor.
    N is less than or equal to bound.
    N is greater than 0.
    It is guaranteed that such a number exists.
     */
    return divisor * (bound / divisor)
}

fun circleOfNumbers(n: Int, firstNumber: Int): Int {
    /**
     * Consider integer numbers from 0 to n - 1 written down along the circle in such a way that the distance
     * between any two neighboring numbers is equal (note that 0 and n - 1 are neighboring, too).

    Given n and firstNumber, find the number which is written in the radially opposite position to firstNumber.
     */
    var result = n / 2 + firstNumber
    if (result >= n) {
        result -= n
    }
    return result
}

fun lateRide(n: Int): Int {
    /**
     * One night you go for a ride on your motorcycle. At 00:00 you start your engine, and the built-in timer
     * automatically begins counting the length of your ride, in minutes. Off you go to explore the neighborhood.

    When you finally decide to head back, you realize there's a chance the bridges on your route home are up, leaving
    you stranded! Unfortunately, you don't have your watch on you and don't know what time it is. All you know thanks
    to the bike's timer is that n minutes have passed since 00:00.

    Using the bike's timer, calculate the current time. Return an answer as the sum of digits that the digital timer in
    the format hh:mm would show.
     */
    var timeHours = n / 60
    var timeMin = n % 60
    return timeHours / 10 + timeHours % 10 + timeMin / 10 + timeMin % 10
}

fun phoneCall(min1: Int, min2_10: Int, min11: Int, s: Int): Int {
    /**
     * Some phone usage rate may be described as follows:

    first minute of a call costs min1 cents,
    each minute from the 2nd up to 10th (inclusive) costs min2_10 cents
    each minute after 10th costs min11 cents.
    You have s cents on your account before the call. What is the duration of the longest call (in minutes rounded down
    to the nearest integer) you can have?
     */
    var cents = s - min1
    var minutes = 1
    if (s < min1) {
        return 0
    }
    while (cents > 0) {
        minutes++
        if (minutes <= 10) {
            cents -= min2_10
        }
        if (minutes > 10) {
            cents -= min11
        }
        if (minutes <= 10 && cents < min2_10 || minutes > 10 && cents < min11) cents = 0
    }
    return minutes
}