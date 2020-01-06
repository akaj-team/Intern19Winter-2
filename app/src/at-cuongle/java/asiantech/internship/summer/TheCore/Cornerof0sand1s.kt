package asiantech.internship.summer.TheCore

import kotlin.math.pow

class Cornerof0sand1s {
}

fun main() {

}

fun arrayPacking(a: MutableList<Int>): Int {
    /**
     * You are given an array of up to four non-negative integers, each less than 256.

    Your task is to pack these integers into one number M in the following way:

    The first element of the array occupies the first 8 bits of M;
    The second element occupies next 8 bits, and so on.
    Return the obtained integer M.

    Note: the phrase "first bits of M" refers to the least significant bits of M - the
    right-most bits of an integer. For further clarification see the following example.
     */
    var binaryNumber = ""
    var result = ""
    var resultToDec = ""
    a.forEach {
        binaryNumber = it.toString(2)
        while (binaryNumber.length < 8) {
            binaryNumber = "0$binaryNumber"
        }
        result = binaryNumber + result
        resultToDec = Integer.parseInt(result, 2).toString()
        binaryNumber = ""
    }
    return resultToDec.toInt()
}

fun rangeBitCount(a: Int, b: Int): Int {
    /**
     * You are given two numbers a and b where 0 ≤ a ≤ b. Imagine you construct an array of all
     * the integers from a to b inclusive. You need to count the number of 1s in the binary representations
     * of all the numbers in the array.
     */
    if (a in 0..b) {
        var total = 0
        for (i in a..b) {
            total += Integer.bitCount(i)
        }
        return total
    }
    return 0
}

fun mirrorBits(a: Int): Int {
    /**
     * Reverse the order of the bits in a given integer.
     */
    return Integer.parseInt(Integer.toBinaryString(a).reversed(), 2)
}

fun secondRightmostZeroBit(n: Int): Int {
    /**
     * Presented with the integer n, find the 0-based position of the second rightmost zero bit
     * in its binary representation (it is guaranteed that such a bit exists), counting from right to left.

    Return the value of 2position_of_the_found_bit.
     */
    return 2.0.pow(
            Integer.toBinaryString(n).length - Integer.toBinaryString(n).lastIndexOf(
                    '0',
                    Integer.toBinaryString(n).lastIndexOf('0') - 1
            ) - 1.toDouble()
    ).toInt()
}

fun swapAdjacentBits(n: Int): Int {
    /**
     * You're given an arbitrary 32-bit integer n. Take its binary representation, split bits into
     * it in pairs (bit number 0 and 1, bit number 2 and 3, etc.) and swap bits in each pair. Then
     * return the result as a decimal number.
     */
    return n and 0x2AAAAAAA shr 1 or (n and 0x15555555 shl 1)
}

fun differentRightmostBit(n: Int, m: Int): Int {
    /**
     * You're given two integers, n and m. Find position of the rightmost bit in which they
     * differ in their binary representations (it is guaranteed that such a bit exists), counting
     * from right to left.

    Return the value of 2position_of_the_found_bit (0-based).
     */
    return -((n xor m).inv() xor (n xor m).inv() + 1).inv() / 2
}

fun equalPairOfBits(n: Int, m: Int): Int {
    /**
     * You're given two integers, n and m. Find position of the rightmost pair of equal
     * bits in their binary representations (it is guaranteed that such a pair exists),
     * counting from right to left.

    Return the value of 2position_of_the_found_pair (0-based).
     */
    return (n xor m) + 1 and (m xor n).inv()
}