package com.roy.surveyheartquiz.util
/** By ROY */
import android.util.Log
import com.roy.surveyheartquiz.data.enum.GameDifficulty

object Utils {

    private const val TAG = "Utils"

    /**
     * This function validates if the @name entered is in the specified range and is not empty
     */
    fun validatePlayerNameEdt(name: String): Boolean {
        // if name is not empty
        return if (name.trim().isNotEmpty()) {
            // check if the name length is in range, return true or false
            name.length in 3..32
        } else {
            // if name is empty, return false
            false
        }
    }

    /**
     * This function formats the @name and capitalizes the first letter of each word
     * E.g. ramesh -> Ramesh, Ramesh gill -> Ramesh Gill, ramesh ram -> Ramesh Ram
     */
    fun convertToNormalCase(name: String): String {
        return if(name.contains(" ")){
            val firstName: String = name.substringBefore(" ")
            val lName: String = name.substringAfter(" ")
            Log.d(TAG, "convertToNormalCase: First: $firstName, Last: $lName")
            firstName.replaceFirstChar { it.uppercase() } + " " + lName.replaceFirstChar { it.uppercase() }

        } else {
            name.replaceFirst(
                oldChar = name[0],
                newChar = name[0].uppercase()[0],
                ignoreCase = false
            )
        }
    }

    /**
     * This function converts the GameDifficulty Enum to a Valid String supported by API
     */
    fun convertGameDifficultyEnumToString(value: GameDifficulty): String {
        return when(value) {
            GameDifficulty.EASY -> "easy"
            GameDifficulty.MEDIUM -> "medium"
            GameDifficulty.HARD -> "hard"
        }
    }

    /**
     * This function maps the Response Code that we get from API to a Valid String Message to be shown
     * to the user.
     */
    fun mapResponseCodeToMessageString(responseCode: Int): String {
        return when(responseCode) {
            1 -> "No Results Could not return results. The API doesn't have enough questions for your query."
            2 -> "Invalid Parameter Contains an invalid parameter. Arguments passed in aren't valid."
            3 -> "Token Not Found Session Token does not exist."
            4 -> "Token Empty Session Token has returned all possible questions for the specified query. Resetting the Token is necessary."
            else -> "Success"
        }
    }

    /**
     * This function calculated the progress of the quiz
     * @param currentCount is the current question count
     * @param totalCount is the total number of questions, which is by default set to 15
     */
    fun calculateQuizProgress(currentCount: Int, totalCount: Int = 15): Int {
        val t: Float = ((currentCount.toFloat()/totalCount.toFloat()) * 100f)
        Log.d(TAG, "calculateQuizProgress: $t, totalCount: $totalCount, currentCount: $currentCount")
        return t.toInt()
    }

}