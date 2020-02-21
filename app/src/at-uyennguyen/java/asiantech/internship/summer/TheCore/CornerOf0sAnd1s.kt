package asiantech.internship.summer.TheCore

/**
you to write a function that will change the kth bit of n ic_previous to 0.

 */
fun killKthBit(n: Int, k: Int): Int {
    return n and (1 shl k - 1).inv()
}

/**
You are given an array of up to four non-negative integers, each less than 256.
Your task is to pack these integers into one number M in the following way:
The first element of the array occupies the first 8 bits of M;
The second element occupies ic_next 8 bits, and so on.
Return the obtained integer M.
 */
fun arrayPacking(a: MutableList<Int>): Int {
    var result: Int = 0
    for (i in 0 until a.size) {
        result += a[i].shl(8 * i)
    }
    return result
}

/**
You are given two numbers a and b where 0 ≤ a ≤ b.
Imagine you construct an array of all the integers from a to b inclusive.
You need to count the number of 1s in the binary representations of all the numbers in the array.
 */
fun rangeBitCount(a: Int, b: Int): Int {
    var total = 0;
    for (i in a..b) {
        total += Integer.bitCount(i)
    }
    return total
}

/**
Reverse the order of the bits in a given integer.
 */
fun mirrorBits(a: Int): Int {
    return Integer.parseInt(Integer.toBinaryString(a).reversed(), 2)
}

/**
Presented with the integer n,
find the 0-based position of the second rightmost zero bit in its binary representation (it is guaranteed that such a bit exists),
counting from right to left.
 */
fun secondRightmostZeroBit(n: Int): Int {
    return -(n - (n xor n + 1).inv() / 2 xor n - (n xor n + 1).inv() / 2 + 1).inv() / 2
}

/**
You're given an arbitrary 32-bit integer n.
Take its binary representation, split bits into it in pairs (bit number 0 and 1, bit number 2 and 3, etc.)
and swap bits in each pair.
Then return the result as a decimal number.
 */
fun swapAdjacentBits(n: Int): Int {
    return n and 0x2AAAAAAA shr 1 or (n and 0x15555555 shl 1)
}

/**
You're given two integers, n and m.
Find position of the rightmost bit in which they differ in their binary representations
(it is guaranteed that such a bit exists),
counting from right to left.
Return the value of 2position_of_the_found_bit (0-based).
 */
fun differentRightmostBit(n: Int, m: Int): Int {
    return -((n xor m).inv() xor (n xor m).inv() + 1).inv() / 2
}

/**
You're given two integers, n and m.
Find position of the rightmost pair of equal bits in their binary representations (it is guaranteed that such a pair exists),
counting from right to left.
Return the value of 2position_of_the_found_pair (0-based).
 */
fun equalPairOfBits(n: Int, m: Int): Int {
    return n + m + 1 and m.inv() - n
}
