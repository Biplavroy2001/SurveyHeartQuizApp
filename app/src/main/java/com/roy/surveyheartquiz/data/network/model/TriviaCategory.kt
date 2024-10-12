package com.roy.surveyheartquiz.data.network.model
/** By ROY */
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TriviaCategory(
    val id: Int,
    val name: String
): Parcelable
