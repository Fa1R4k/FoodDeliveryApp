package com.example.fooddeliveryapp.ui.home.menu_recycler

import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.fooddeliveryapp.R
import com.example.fooddeliveryapp.domain.ProductItem

class MenuViewHolder(
    itemView: View,
    private val openProductItemClick: (Int) -> Unit,
) :
    RecyclerView.ViewHolder(itemView) {
    fun onBind(item: ProductItem.ProductData) {
        val image = itemView.findViewById<ImageView>(R.id.ivProductImage)
        val name = itemView.findViewById<TextView>(R.id.tvProductName)
        val description = itemView.findViewById<TextView>(R.id.tvProductDescription)
        val btnPrise = itemView.findViewById<Button>(R.id.btnPrise)
        setImage(item.imageUrl, image)
        name.text = item.name
        description.text = item.description
        var prise = "0.0"
        try {
            prise = item.prise.values.first().toString()
        } catch (_: Exception){}
        btnPrise.text = "${itemView.resources.getText(R.string.min_price)} $prise ${itemView.resources.getText(R.string.currency)}"

        itemView.setOnClickListener {
            openProductItemClick(item.id)
        }
    }

    private fun setImage(url: String, image: ImageView) {
        Glide.with(image)
            .load(url)
            .into(image)
    }
}