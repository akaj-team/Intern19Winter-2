package asiantech.internship.summer.thecore

import kotlin.math.pow

fun main() {


}
/*
* Implement the missing code, denoted by ellipses. You may not modify the pre-existing code.
* */
fun killKthBit(n: Int, k: Int): Int {
    return n and (1 shl k - 1).inv()
}
/*
* You are given an array of up to four non-negative integers, each less than 256.
* */
fun arrayPacking(a: MutableList<Int>): Int {
    var s = ""
    var result = ""
    a.forEach {
        s = it.toString(2)
        while (s.length<8){
            s = "0" + s
        }
        result = s + result
        s = ""
    }
    result = Integer.parseInt(result,2).toString()
    return result.toInt()
}
/*
*You are given two numbers a and b where 0 ≤ a ≤ b. Imagine you construct an array of all the integers from a to b inclusive. You need to count the number of 1s in the binary representations of all the numbers in the array.
* */
fun rangeBitCount(a: Int, b: Int): Int {
    if (a in 0..b) {
        var total = 0;
        for (i in a..b) {
            var t = i
            while (t != 0) {
                if ((t and 1) == 1)
                    total++
                t =t shr 1
            }
        }
        return total;
    }
    return 0
}
/*
* Reverse the order of the bits in a given integer.
* */
fun mirrorBits(a: Int): Int {
    return Integer.parseInt(Integer.toBinaryString(a).toString().reversed(),2)
}
/*
* Presented with the integer n, find the 0-based position of the second rightmost zero bit in its binary representation (it is guaranteed that such a bit exists), counting from right to left.

Return the value of 2position_of_the_found_bit.
* */
fun secondRightmostZeroBit(n: Int): Int {
    return 2.0.pow(
            Integer.toBinaryString(n).length - Integer.toBinaryString(n).lastIndexOf('0',
                    Integer.toBinaryString(n).lastIndexOf('0') - 1
            ) - 1.toDouble()
    ).toInt()
}
/*
* You're given an arbitrary 32-bit integer n. Take its binary representation, split bits into it in pairs (bit number 0 and 1, bit number 2 and 3, etc.) and swap bits in each pair. Then return the result as a decimal number.
* */
fun swapAdjacentBits(n: Int): Int {
    return n and -0x55555556 shr 1 or (n and 0x55555555 shl 1)
}
/*
* You're given two integers, n and m. Find position of the rightmost bit in which they differ in their binary representations (it is guaranteed that such a bit exists), counting from right to left.Return the value of 2position_of_the_found_bit (0-based).
* */
fun differentRightmostBit(n: Int, m: Int): Int {
    return n xor m and -(n xor m)
}
/*
*You're given two integers, n and m. Find position of the rightmost pair of equal bits in their binary representations (it is guaranteed that such a pair exists), counting from right to left.
* */
fun equalPairOfBits(n: Int, m: Int): Int {
    return (n xor m).inv() and (n xor m) + 1
}


