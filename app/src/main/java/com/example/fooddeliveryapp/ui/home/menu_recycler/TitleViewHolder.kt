package com.example.fooddeliveryapp.ui.home.menu_recycler

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.fooddeliveryapp.R
import com.example.fooddeliveryapp.domain.ProductItem

class TitleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun onBind(item: ProductItem.ProductTitleItem) {
        itemView.findViewById<TextView>(R.id.text).text = item.title
    }
}