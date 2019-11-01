package mastermind

data class Evaluation(val rightPosition: Int, val wrongPosition: Int)

private lateinit var secretArray: CharArray

fun evaluateGuess(secret: String, guess: String): Evaluation {

    secretArray = convertCodeStringToCharArray(secret)

    val isEqual = IntArray(secret.length)

    var rightLetter = 0
    var rightLetterAndPosition = 0
    var rightLetterNotPosition = 0
    var wrongLetter = 0

    var index = 0


    for (letter in guess) {

        val secretLetter = secretArray.elementAt(index = index)
        when (letter) {
            secretLetter -> {

                val counter = letterWithSameIndexCounter(
                    letterCounter = rightLetter,
                    positionCounter = rightLetterAndPosition,
                    marker = isEqual,
                    index = index)

                rightLetter = counter.first
                rightLetterAndPosition = counter.second
            }
            else -> isEqual[index] = 0
        }
        index += 1
    }

    var gPosition = 0

    for (letter in guess) {

        var sPosition = 0
        var notEqualLetterCounter = secret.length

        for (secret in secretArray) {

            val hasCounted = isEqual[sPosition]

            if (isEqual[gPosition] > 0) {
                break
            } else if (letter == secret && hasCounted == 0) {
                rightLetterNotPosition = letterWithDifferentPositionCounter(
                    letterCounter = rightLetter,
                    positionCounter = rightLetterNotPosition,
                    index = sPosition)
                break
            } else {
                notEqualLetterCounter = notSecretLetter(
                    counter = notEqualLetterCounter, letterCounter = wrongLetter)
            }
            sPosition += 1
        }
        gPosition += 1
    }

    return Evaluation(rightPosition = rightLetterAndPosition, wrongPosition = rightLetterNotPosition)
}

private fun notSecretLetter(counter: Int, letterCounter: Int): Int {
    var notEqualLetterCounter = counter
    var wrongLetter1 = letterCounter

    notEqualLetterCounter -= 1

    when (notEqualLetterCounter) {
        0 -> wrongLetter1 += 1
    }

    return notEqualLetterCounter
}

private fun letterWithDifferentPositionCounter(letterCounter: Int,
                                               positionCounter: Int,
                                               index: Int): Int {
    var rightLetter = letterCounter
    var rightLetterNotPosition = positionCounter

    rightLetter += 1
    rightLetterNotPosition += 1
    secretArray[index] = ' '

    return rightLetterNotPosition
}

private fun letterWithSameIndexCounter(letterCounter: Int,
                                       positionCounter: Int,
                                       marker: IntArray,
                                       index: Int): Pair<Int, Int> {

    var rightLetter = letterCounter
    var rightPosition = positionCounter

    rightLetter += 1
    rightPosition += 1
    marker[index] = 1
    secretArray[index] = ' '

    return Pair(rightLetter, rightPosition)
}

private fun convertCodeStringToCharArray(secret: String): CharArray {
    val charArray = CharArray(secret.length)
    var position = 0
    for (i in secret) {
        charArray[position] = i
        position += 1
    }

    return charArray
}


