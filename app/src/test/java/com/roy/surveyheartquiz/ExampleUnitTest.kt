package com.roy.surveyheartquiz
/** By ROY */
import com.roy.surveyheartquiz.data.network.model.FormattedQuestion
import com.roy.surveyheartquiz.data.network.model.Question
import com.roy.surveyheartquiz.util.Constants
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }


    /**
     * This function tests if the Question object can be mapped with Formatted Question
     * INPUT: [Constants.listOfQuestyions]
     * OUTPUT: [Constants.listOfFormattedQ]
     */
    @Test
    fun mapping_Question_To_FormattedQuestion() {
        var listOfOptions: List<FormattedQuestion> = listOf()

        val listOfQuestion: List<Question> = Constants.listOfQuestyions

        val job = listOfQuestion.map {

            val combinedList = it.incorrectAnswers.plus(it.correctAnswer)

            val formattedQuestion = FormattedQuestion(
                category = it.category,
                correctAnswer = it.correctAnswer,
                question = it.question,
                options = combinedList,
                difficulty = "medium",
                type = "multiple"
            )

            formattedQuestion

        }

        listOfOptions = job
        val expected = Constants.listOfFormattedQ

        listOfOptions.forEach {
            var count = 0
            it.options.forEach { option ->
                assertEquals(option, expected.options[count++])
            }
        }

    }

}