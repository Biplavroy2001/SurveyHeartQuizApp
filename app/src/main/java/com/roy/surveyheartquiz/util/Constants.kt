package com.roy.surveyheartquiz.util
/** By ROY */
import com.roy.surveyheartquiz.data.network.model.FormattedQuestion
import com.roy.surveyheartquiz.data.network.model.Question

object Constants {

    const val BASE_URL = "https://opentdb.com"

    // Sample models created for Unit Testing
    val listOfQuestyions = listOf(
        Question(
            category = "Entertainment: Video Games",
            correctAnswer = "Victor Branco",
            question = "Who was the main antagonist of Max Payne 3 (2012)?",
            incorrectAnswers = arrayListOf(
                "&Aacute;lvaro Neves",
                "Armando Becker",
                "Milo Rego"
            ),
            difficulty = "medium",
            type = "multiple"
        )
    )

    // Sample models created for Unit Testing
    val listOfFormattedQ = FormattedQuestion(
        category = "Entertainment: Video Games",
        correctAnswer = "Victor Branco",
        question = "Who was the main antagonist of Max Payne 3 (2012)?",
        options = arrayListOf(
            "&Aacute;lvaro Neves",
            "Armando Becker",
            "Milo Rego",
            "Victor Branco"
        ),
        difficulty = "medium",
        type = "multiple"
    )
}