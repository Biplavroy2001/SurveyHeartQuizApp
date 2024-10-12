package com.roy.surveyheartquiz.ui.fragment
/** By ROY */
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.roy.surveyheartquiz.R
import com.roy.surveyheartquiz.databinding.FragmentWelcomeBinding
import com.roy.surveyheartquiz.viewmodel.MainViewModel

/**
 * This is @WelcomeFragment
 * Here, the user enters a player name before proceeding to the Quiz
 */
class WelcomeFragment : Fragment() {

    private var _binding: FragmentWelcomeBinding? = null
    private val binding get(): FragmentWelcomeBinding = _binding!!

    private lateinit var mainViewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentWelcomeBinding.inflate(inflater, container, false)

        mainViewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]

        /** Reset currentQuestionCount to 0 (the fragment might have been created after returning from ResultFragment) */
        mainViewModel.setCurrentQuestionCount(0)

        binding.playerNameEdt.editText?.addTextChangedListener {
            if(it?.trim()?.isNotEmpty() == true) {
                binding.playerNameEdt.error = ""
            }
        }

        binding.playQuizBtn.setOnClickListener {
            val playerName = binding.playerNameEdt.editText?.text.toString()
            val isSuccess = mainViewModel.setPlayerName(playerName)
            if(!isSuccess) {
                binding.playerNameEdt.error = "Please enter a valid name"
            } else {
                Toast.makeText(
                    requireContext(),
                    "Success! Starting Quiz",
                    Toast.LENGTH_SHORT
                ).show()

                Log.d("WelcomeFragment", "PlayerName: ${mainViewModel.playerName}")

                // Move to Quiz Category Fragment
                findNavController().navigate(R.id.action_welcomeFragment_to_selectCategoryFragment)
            }
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}