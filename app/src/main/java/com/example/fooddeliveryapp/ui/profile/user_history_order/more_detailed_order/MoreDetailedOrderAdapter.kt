package com.example.fooddeliveryapp.ui.profile.user_history_order.more_detailed_order

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.example.fooddeliveryapp.R
import com.example.fooddeliveryapp.databinding.RvItemCartBinding
import com.example.fooddeliveryapp.databinding.RvItemMoreDetailedOrderBinding
import com.example.fooddeliveryapp.domain.model.CartProduct
import com.example.fooddeliveryapp.ui.cart.CartChanges

class MoreDetailedOrderAdapter(
) : RecyclerView.Adapter<MoreDetailedOrderHolder>() {

    private val listProduct: MutableList<CartProduct> = mutableListOf()
    private lateinit var holder: MoreDetailedOrderHolder

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoreDetailedOrderHolder {
        val item = RvItemMoreDetailedOrderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MoreDetailedOrderHolder(item)
    }

    override fun getItemCount(): Int = listProduct.size

    override fun onBindViewHolder(holder: MoreDetailedOrderHolder, position: Int) {
        holder.onBind(listProduct[position])
        this.holder = holder
    }

    fun setItems(newListProductData: List<CartProduct>) {
        listProduct.clear()
        listProduct.addAll(newListProductData)
        notifyDataSetChanged()
    }
}