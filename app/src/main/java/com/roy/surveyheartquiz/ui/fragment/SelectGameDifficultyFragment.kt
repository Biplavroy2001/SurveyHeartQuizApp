package com.roy.surveyheartquiz.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.roy.surveyheartquiz.R
import com.roy.surveyheartquiz.data.enum.GameDifficulty
import com.roy.surveyheartquiz.data.network.model.AllQuestions
import com.roy.surveyheartquiz.data.network.model.FormattedQuestion
import com.roy.surveyheartquiz.databinding.FragmentSelectGameDifficultyBinding
import com.roy.surveyheartquiz.util.Utils
import com.roy.surveyheartquiz.viewmodel.MainViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
/** By ROY */
private const val TAG = "SelectGameDifficulty"

/**
 * This is @SelectGameDifficultyFragment
 * Here, the user chooses a difficulty level for the quiz based on which questions would be fetched from API
 * Also, taking @selectedCategory accepted as argument by @SelectGameDifficultyFragment
 */
class SelectGameDifficultyFragment : Fragment() {

    private var _binding: FragmentSelectGameDifficultyBinding? = null
    private val binding get(): FragmentSelectGameDifficultyBinding = _binding!!

    private val args: SelectGameDifficultyFragmentArgs by navArgs()

    private lateinit var mainViewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSelectGameDifficultyBinding.inflate(inflater, container, false)

        mainViewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]

        binding.gameDifficultyChip.setOnCheckedStateChangeListener { _, checkedIds ->
            val value = when (checkedIds[0]) {
                R.id.easyChip -> GameDifficulty.EASY
                R.id.mediumChip -> GameDifficulty.MEDIUM
                R.id.hardChip -> GameDifficulty.HARD
                else -> GameDifficulty.EASY
            }

            mainViewModel.setSelectedGameDifficultyLevel(value)

            fetchQuestionsFromServer(
                args.selectedCategory.id,
                mainViewModel.selectedGameDifficultyLevel
            )
        }

        return binding.root
    }

    /**
     * Fetch Questions from Server based on the Quiz Category and Difficulty Level selected
     * @param categoryId is the id of the category selected by the user
     * @param selectedDifficulty is the GameDifficulty Level selected by the User
     */
    private fun fetchQuestionsFromServer(categoryId: Int, selectedDifficulty: GameDifficulty) {
        mainViewModel
            .getQuestions(categoryId, Utils.convertGameDifficultyEnumToString(selectedDifficulty))
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<AllQuestions> {
                override fun onSubscribe(d: Disposable) {
                    Toast.makeText(requireContext(), "Loading Quiz...", Toast.LENGTH_SHORT).show()
                    Log.d(TAG, "getQuestions: onSubscribe called")
                }

                override fun onError(e: Throwable) {
                    Log.d(TAG, "getQuestions: onError: ${e.message}")
                }

                override fun onComplete() {
                    Log.d(TAG, "getQuestions: onComplete called")
                    val isSuccess = mainViewModel.isResponseSuccess

                    if (isSuccess) {
                        moveToQuizFragment()
                    }

                }

                override fun onNext(t: AllQuestions) {
                    Log.d(TAG, "getQuestions: onNext called")

                    /** If response is successful, execute this block */
                    if (t.responseCode == 0) {

                        mainViewModel.setIsResponseSuccess(true)

                        /** Store the combined options list here */
                        var listOfOptions: List<FormattedQuestion> = listOf()

                        /** Mapping the Question model to Formatted Question with combined options list */
                        val question = t.questions.map {

                            /** Combining the incorrectAnswers and correctAnswer in a single list */
                            val combinedOptions = it.incorrectAnswers.plus(it.correctAnswer)

                            val formattedQuestion = FormattedQuestion(
                                category = it.category,
                                correctAnswer = it.correctAnswer,
                                difficulty = it.difficulty,
                                question = it.question,
                                type = it.question,
                                options = combinedOptions
                            )

                            formattedQuestion
                        }

                        /** Setting the new list containing List<FormattedQuestion> */
                        listOfOptions = question

                        // Saving the new list to mainViewModel
                        mainViewModel.setQuestions(listOfOptions)

                    } else {

                        mainViewModel.setIsResponseSuccess(false)

                        if (Utils.mapResponseCodeToMessageString(t.responseCode) != "Success") {
                            Toast.makeText(
                                requireContext(),
                                Utils.mapResponseCodeToMessageString(t.responseCode),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }

            })
    }

    /**
     * On Successfully getting all the questions from the Server, move to Quiz Fragment
     */
    private fun moveToQuizFragment() {
        findNavController().navigate(R.id.action_selectGameDifficultyFragment_to_quizFragment)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}