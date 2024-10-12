package com.roy.surveyheartquiz.ui.fragment
/** By ROY */
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.roy.surveyheartquiz.R
import com.roy.surveyheartquiz.databinding.FragmentResultBinding
import com.roy.surveyheartquiz.viewmodel.MainViewModel

/**
 * This is @ResultFragment
 * Here, the final score is displayed to the user along with an animation and score remarks
 */
class ResultFragment : Fragment() {

    private var _binding: FragmentResultBinding? = null
    private val binding get(): FragmentResultBinding = _binding!!

    private lateinit var mainViewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentResultBinding.inflate(inflater, container, false)

        mainViewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]

        val score = mainViewModel.score

        // Setting Animation View and Score Remarks based on the score
        setDataFromScore(score)

        // Return to Welcome Fragment
        binding.returnToHomeBtn.setOnClickListener {
            findNavController().navigate(R.id.action_resultFragment_to_welcomeFragment)
        }

        return binding.root
    }

    /**
     * This function sets the animation view and the score remarks
     * @param score is used to determine which animation and score remarks to be set
     */
    @SuppressLint("SetTextI18n")
    private fun setDataFromScore(score: Int) {
        if (score >= 7) {
            // Show Excellence animation
            isResultGood(true)
            binding.scoreRemarksTv.text = "Congratulations!!"
            binding.scoreRemarksDescTv.text = "You scored $score/15. Not bad.\nKeep nailing!"
        } else {
            // Show Sad animation
            isResultGood(false)
            binding.scoreRemarksTv.text = "Work Hard!!"
            binding.scoreRemarksDescTv.text =
                "You need to work more on your Knowledge. You scored: $score/15"
        }
    }

    /**
     * This function is used to choose the animation file to be loaded and played based on the score
     * @param isGood is used to choose the animation file to be loaded
     */
    private fun isResultGood(isGood: Boolean) {

        binding.resultAnimView.apply {
            if (isGood) {
                setAnimation(R.raw.excel1)
                repeatCount = ValueAnimator.INFINITE
                playAnimation()
            } else {
                setAnimation(R.raw.sad_emoji)
                repeatCount = ValueAnimator.INFINITE
                playAnimation()
            }

        }
    }

}