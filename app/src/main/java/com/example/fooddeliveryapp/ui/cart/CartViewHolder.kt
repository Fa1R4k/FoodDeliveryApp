package com.example.fooddeliveryapp.ui.cart

import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.fooddeliveryapp.R
import com.example.fooddeliveryapp.domain.ProductInCart

class CartViewHolder(
    itemView: View,
    private val addCountForProduct: (Int, String, CART_CHANGES) -> Unit,
    private val cartAdapter: CartAdapter,
) :
    RecyclerView.ViewHolder(itemView) {
    fun onBind(item: ProductInCart) {
        val image = itemView.findViewById<ImageView>(R.id.ivProductImage)
        val name = itemView.findViewById<TextView>(R.id.tvProductName)
        val price = itemView.findViewById<TextView>(R.id.tvPrice)
        val parameter = itemView.findViewById<TextView>(R.id.tvParameter)
        val count = itemView.findViewById<TextView>(R.id.tvProductCount)
        val btnRemove = itemView.findViewById<ImageButton>(R.id.btnRemove)
        val btnAdd = itemView.findViewById<ImageButton>(R.id.btnAdd)
        setImage(item.imageUrl, image)
        name.text = item.name
        parameter.text = item.productParameter
        count.text = item.countProductInCart.toString()
        price.text =
            "${item.prise * item.countProductInCart} ${itemView.resources.getText(R.string.currency)}"


        btnAdd.setOnClickListener { clickAdd(item, count, price) }
        btnRemove.setOnClickListener { clickRemove(item, count, price) }
    }

    private fun clickAdd(item: ProductInCart, count: TextView, price: TextView) {
        if (item.countProductInCart == 99) {
        } else {
            addCountForProduct(item.id,item.productParameter, CART_CHANGES.ADD)
            count.text = item.countProductInCart.toString()
            price.text =
                "${item.prise * item.countProductInCart} ${itemView.resources.getText(R.string.currency)}"
        }
    }

    private fun clickRemove(item: ProductInCart, count: TextView, price: TextView) {
        if (item.countProductInCart == 1) {
            addCountForProduct(item.id, item.productParameter,CART_CHANGES.DELETE)
            cartAdapter.updateList(item, itemView)
        } else {
            addCountForProduct(item.id, item.productParameter,CART_CHANGES.REMOVE)
            count.text = item.countProductInCart.toString()
            price.text =
                "${item.prise * item.countProductInCart} ${itemView.resources.getText(R.string.currency)}"
        }
    }

    private fun setImage(url: String, image: ImageView) {
        Glide.with(image)
            .load(url)
            .into(image)
    }
}