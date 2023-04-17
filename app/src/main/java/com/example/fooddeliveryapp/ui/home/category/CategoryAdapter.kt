package com.example.fooddeliveryapp.ui.home.category

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fooddeliveryapp.databinding.RvItemCategoryBinding
import com.example.fooddeliveryapp.domain.model.CategoryData

class CategoryAdapter(
    private val itemCategoryClick: (String) -> Unit,
) : RecyclerView.Adapter<CategoryViewHolder>() {

    private val categoryList: MutableList<CategoryData> = mutableListOf()
    private var isNewRadioButtonChecked = false
    private var lastCheckedPosition = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val itemBinding =
            RvItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryViewHolder(
            itemBinding,
            itemCategoryClick,
            this::handleRadioButtonChecks,
            this::getStateNewRadioButtonChecked,
            this::setNewRadioButton,
            this::getIsSelectedByPosition
        )
    }

    override fun getItemCount(): Int = categoryList.size

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.onBind(categoryList[position])
    }

    private fun handleRadioButtonChecks(adapterPosition: Int) {
        if (categoryList.isNotEmpty()) {
            categoryList[lastCheckedPosition].isSelected = false
            categoryList[adapterPosition].isSelected = true
            notifyItemChanged(adapterPosition)
            notifyItemChanged(lastCheckedPosition)
            lastCheckedPosition = adapterPosition
        }
    }

    private fun setNewRadioButton(checked: Boolean) {
        isNewRadioButtonChecked = checked
    }

    fun setItems(newList: MutableList<CategoryData>) {
        categoryList.clear()
        categoryList.addAll(newList)
        handleRadioButtonChecks(lastCheckedPosition)
        notifyDataSetChanged()
    }

    fun setSelectedItem(categoryName: String): Int {
        val position =
            categoryList.indexOfFirst { categoryData -> categoryData.categoryName == categoryName }
        handleRadioButtonChecks(position)
        return position
    }

    private fun getIsSelectedByPosition(position: Int): Boolean = categoryList[position].isSelected

    private fun getStateNewRadioButtonChecked() = isNewRadioButtonChecked
}
