package com.roy.surveyheartquiz.ui.fragment
/** By ROY */
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.roy.surveyheartquiz.data.network.model.AllCategory
import com.roy.surveyheartquiz.data.network.model.TriviaCategory
import com.roy.surveyheartquiz.databinding.FragmentSelectCategoryBinding
import com.roy.surveyheartquiz.ui.adapter.CategoryAdapter
import com.roy.surveyheartquiz.ui.interfaces.OnClickInterface
import com.roy.surveyheartquiz.viewmodel.MainViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable

/**
 * [SelectCategoryFragment] is the screen, where the users will select the category
 * The questions of the quiz will be only from this category
 */
class SelectCategoryFragment : Fragment(), OnClickInterface<TriviaCategory> {

    private var _binding: FragmentSelectCategoryBinding? = null
    private val binding get(): FragmentSelectCategoryBinding = _binding!!

    private lateinit var mainViewModel: MainViewModel

    private val mCategoryAdapter: CategoryAdapter by lazy { CategoryAdapter(this) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSelectCategoryBinding.inflate(inflater, container, false)

        mainViewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]

        binding.categoryRv.apply {
            adapter = mCategoryAdapter
            layoutManager = GridLayoutManager(requireContext(), 2)
        }

        mainViewModel.getCategories()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object: Observer<AllCategory> {
            override fun onSubscribe(d: Disposable) {
                Log.d("getCategory", "onSubscribe: Called")
                showProgressBar(true)
            }

            override fun onError(e: Throwable) {
                Log.d("getCategory", "onError: ${e.message}")
            }

            override fun onComplete() {
                Log.d("getCategory", "onComplete: Called")
            }

            override fun onNext(t: AllCategory) {
                mCategoryAdapter.setData(t.categories)
                showProgressBar(false)
                Log.d("getCategory", "getCategory: ${t.categories}")
            }

        })

        return binding.root
    }

    /**
     * @param value is used to determine whether to show the progressBar or not
     * This  function is used to show/hide the progressBar
     */
    private fun showProgressBar(value: Boolean) {
        if(value) {
            binding.progressBar.visibility = View.VISIBLE
            binding.categoryRv.visibility = View.GONE
        } else {
            binding.progressBar.visibility = View.GONE
            binding.categoryRv.visibility = View.VISIBLE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(value: TriviaCategory) {
        val directions = SelectCategoryFragmentDirections
            .actionSelectCategoryFragmentToSelectGameDifficultyFragment(value)

        findNavController().navigate(directions)
    }

}