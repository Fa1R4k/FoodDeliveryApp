package com.example.fooddeliveryapp.ui.home.menu_recycler

import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.fooddeliveryapp.R
import com.example.fooddeliveryapp.databinding.RvItemProductBinding
import com.example.fooddeliveryapp.domain.model.ProductItem

class MenuViewHolder(
    private val binding: RvItemProductBinding,
    private val openProductItemClick: (Int) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {
    fun onBind(item: ProductItem.ProductData) {
        setImage(item.imageUrl, binding.ivProductImage)
        binding.tvProductName.text = item.name
        binding.tvProductDescription.text = item.description
        var price = "0"
        var isItOne = false
        try {
            price = item.price.values.first().toString()
            isItOne = item.price.values.size == 1
        } catch (_: Exception) {
        }
        if (isItOne) {
            binding.btnPrice.text = itemView.resources.getString(R.string.price_all_currency, price)
        } else {
            binding.btnPrice.text = itemView.resources.getString(R.string.from_prise_currency, price)
        }

        binding.btnPrice.setOnClickListener { openProductItemClick(item.id) }
        itemView.setOnClickListener { openProductItemClick(item.id) }
    }
}

private fun setImage(url: String, image: ImageView) {
    Glide.with(image).load(url).into(image)
}