package com.example.fooddeliveryapp.ui.home.category

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fooddeliveryapp.databinding.RvItemCategoryBinding
import com.example.fooddeliveryapp.domain.CategoryData

class CategoryAdapter(
    private val categoryList: MutableList<CategoryData>,
    private val itemCategoryCLick: (String) -> Unit,
) :
    RecyclerView.Adapter<CategoryViewHolder>() {
    private var isNewRadioButtonChecked = false
    private var lastCheckedPosition = 0


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val item = RvItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryViewHolder(item,
            itemCategoryCLick,
            ::handleRadioButtonChecks,
            ::getStateNewRadioButtonChecked,
            ::setNewRadioButton,
            ::getIsSelectedByPosition)
    }

    override fun getItemCount(): Int = categoryList.size

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.onBind(categoryList[position])
    }

    private fun handleRadioButtonChecks(adapterPosition: Int) {
        categoryList[lastCheckedPosition].isSelected = false
        categoryList[adapterPosition].isSelected = true
        notifyItemChanged(adapterPosition)
        notifyItemChanged(lastCheckedPosition)
        lastCheckedPosition = adapterPosition
    }

    private fun setNewRadioButton(checked: Boolean) {
        isNewRadioButtonChecked = checked
    }

    private fun getIsSelectedByPosition(position: Int): Boolean = categoryList[position].isSelected

    private fun getStateNewRadioButtonChecked() = isNewRadioButtonChecked
}