package com.roy.surveyheartquiz.ui.adapter
/** By ROY */
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.roy.surveyheartquiz.data.network.model.TriviaCategory
import com.roy.surveyheartquiz.databinding.CategoryItemBinding
import com.roy.surveyheartquiz.ui.interfaces.OnClickInterface
import com.roy.surveyheartquiz.util.TriviaDiffUtil

private const val TAG = "CategoryAdapter"

/**
 * This is the RecyclerView adapter used by the @SelectCategoryFragment
 * It accepts @onClickInterface as a parameter, through which current CategoryItem is passed
 */
class CategoryAdapter (
    private val onClickInterface: OnClickInterface<TriviaCategory>
): RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    private var categories: List<TriviaCategory> = mutableListOf<TriviaCategory>()

    inner class CategoryViewHolder(val binding: CategoryItemBinding)
        : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = CategoryItemBinding.inflate(inflater, parent, false)
        return CategoryViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return categories.size
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val currentCategory = categories[position]

        holder.binding.categoryNameTv.text = currentCategory.name
        holder.itemView.setOnClickListener {
            Log.d(TAG, "Category Item Clicked: $currentCategory")
            onClickInterface.onClick(currentCategory)
        }
    }

    /**
     * For effective list optimization, DiffUtil is used to prevent wastage resources
     */
    fun setData(newList: List<TriviaCategory>) {
        val triviaDiffUtil = TriviaDiffUtil(categories, newList)
        val diffUtilResult = DiffUtil.calculateDiff(triviaDiffUtil)
        categories = newList
        diffUtilResult.dispatchUpdatesTo(this)
    }

}