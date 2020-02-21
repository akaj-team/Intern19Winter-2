package asiantech.internship.summer.thecore

class AttheCrossroads {
}

fun main() {
    println(tennisSet(7, 5))
}

/**
 * 09
 * You are playing an RPG game. Currently your experience points (XP)
 * total is equal to experience. To reach the ic_next level your XP
 * should be at least at threshold. If you kill the monster in front of you,
 * you will gain more experience points in the amount of the reward.

 * Given values experience, threshold and reward,
 * check if you reach the ic_next level after killing the monster.
 */
fun reachNextLevel(experience: Int, threshold: Int, reward: Int): Boolean {
    if ((experience + reward) >= threshold) {
        return true
    }
    return false
}

/**
 * 10
 * You found two items in a treasure chest!
 * The first item weighs weight1 and is worth value1,
 * and the second item weighs weight2 and is worth value2.
 * What is the total maximum value of the items you can take with you,
 * assuming that your max weight capacity is maxW and you can't come ic_previous for the items later?
 */
fun knapsackLight(value1: Int, weight1: Int, value2: Int, weight2: Int, maxW: Int): Int {
    var value: Int = 0
    var max = maxW
    if ((weight1 <= max && (value1 > value2 || weight2 > max)) || (weight1 + weight2 <= max)) {
        value += value1
        max -= weight1
    }
    if (weight2 <= max) {
        value += value2
        max -= weight2
    }
    return value
}

/**
 * 11
 * You're given three integers, a, b and c.
 * It is guaranteed that two of these integers are equal to each other.
 * What is the value of the third integer?
 */
fun extraNumber(a: Int, b: Int, c: Int): Int {
    val list = mutableListOf<Int>(a, b, c)
    list.groupBy { it }.entries.find {
        it.value.size == 1
    }?.let {
        return it.key
    }
    return 0
}

/**
 * 12
 * Given integers a and b, determine whether
 * the following pseudocode results in an infinite loop
 * while a is not equal to b do
increase a by 1
decrease b by 1
 * Assume that the program is executed on
 * a virtual machine which can store arbitrary long numbers and execute forever.
 */
fun isInfiniteProcess(a: Int, b: Int): Boolean {
    if ((a < b && (a - b) % 2 != 0) || a > b) {
        return true
    }
    return false
}

/**
 * 13
 * Consider an arithmetic expression of the form a#b=c.
 * Check whether it is possible to replace # with
 * one of the four signs: +, -, * or / to obtain a correct expression.
 */
fun arithmeticExpression(a: Int, b: Int, c: Int): Boolean {
    if (a + b == c || a - b == c || a * b == c || (a / b == c && a % b == 0)) {
        return true
    }
    return false
}

/**
 * 14
 * Write a function to return a true score of the tenis match
 */
fun tennisSet(score1: Int, score2: Int): Boolean {
    val a = score1
    val b = score2
    if (a > b) {
        if ((a == 6 && b != 5) || (a == 7 && b >= 5)) {
            return true;
        }
    }
    if (a < b) {
        if ((b == 6 && a != 5) || (b == 7 && a >= 5)) {
            return true;
        }
    }
    return false
}

/**
 * 15
 * Once Mary heard a famous song, and a line from it stuck in her head.
 * That line was "Will you still love me when I'm no longer young and beautiful?".
 * Mary believes that a person is loved if and only if he/she is both young and beautiful,
 * but this is quite a depressing thought, so she wants to put her belief to the test.

 * Knowing whether a person is young, beautiful and loved, find out if they contradict Mary's belief.

 * A person contradicts Mary's belief if one of the following statements is true:
 */
fun willYou(young: Boolean, beautiful: Boolean, loved: Boolean): Boolean {
    return (!(young && beautiful) && loved) || ((young && beautiful) && !loved)
}

/**
 * 16
 * Write a function to return the number of
 * last month when you knew the number of this month
 */
fun metroCard(lastNumberOfDays: Int): MutableList<Int> {
    var list: MutableList<Int> = mutableListOf<Int>()
    when (lastNumberOfDays) {
        30, 28 -> list = mutableListOf<Int>(31)
        31 -> list = mutableListOf<Int>(28, 30, 31)
    }
    return list
}