package mastermind

import java.lang.StringBuilder

data class Evaluation(val rightPosition: Int, val wrongPosition: Int)

fun evaluateGuess(secret: String, guess: String): Evaluation {

    val secretArray = convertSecretIntoCharArray(secret)

    val feedback = StringBuilder()

    var position = 0

    var countRightWord = 0
    var countWrongWord = 0
    var countRightWordAndPosition = 0
    var countRightWordNotPosition = 0

    for (i in guess) {

        val s = secretArray[position]

        when (i) {
            in secretArray -> {

                when(i){
                    s -> {
                        secretArray[position] = ' '
                        feedback.append("Letter $i and position is right! \n")
                        countRightWord += 1
                        countRightWordAndPosition += 1
                    }
                    else -> {
                        feedback.append("Letter $i is in the wrong position! \n")
                        countRightWord += 1
                        countRightWordNotPosition += 1
                    }
                }
            }
            else -> {
                countWrongWord += 1
                feedback.append("There is no letter $i in the code! \n")
            }
        }

        position += 1
    }

    println(feedback)

    return Evaluation(countRightWordAndPosition, countRightWordNotPosition)
}

private fun convertSecretIntoCharArray(secret: String): CharArray {
    val secretArray = CharArray(secret.length)
    var charArrayPosition = 0

    for (i in secret) {
        secretArray[charArrayPosition] = i
        charArrayPosition += 1
    }
    return secretArray
}

