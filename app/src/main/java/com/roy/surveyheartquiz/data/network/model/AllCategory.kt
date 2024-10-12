package com.roy.surveyheartquiz.data.network.model
/** By ROY */
import com.google.gson.annotations.SerializedName

data class AllCategory(
    @SerializedName("trivia_categories")
    val categories: List<TriviaCategory>
)
