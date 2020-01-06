package asiantech.internship.summer.thecore

fun main() {

}


/*
* You found two items in a treasure chest! The first item weighs weight1 and is worth value1, and the second item weighs weight2 and is worth value2. What is the total maximum value of the items you can take with you, assuming that your max weight capacity is maxW and you can't come back for the items later?
* */
fun knapsackLight(value1: Int, weight1: Int, value2: Int, weight2: Int, maxW: Int): Int {
    when {
        weight1 > maxW && weight2 > maxW -> return 0
        weight1 > maxW -> return value2
        weight2 > maxW -> return value1
        weight1 + weight2 <= maxW -> return value1 + value2

    }
    return Math.max(value1, value2)
}

/*
*You're given three integers, a, b and c. It is guaranteed that two of these integers are equal to each other. What is the value of the third integer?
* */
fun extraNumber(a: Int, b: Int, c: Int): Int {
    val list = mutableListOf<Int>(a, b, c)
    list.groupBy { it }.entries.find {
        it.value.size == 1
    }?.let {
        return it.key
    }
    return 0
}

/*
*Given integers a and b, determine whether the following pseudocode results in an infinite loop
* */
fun isInfiniteProcess(a: Int, b: Int): Boolean {
    if (a > b) {
        return true
    } else if (Math.abs(a - b) % 2 == 0) {
        return false
    }
    return true
}

/*
*Consider an arithmetic expression of the form a#b=c. Check whether it is possible to replace # with one of the four signs: +, -, * or / to obtain a correct expression.
* */
fun arithmeticExpression(a: Int, b: Int, c: Int): Boolean {
    if (a + b == c || a - b == c || a * b == c || a / b == c && a % b == 0) {
        return true
    }
    return false
}
/*
* Given two integers score1 and score2, your task is to determine if it is possible for a tennis set to be finished with a final score of score1 : score2.
* */
fun tennisSet(score1: Int, score2: Int): Boolean {
    var check:Int = (Math.max(score1,score2).toString() + Math.min(score1,score2).toString()).toInt()
    when(check){
        in 60..64 -> return true
        75,76 -> return true
    }
    return false
}
/*
*Knowing whether a person is young, beautiful and loved, find out if they contradict Mary's belief.
* */
fun willYou(young: Boolean, beautiful: Boolean, loved: Boolean): Boolean {
    return (young && beautiful && !loved) || ((!young || !beautiful) && loved)
}
/*
*You just bought a public transit card that allows you to ride the Metro for a certain number of days.
* */
fun metroCard(lastNumberOfDays: Int): MutableList<Int> {
    var day: MutableList<Int> = mutableListOf<Int>()
    when (lastNumberOfDays) {
        30 -> day.add(31)
        28 -> day.add(31)
        31 -> day.add(30)
        else -> day.addAll(mutableListOf(28, 30, 31))
    }
    return day
}

