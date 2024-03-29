package com.example.fooddeliveryapp.ui.home.category

import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.example.fooddeliveryapp.R
import com.example.fooddeliveryapp.databinding.RvItemCategoryBinding
import com.example.fooddeliveryapp.domain.model.CategoryData

class CategoryViewHolder(
    private val binding: RvItemCategoryBinding,
    private val itemCategoryCLick: (String) -> Unit,
    private val handleRadioButtonChecks: (Int) -> Unit,
    private val isNewRadioButtonChecked: () -> Boolean,
    private val setNewRadioButton: (Boolean) -> Unit,
    private val getCategoryFromList: (Int) -> Boolean,
) : RecyclerView.ViewHolder(binding.root) {
    fun onBind(item: CategoryData) {
        if (isNewRadioButtonChecked()) {
            binding.rbCategory.isChecked = getCategoryFromList(adapterPosition)
        } else {
            if (adapterPosition == 0) {
                binding.rbCategory.isChecked = true
                setNewRadioButton(true)
            }
        }
        with(itemView.findViewById<Button>(R.id.rbCategory)) {
            text = item.categoryName
            setOnClickListener {
                handleRadioButtonChecks(adapterPosition)
                itemCategoryCLick(item.categoryName)
            }
        }
    }
}