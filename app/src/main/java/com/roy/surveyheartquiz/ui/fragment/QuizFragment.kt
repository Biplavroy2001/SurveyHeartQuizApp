package com.roy.surveyheartquiz.ui.fragment
/** By ROY */
import android.content.res.ColorStateList
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.forEach
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.chip.Chip
import com.roy.surveyheartquiz.R
import com.roy.surveyheartquiz.data.network.model.FormattedQuestion
import com.roy.surveyheartquiz.databinding.FragmentQuizBinding
import com.roy.surveyheartquiz.util.Utils
import com.roy.surveyheartquiz.viewmodel.MainViewModel

private const val TAG = "QuizFragment"

/**
 * This is @QuizFragment
 * Here, we will show all the quiz questions, and users can answer them.
 */
class QuizFragment : Fragment() {

    private var _binding: FragmentQuizBinding? = null
    private val binding get(): FragmentQuizBinding = _binding!!

    private lateinit var mainViewModel: MainViewModel

    private var currentQuestionCount: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentQuizBinding.inflate(inflater, container, false)

        mainViewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]

        binding.playerNameTv.text = "Hi, ${mainViewModel.playerName}!"

        setQuestions()

        binding.nextBtn.setOnClickListener {

            /** Execute this block only if the USER select any option */
            if(binding.quizOptionsChipGroup.checkedChipId > 0) {
                answerFinalSubmit(true)

                val selectedChipId: Int = binding.quizOptionsChipGroup.checkedChipId
                val correctAnswerChipId: Int = mainViewModel.correctAnswerChipId

                checkCorrectAnswer(selectedChipId, correctAnswerChipId)
            } else {
                /** Execute this block if no option is selected by the USER */
                Toast.makeText(
                    requireContext(),
                    "Please select an option to continue",
                    Toast.LENGTH_SHORT
                ).show()
            }

        }

        binding.moveToNextBtn.setOnClickListener {

            if (mainViewModel.currentQuestionCount < 15) {
                // Set next question if Total Question count not achieved
                setQuestions()
            } else {
                findNavController().navigate(R.id.action_quizFragment_to_resultFragment)
            }
        }

        return binding.root
    }

    /**
     * This function checks if the selected answer is the correct answer
     * and change the backgroundColor of the selected and correctChip to GREEN and RED respectively.
     * @param selectedChipId is the chip selected by the USER
     * @param correctAnswerChipId is the chip which is the CORRECT ANSWER
     */
    private fun checkCorrectAnswer(selectedChipId: Int, correctAnswerChipId: Int) {

        val selectedChip = binding.quizOptionsChipGroup.findViewById<Chip>(selectedChipId)
        val correctChip = binding.quizOptionsChipGroup.findViewById<Chip>(correctAnswerChipId)

        if (selectedChipId == correctAnswerChipId) {

            selectedChip.chipBackgroundColor = ColorStateList.valueOf(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.green
                )
            )

            selectedChip.setTextColor(
                ColorStateList.valueOf(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.white
                    )
                )
            )

            /**
             * Updating the Player Score:
             * ONLY IF THE USER SELECTED THE CORRECT OPTION/ANSWER
             */
            mainViewModel.setPlayerScore(mainViewModel.score + 1)

        } else {

            selectedChip.chipBackgroundColor = ColorStateList.valueOf(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.red
                )
            )

            selectedChip.setTextColor(
                ColorStateList.valueOf(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.white
                    )
                )
            )

            correctChip.chipBackgroundColor = ColorStateList.valueOf(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.green
                )
            )

            correctChip.setTextColor(
                ColorStateList.valueOf(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.white
                    )
                )
            )
        }

        /** Updating the question count */
        mainViewModel.setCurrentQuestionCount(currentQuestionCount + 1)

    }

    /**
     * This function sets the Question and Its OPTIONS, and Quiz Progress
     * Also handles the chipBackgroundColor rest operation
     */
    private fun setQuestions() {

        // Before setting questions, reset the Chip Background Color
        resetChipBackgroundColor()

        // Reset the checked Chip before setting questions
        binding.quizOptionsChipGroup.clearCheck()

        answerFinalSubmit(false)

        currentQuestionCount = mainViewModel.currentQuestionCount
        val currentQuestion: FormattedQuestion = mainViewModel.questions[currentQuestionCount]

        binding.apply {

            /** Randomly shuffling options list */
            val shuffledOptions = currentQuestion.options.shuffled()

            // Getting the IDs of all the options Chip
            val optionChipIds: List<Int> = listOf(
                binding.option1Chip.id,
                binding.option2Chip.id,
                binding.option3Chip.id,
                binding.option4Chip.id,
            )

            if (Build.VERSION.SDK_INT >= 24) {
                // Setting Question
                questionTv.text =
                    Html.fromHtml(currentQuestion.question, Html.FROM_HTML_MODE_LEGACY)

                var count = 0

                shuffledOptions.forEach {

                    // Setting the correct answer chip resource ID
                    if(it == currentQuestion.correctAnswer) {
                        mainViewModel.setCorrectAnswerChipId(optionChipIds[count])
                    }

                    // Setting Options
                    quizOptionsChipGroup
                        .findViewById<Chip>(optionChipIds[count++])
                        .text = Html.fromHtml(it, Html.FROM_HTML_MODE_LEGACY)

                }

            } else {
                // Setting Question
                questionTv.text = Html.fromHtml(currentQuestion.question)

                var count = 0

                shuffledOptions.forEach {

                    // Setting the correct answer chip resource ID
                    if(it == currentQuestion.correctAnswer) {
                        mainViewModel.setCorrectAnswerChipId(optionChipIds[count])
                    }

                    // Setting Options
                    quizOptionsChipGroup
                        .findViewById<Chip>(optionChipIds[count++])
                        .text = Html.fromHtml(it)
                }
            }


            // Setting Question ProgressBar
            questionProgressBar.progress = Utils.calculateQuizProgress(currentQuestionCount + 1)
            questionProgressTv.text = "${currentQuestionCount + 1}/15"
        }
    }

    /**
     * This function is used to check if the USER is performing final submit of the quiz
     * @param value is used to handle the visibility of nextBtn and moveToNextQuestionBtn
     */
    private fun answerFinalSubmit(value: Boolean) {
        if (value) {
            binding.nextBtn.isEnabled = false
            binding.moveToNextBtn.isEnabled = true

            disableOptions(true)

        } else {
            binding.nextBtn.isEnabled = true
            binding.moveToNextBtn.isEnabled = false

            disableOptions(false)
        }
    }

    /**
     * On Pressing nextBtn,
     * This function disables the option chips, so that the altering of answers is not possible
     * @param value is used to handle the enabling/disabling operation of OPTION CHIPS
     */
    private fun disableOptions(value: Boolean) {
        if (value) {
            // Disable ChipGroup and Chips
            binding.quizOptionsChipGroup.apply {
                this.forEach {
                    it.isEnabled = false
                }
            }
        } else {
            // Enable ChipGroup and Chips
            binding.quizOptionsChipGroup.apply {
                this.forEach {
                    it.isEnabled = true
                }
            }
        }
    }

    /**
     * This function resets the chipBackgroundColor while setting new questions
     */
    private fun resetChipBackgroundColor() {

        // Option 1 Chip
        binding.option1Chip.chipBackgroundColor = ColorStateList.valueOf(
            ContextCompat.getColor(
                requireContext(),
                R.color.default_chip_background_color
            )
        )

        binding.option1Chip.setTextColor(
            ColorStateList.valueOf(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.black
                )
            )
        )

        // Option 2 Chip
        binding.option2Chip.chipBackgroundColor = ColorStateList.valueOf(
            ContextCompat.getColor(
                requireContext(),
                R.color.default_chip_background_color
            )
        )

        binding.option2Chip.setTextColor(
            ColorStateList.valueOf(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.black
                )
            )
        )

        // Option 3 Chip
        binding.option3Chip.chipBackgroundColor = ColorStateList.valueOf(
            ContextCompat.getColor(
                requireContext(),
                R.color.default_chip_background_color
            )
        )

        binding.option3Chip.setTextColor(
            ColorStateList.valueOf(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.black
                )
            )
        )

        // Option 4 Chip

        binding.option4Chip.chipBackgroundColor = ColorStateList.valueOf(
            ContextCompat.getColor(
                requireContext(),
                R.color.default_chip_background_color
            )
        )

        binding.option4Chip.setTextColor(
            ColorStateList.valueOf(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.black
                )
            )
        )

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}