package asiantech.internship.summer.thecore

object Exercise1 {
    @JvmStatic
    fun main(args: Array<String>) {

    }

    /*
    * You are given a two-digit integer n. Return the sum of its digits.
    */
    fun addTwoDigits(n: Int): Int {
        return n % 10 + n / 10;
    }

    /*
    * Given an integer n, return the largest number that contains exactly n digits.
    * */
    fun largestNumber(n: Int): Int {
        var result = 0.0
        for (i in 0 until n) {
            result += 9.0 * Math.pow(10.0, i.toDouble());
        }
        return result.toInt()
    }
    /*
    * n children have got m pieces of candy. They want to eat as much candy as they can, but each child must eat exactly the same amount of candy as any other child.
    *  Determine how many pieces of candy will be eaten by all the children together. Individual pieces of candy cannot be split.
    * */
    fun candies(n: Int, m: Int): Int {
        when{
            m < n  -> return 0
            m == n -> return n
            else -> return m - (m % n)
        }
        return 0
    }

    /*
    *Given a divisor and a bound, find the largest integer N such that:
    *N is divisible by divisor.
    *N is less than or equal to bound.
    *N is greater than 0.
    * */
    fun maxMultiple(divisor: Int, bound: Int): Int {
        return divisor*(bound/divisor)
    }
    /*
    * Given the total number of rows and columns in the theater (nRows and nCols, respectively), and the row and column you're sitting in, return the number of people who sit strictly behind you and in your column or to the left, assuming all seats are occupied.
    * */
    fun seatsInTheater(nCols: Int, nRows: Int, col: Int, row: Int): Int {
        return(nCols+1-col)*(nRows-row)
    }
    /*
    * Consider integer numbers from 0 to n - 1 written down along the circle in such a way that the distance between any two neighboring numbers is equal (note that 0 and n - 1 are neighboring, too).
    * Given n and firstNumber, find the number which is written in the radially opposite position to firstNumber.
    * */
    fun circleOfNumbers(n: Int, firstNumber: Int): Int{
        return (firstNumber + n/2) % n
    }
    /*
    * Using the bike's timer, calculate the current time. Return an answer as the sum of digits that the digital timer in the format hh:mm would show.
    * */
    fun lateRide(n: Int): Int {
        return (n/60)/10 + (n/60)%10 + (n%60)/10 + (n%60)%10
    }
    /*
    *
    * */
    fun phoneCall(min1: Int, min2_10: Int, min11: Int, s: Int): Int {
        var minCall = s
        if(s<min1){
            return 0
        }
        var i = 0
        while(minCall>0 && i<10 ){
            minCall -= min2_10
            i++
        }
        while(s>0){
            minCall -= min11
            i++
        }
        return i
    }

}
