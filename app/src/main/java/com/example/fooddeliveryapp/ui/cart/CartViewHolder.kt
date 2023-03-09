package com.example.fooddeliveryapp.ui.cart

import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.fooddeliveryapp.R
import com.example.fooddeliveryapp.domain.ProductInCart
import com.example.fooddeliveryapp.domain.ProductItem

class CartViewHolder(
    itemView: View,
    private val openProductItemClick: (Int) -> Unit,
) :
    RecyclerView.ViewHolder(itemView) {
    fun onBind(item: ProductInCart) {
        val image = itemView.findViewById<ImageView>(R.id.ivProductImage)
        val name = itemView.findViewById<TextView>(R.id.tvProductName)
        val price = itemView.findViewById<TextView>(R.id.tvPrice)
        setImage(item.imageUrl, image)
        name.text = item.name
        price.text = item.prise.toString()
    }

    private fun setImage(url: String, image: ImageView) {
        Glide.with(image)
            .load(url)
            .into(image)
    }
}