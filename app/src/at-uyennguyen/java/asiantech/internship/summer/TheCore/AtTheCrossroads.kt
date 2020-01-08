package asiantech.internship.summer.TheCore

/**
Given values experience, threshold and reward, check if you reach the next level after killing the monster.
 */
fun reachNextLevel(experience: Int, threshold: Int, reward: Int): Boolean {
    if ((experience + reward) >= threshold) {
        return true
    }
    return false
}

/**
You found two items in a treasure chest!
The first item weighs weight1 and is worth value1,
and the second item weighs weight2 and is worth value2.
What is the total maximum value of the items you can take with you,
assuming that your max weight capacity is maxW and you can't come back for the items later?
 */
fun knapsackLight(value1: Int, weight1: Int, value2: Int, weight2: Int, maxW: Int): Int {
    if (weight1 + weight2 <= maxW) {
        return value1 + value2;
    }
    if (weight1 + weight2 > maxW && weight1 <= maxW && weight2 <= maxW && value1 > value2) {
        return value1;
    }
    if (weight1 + weight2 > maxW && weight1 <= maxW && weight2 <= maxW && value1 < value2) {
        return value2;
    }
    if (weight1 + weight2 > maxW && weight1 <= maxW && weight2 <= maxW && value1 == value2) {
        return value2;
    }
    if (weight1 + weight2 > maxW && weight1 <= maxW && weight2 > maxW) {
        return value1;
    }
    if (weight1 + weight2 > maxW && weight2 <= maxW && weight1 > maxW) {
        return value2;
    }
    if (weight1 + weight2 > maxW && weight2 <= maxW && weight1 <= maxW) {
        return value2;
    }
    return 0;
}

/**
You're given three integers, a, b and c.
It is guaranteed that two of these integers are equal to each other.
What is the value of the third integer?
 */
fun extraNumber(a: Int, b: Int, c: Int): Int {
    if (a == b) {
        return c
    }
    if (a == c) {
        return b
    }
    if (c == b) {
        return a
    }
    return 0
}

/**
Given integers a and b, determine whether the following pseudocode results in an infinite loop
while a is not equal to b do
increase a by 1
decrease b by 1
 */
fun isInfiniteProcess(a: Int, b: Int): Boolean {
    if (a == b) {
        return false
    }
    if (a > b) {
        return true
    }
    if (a < b) {
        if ((a - b) % 2 == 0) {
            return false
        }
    }
    return true;
}

/**
Consider an arithmetic expression of the form a#b=c.
Check whether it is possible to replace # with one of the four signs: +, -, * or / to obtain a correct expression.
 */
fun arithmeticExpression(a: Int, b: Int, c: Int): Boolean {
    if (a + b == c || a - b == c || a * b == c || (a / b == c && a % b == 0)) {
        return true
    }
    return false
}

/**
Given two integers score1 and score2,
your task is to determine if it is possible for a tennis set to be finished with a final score of score1 : score2.
 */
fun tennisSet(score1: Int, score2: Int): Boolean {
    if (score1 < 7 && score2 < 7) {
        if ((score1 > score2 || score1 < score2) && (score1 - score2 >= 2 || score2 - score1 >= 2)) {
            return true
        }
    }
    if ((score1 == 7 || score2 == 7) && (score1 == 5 || score2 == 6 || score2 == 5 || score1 == 6)) {
        return true
    }
    return false
}

/**
Mary believes that "Will you still love me when I'm no longer young and beautiful?"
Knowing whether a person is young, beautiful and loved, find out if they contradict Mary's belief.
A person contradicts Mary's belief if one of the following statements is true:
they are young and beautiful but not loved;
they are loved but not young or not beautiful.
 */
fun willYou(young: Boolean, beautiful: Boolean, loved: Boolean): Boolean {
    if (young == true && beautiful == true) {
        return loved == false
    }
    if (young == true && beautiful == false) {
        return loved == true
    }
    if (young == false && beautiful == true) {
        return loved == true
    }
    if (young == false && beautiful == false) {
        return loved == true
    }
    return false
}

/**
upon first receiving the card,
the system allocates you a 31-day pass,
which equals the number of days in January.
The second time you pay for the card, your pass is extended by 28 days,\
i.e. the number of days in February (note that leap years are not considered), and so on.
The 13th time you extend the pass, you get 31 days again.
Figure out the number of days by which your pass will now be extended,
and return all the options as an array sorted in increasing order.
 */
fun metroCard(lastNumberOfDays: Int): MutableList<Int> {
    var day: MutableList<Int> = mutableListOf<Int>()
    when (lastNumberOfDays) {
        30, 28 -> day = mutableListOf<Int>(31)
        31 -> day = mutableListOf<Int>(28, 30, 31)
    }
    return day
}
