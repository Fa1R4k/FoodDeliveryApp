package com.example.fooddeliveryapp.ui.profile.user_history_order.recyclerImages

import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.fooddeliveryapp.R
import com.example.fooddeliveryapp.databinding.RvItemImagesBinding
import com.example.fooddeliveryapp.databinding.RvItemOrderHistoryBinding
import com.example.fooddeliveryapp.domain.model.HistoryOrderData

class ImagesHolder(
    private val binding: RvItemImagesBinding,
) : RecyclerView.ViewHolder(binding.root) {
    fun onBind(item: String) {
        setImage(item, binding.ivProduct)
    }

    private fun setImage(url: String, image: ImageView) {
        Glide.with(image).load(url).into(image)
    }
}