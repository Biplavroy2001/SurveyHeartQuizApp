package com.roy.surveyheartquiz.data.network.model
/** By ROY */
import com.google.gson.annotations.SerializedName

data class AllQuestions(
    @SerializedName("response_code")
    val responseCode: Int,
    @SerializedName("results")
    val questions: List<Question>
)