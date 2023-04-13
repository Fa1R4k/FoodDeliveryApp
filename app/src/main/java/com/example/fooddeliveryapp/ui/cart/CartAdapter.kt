package com.example.fooddeliveryapp.ui.cart

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.example.fooddeliveryapp.R
import com.example.fooddeliveryapp.databinding.RvItemCartBinding
import com.example.fooddeliveryapp.domain.model.CartProduct

class CartAdapter(
    private val addCountForProduct: (Int, String, CartChanges) -> Unit,
) : RecyclerView.Adapter<CartHolder>() {

    private val listProduct: MutableList<CartProduct> = mutableListOf()
    private lateinit var holder: CartHolder

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartHolder {
        val item = RvItemCartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartHolder(item, addCountForProduct, this)
    }

    override fun getItemCount(): Int = listProduct.size

    override fun onBindViewHolder(holder: CartHolder, position: Int) {
        holder.onBind(listProduct[position])
        this.holder = holder
    }

    fun updateList(productInCart: CartProduct, rowView: View) {
        val anim = AnimationUtils.loadAnimation(rowView.context, R.anim.to_right)
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

    fun setItems(newListProductData: List<CartProduct>) {
        listProduct.clear()
        listProduct.addAll(newListProductData)
        notifyDataSetChanged()
    }
}