package com.example.fooddeliveryapp.ui.profile.user_history_order

import android.widget.ImageView
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.fooddeliveryapp.R
import com.example.fooddeliveryapp.databinding.RvItemOrderHistoryBinding
import com.example.fooddeliveryapp.domain.model.HistoryOrderData

class OrderHistoryHolder(
    private val binding: RvItemOrderHistoryBinding,
    private val itemCategoryCLick: (String) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {
    fun onBind(item: HistoryOrderData) {
        binding.tvOrderTime.text = item.orderTime
        binding.tvOrderPrice.text = itemView.resources.getString(
            R.string.price_all_currency, String.format("%.2f", item.orderPrice)
        )

        item.cartProducts.map {
            binding.llForImage.addView(
                addImageView(it.imageUrl)
            )
        }
        binding.tvNavigateToMoreDetail.setOnClickListener { itemCategoryCLick(item.orderTime) }
    }

    private fun addImageView(imageUrl: String): ImageView {
        val newImageView = ImageView(binding.root.context)
        setImage(imageUrl, newImageView)
        newImageView.layoutParams = LinearLayout.LayoutParams(
            SIZE_FOR_IMAGE,
            SIZE_FOR_IMAGE
        )
        return newImageView
    }

    private fun setImage(url: String, image: ImageView) {
        Glide.with(image)
            .load(url)
            .into(image)
    }

    companion object {
        private const val SIZE_FOR_IMAGE = 200
    }
}