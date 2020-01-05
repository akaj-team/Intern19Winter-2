package asiantech.internship.summer.TheCore

class AttheCrossroads {
}

fun reachNextLevel(experience: Int, threshold: Int, reward: Int): Boolean {
    /**
     * You are playing an RPG game. Currently your experience points (XP) total is equal
     * to experience. To reach the next level your XP should be at least at threshold. If you
     * kill the monster in front of you, you will gain more experience points in the amount of the reward.

    Given values experience, threshold and reward, check if you reach the next level after killing the monster.
     */
    if (experience + reward >= threshold) {
        return true
    }
    return false
}

fun knapsackLight(value1: Int, weight1: Int, value2: Int, weight2: Int, maxW: Int): Int {
    /**
     * You found two items in a treasure chest! The first item weighs weight1 and is worth value1,
     * and the second item weighs weight2 and is worth value2. What is the total maximum value of
     * the items you can take with you, assuming that your max weight capacity is maxW and you can't
     * come back for the items later?

    Note that there are only two items and you can't bring more than one item of each type, i.e. you
    can't take two first items or two second items.
     */
    if (weight1 > maxW && weight2 > maxW) return 0
    if (weight1 + weight2 <= maxW) return value1 + value2
    if (weight1 > maxW) return value2
    return if (weight2 > maxW) value1 else Math.max(value1, value2)
}

fun extraNumber(a: Int, b: Int, c: Int): Int {
    /**
     * You're given three integers, a, b and c. It is guaranteed that two of these integers are
     * equal to each other. What is the value of the third integer?
     */
    val list = mutableListOf<Int>(a, b, c)
    list.groupBy { it }.entries.find {
        it.value.size == 1
    }?.let {
        return it.key
    }
    return 0
}

fun isInfiniteProcess(a: Int, b: Int): Boolean {
    /**
     * Given integers a and b, determine whether the following pseudocode results in an infinite loop

    while a is not equal to b do
    increase a by 1
    decrease b by 1
    Assume that the program is executed on a virtual machine which can store arbitrary long numbers and execute forever.
     */
    if (b < a) {
        return true
    } else if (Math.abs((a - b)) % 2 == 0) {
        return false
    }
    return true
}

fun arithmeticExpression(a: Int, b: Int, c: Int): Boolean {
    /**
     * Consider an arithmetic expression of the form a#b=c. Check whether it is possible to replace # with one of the four
     * signs: +, -, * or / to obtain a correct expression.
     */
    if (a + b == c || a - b == c || a * b == c || (a / b == c && a % b == 0)) {
        return true
    }
    return false
}


fun tennisSet(score1: Int, score2: Int): Boolean {
    /**
     * In tennis, the winner of a set is based on how many games each player wins. The first player to win 6 games is declared
     * the winner unless their opponent had already won 5 games, in which case the set continues until one of the players has won
     * 7 games.

    Given two integers score1 and score2, your task is to determine if it is possible for a tennis set to be finished with a final
    score of score1 : score2.
     */
    val check = (Math.max(score1, score2).toString() + Math.min(score1, score2).toString()).toInt()
    when (check) {
        in 60..64 -> return true
        75, 76 -> return true
    }
    return false
}

fun willYou(young: Boolean, beautiful: Boolean, loved: Boolean): Boolean {
    /**
     * Once Mary heard a famous song, and a line from it stuck in her head. That line was "Will you still love me when I'm no longer
     * young and beautiful?". Mary believes that a person is loved if and only if he/she is both young and beautiful, but this is quite
     * a depressing thought, so she wants to put her belief to the test.

    Knowing whether a person is young, beautiful and loved, find out if they contradict Mary's belief.

    A person contradicts Mary's belief if one of the following statements is true:

    they are young and beautiful but not loved;
    they are loved but not young or not beautiful.
     */
    when {
        young && beautiful && !loved -> return true
        young && !beautiful && loved -> return true
        !young && beautiful && loved -> return true
        !young && !beautiful && loved -> return true
    }
    return false
}

fun metroCard(lastNumberOfDays: Int): MutableList<Int> {
    /**
     * You just bought a public transit card that allows you to ride the Metro for a certain number of days.

    Here is how it works: upon first receiving the card, the system allocates you a 31-day pass, which equals
    the number of days in January. The second time you pay for the card, your pass is extended by 28 days, i.e.
    the number of days in February (note that leap years are not considered), and so on. The 13th time you extend
    the pass, you get 31 days again.

    You just ran out of days on the card, and unfortunately you've forgotten how many times your pass has been
    extended so far. However, you do remember the number of days you were able to ride the Metro during this most
    recent month. Figure out the number of days by which your pass will now be extended, and return all the options
    as an array sorted in increasing order.
     */
    val result: MutableList<Int> = mutableListOf<Int>()
    when {
        lastNumberOfDays % 3 == 0 -> result.add(31)
        lastNumberOfDays % 2 != 0 -> result.addAll(mutableListOf(28, 30, 31))
        else -> result.add(31)
    }
    return result
}