package com.example.fooddeliveryapp.ui.cart

import android.animation.AnimatorListenerAdapter
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.example.fooddeliveryapp.R
import com.example.fooddeliveryapp.domain.ProductInCart

class CartAdapter(
    listProductData: List<ProductInCart>,
    private val addCountForProduct: (Int,String, CART_CHANGES) -> Unit,
) : RecyclerView.Adapter<CartViewHolder>() {

    private val listProduct = listProductData.toMutableList()
    private lateinit var holder: CartViewHolder

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rv_item_cart, parent, false)
        return CartViewHolder(view, addCountForProduct, this)
    }


    override fun getItemCount(): Int = listProduct.size

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.onBind(listProduct[position])
        this.holder = holder
    }

    fun updateList(productInCart: ProductInCart, rowView: View) {
        val anim = AnimationUtils.loadAnimation(rowView.context, R.anim.slide_out_right)
        anim.duration = 300
        rowView.startAnimation(anim)

        anim.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {}

            override fun onAnimationRepeat(animation: Animation?) {}

            override fun onAnimationEnd(animation: Animation?) {
                listProduct.remove(productInCart)
                notifyDataSetChanged()
            }
        })
    }

}