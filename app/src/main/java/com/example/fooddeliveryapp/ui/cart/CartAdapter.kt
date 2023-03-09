package com.example.fooddeliveryapp.ui.cart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fooddeliveryapp.R
import com.example.fooddeliveryapp.domain.ProductInCart
import com.example.fooddeliveryapp.domain.ProductItem
import java.lang.Exception

class CartAdapter(
    private val listProductData: List<ProductInCart>,
    private val onClick: (Int) -> Unit,
) : RecyclerView.Adapter<CartViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rv_item_cart, parent, false)
        return CartViewHolder(view, onClick)
    }


    override fun getItemCount(): Int = listProductData.size

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.onBind(listProductData[position])
    }


}