package com.roy.surveyheartquiz.viewmodel
/** By ROY */
import android.util.Log
import androidx.lifecycle.ViewModel
import com.roy.surveyheartquiz.data.enum.GameDifficulty
import com.roy.surveyheartquiz.data.network.RetrofitInstance
import com.roy.surveyheartquiz.data.network.api.ApiService
import com.roy.surveyheartquiz.data.network.model.AllCategory
import com.roy.surveyheartquiz.data.network.model.AllQuestions
import com.roy.surveyheartquiz.data.network.model.FormattedQuestion
import com.roy.surveyheartquiz.data.repository.Repository
import com.roy.surveyheartquiz.util.Utils
import io.reactivex.rxjava3.core.Observable

class MainViewModel: ViewModel() {

    private val triviaApi: ApiService = RetrofitInstance().apiService
    private val repository: Repository = Repository(triviaApi)

    /** PLAYER NAME - WelcomeFragment */

    private var _playerName = ""
    val playerName get(): String = _playerName


    fun setPlayerName(name: String): Boolean {
        val isValid = Utils.validatePlayerNameEdt(name)
        return if(isValid) {
            _playerName = Utils.convertToNormalCase(name)
            Log.d("MainViewModel", "setPlayerName: PlayerName: $_playerName")
            true
        } else {
            false
        }
    }

    /** CATEGORY SELECTION - SelectCategoryFragment */

    fun getCategories(): Observable<AllCategory> {
        return repository.getCategories()
    }

    /** SELECT GAME DIFFICULTY LEVEL - SelectGameDifficultyFragment */
    private var _selectedGameDifficultyLevel: GameDifficulty = GameDifficulty.EASY
    val selectedGameDifficultyLevel get(): GameDifficulty = _selectedGameDifficultyLevel

    fun setSelectedGameDifficultyLevel(level: GameDifficulty) {
        _selectedGameDifficultyLevel = level
    }


    /** GET ALL QUIZZES - QuizFragment */
    private var _isSuccess: Boolean = true
    val isResponseSuccess get(): Boolean = _isSuccess

    fun setIsResponseSuccess(value: Boolean) {
        _isSuccess = value
    }

    private var _questions: List<FormattedQuestion> = mutableListOf()
    val questions get(): List<FormattedQuestion> = _questions

    fun getQuestions(categoryId: Int, difficulty: String): Observable<AllQuestions> {
        return repository.getQuestions(categoryId, difficulty)
    }

    fun setQuestions(list: List<FormattedQuestion>) {
        _questions = list
    }

    private var _currentQuestionCount: Int = 0
    val currentQuestionCount get(): Int = _currentQuestionCount

    fun setCurrentQuestionCount(value: Int) {
        _currentQuestionCount = value
    }

    private var _correctAnswerChipId: Int = 0
    val correctAnswerChipId get(): Int = _correctAnswerChipId

    fun setCorrectAnswerChipId(value: Int) {
        _correctAnswerChipId = value
    }

    private var _playerScore: Int = 0
    val score get(): Int = _playerScore

    fun setPlayerScore(value: Int) {
        _playerScore = value
    }

}