package asiantech.internship.summer.TheCore

/**
Return the sum of its digits.
 */
fun addTwoDigits(n: Int): Int {
    var a: Int
    var result: Int = 0
    var sum: Int
    var b: Int
    a = n % 10
    result = n / 10
    b = result % 10
    sum = a + b
    return sum
}

/**
Given an integer n
Return the largest number that contains exactly n digits.
 */
fun largestNumber(n: Int): Int {
    var a = 0.0
    for (i in 0 until n) {
        a = a + 9 * Math.pow(10.0, i.toDouble())
    }
    return a.toInt()
}

/**
n children have got m pieces of candy.
They want to eat as much candy as they can
but each child must eat exactly the same amount of candy as any other child.
Determine how many pieces of candy will be eaten by all the children together.
 */
fun candies(n: Int, m: Int): Int {
    return m / n * n
}

/**
Given the total number of rows and columns in the theater (nRows and nCols, respectively),
and the row and column you're sitting in,
return the number of people who sit strictly behind you and in your column or to the left,
assuming all seats are occupied.
 */
fun seatsInTheater(nCols: Int, nRows: Int, col: Int, row: Int): Int {
    return (nCols - (col - 1)) * (nRows - row)
}

/**
Given a divisor and a bound, find the largest integer N such that:
N is divisible by divisor.
N is less than or equal to bound.
N is greater than 0.
It is guaranteed that such a number exists.
 */
fun maxMultiple(divisor: Int, bound: Int): Int {
    return bound / divisor * divisor
}

/**
Given n and firstNumber,
find the number which is written in the radially opposite position to firstNumber.
 */
fun circleOfNumbers(n: Int, firstNumber: Int): Int {
    if (firstNumber < n / 2) {
        return n / 2 + firstNumber
    }
    if (firstNumber > n / 2) {
        return firstNumber - n / 2
    }
    return 0
}

/**
Return the sum of digits that the digital timer in the format hh:mm would show.
 */
fun lateRide(n: Int): Int {
    var hour: Int
    hour = n / 60
    var minute: Int
    minute = n % 60
    var sum: Int
    var a = hour % 10
    var a1 = hour / 10
    var a2 = a1 % 10
    var b = minute % 10
    var b1 = minute / 10
    var b2 = b1 % 10
    sum = a + a2 + b + b2
    return sum
}

/**
You have s cents on your account before the call.
What is the duration of the longest call (in minutes rounded down to the nearest integer) you can have?
 */
fun phoneCall(min1: Int, min2_10: Int, min11: Int, s: Int): Int {
    var sum: Int = 0
    var a: Int
    var b: Int
    var i: Int = 9
    if (s >= min1) {
        sum += 1
        while (i >= 1) {
            if (min2_10 * i < (s - min1)) {
                sum = sum + i
                if (s - min1 - min2_10 * i > min11) {
                    b = (s - min1 - min2_10 * i) / min11
                    sum = sum + b
                }
                break
            }
            i--
        }
    }
    return sum
}
