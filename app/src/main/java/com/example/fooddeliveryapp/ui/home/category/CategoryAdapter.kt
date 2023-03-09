package com.example.fooddeliveryapp.ui.home.category

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet.Constraint
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.fooddeliveryapp.R
import com.example.fooddeliveryapp.domain.CategoryData
import kotlinx.android.synthetic.main.rv_item_category.view.*

class CategoryAdapter(
    private val categoryList: MutableList<CategoryData>,
    private val itemCategoryCLick: (String) -> Unit,
) :
    RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {
    private var isNewRadioButtonChecked = false
    private var lastCheckedPosition = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.rv_item_category, parent, false)
        return CategoryViewHolder(view, itemCategoryCLick)
    }

    override fun getItemCount(): Int = categoryList.size

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.onBind(categoryList[position])
    }

    inner class CategoryViewHolder(
        itemView: View,
        private val itemCategoryCLick: (String) -> Unit,
    ) : RecyclerView.ViewHolder(itemView) {
        fun onBind(item: CategoryData) {
            val categoryRadioButton = itemView.findViewById<Button>(R.id.rbCategory)

            if (isNewRadioButtonChecked) {
                itemView.rbCategory.isChecked = categoryList[position].isSelected
            } else {
                if (adapterPosition == 0) {
                    itemView.rbCategory.isChecked = true
                    isNewRadioButtonChecked = true
                }
            }
            categoryRadioButton.text = item.categoryName

            categoryRadioButton.setOnClickListener {
                handleRadioButtonChecks(adapterPosition)
                itemCategoryCLick(item.categoryName)
            }
        }

        private fun handleRadioButtonChecks(adapterPosition: Int) {
            categoryList[lastCheckedPosition].isSelected = false
            categoryList[adapterPosition].isSelected = true
            notifyItemChanged(adapterPosition)
            notifyItemChanged(lastCheckedPosition)
            lastCheckedPosition = adapterPosition

        }
    }
}