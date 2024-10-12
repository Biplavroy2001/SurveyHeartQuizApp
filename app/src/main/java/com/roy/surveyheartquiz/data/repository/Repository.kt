package com.roy.surveyheartquiz.data.repository
/** By ROY */
import com.roy.surveyheartquiz.data.network.api.ApiService
import com.roy.surveyheartquiz.data.network.model.AllCategory
import com.roy.surveyheartquiz.data.network.model.AllQuestions
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers

class Repository (
    private val triviaApi: ApiService
) {

    fun getCategories(): Observable<AllCategory> {
        return triviaApi
            .getCategories()
            .subscribeOn(Schedulers.io())
    }

    /**
     * This function is used to get questions list from the API based on category Id and difficulty level
     * @param categoryId is the id of the category, selected by the USER
     * @param difficulty is the difficulty level, selected by the USER
     */
    fun getQuestions(categoryId: Int, difficulty: String): Observable<AllQuestions> {
        return triviaApi
            .getQuestions(category = categoryId, difficulty = difficulty)
            .subscribeOn(Schedulers.io())
    }

}