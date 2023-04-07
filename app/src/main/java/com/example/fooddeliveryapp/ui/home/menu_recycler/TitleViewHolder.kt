package com.example.fooddeliveryapp.ui.home.menu_recycler
import androidx.recyclerview.widget.RecyclerView
import com.example.fooddeliveryapp.databinding.TextItemBinding
import com.example.fooddeliveryapp.domain.model.ProductItem

class TitleViewHolder(private val binding: TextItemBinding) : RecyclerView.ViewHolder(binding.root) {

    fun onBind(item: ProductItem.ProductTitleItem) {
        binding.tvTitle.text = item.title
    }
}