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
        var price = "0.0"
        try {
            price = item.price.values.first().toString()
        } catch (_: Exception) {
        }
        binding.btnPrice.text =
            "${itemView.resources.getText(R.string.min_price)} $price ${itemView.resources.getText(R.string.currency)}"

        binding.btnPrice.setOnClickListener { openProductItemClick(item.id) }
        itemView.setOnClickListener { openProductItemClick(item.id) }
    }

    private fun setImage(url: String, image: ImageView) {
        Glide.with(image).load(url).into(image)
    }
}