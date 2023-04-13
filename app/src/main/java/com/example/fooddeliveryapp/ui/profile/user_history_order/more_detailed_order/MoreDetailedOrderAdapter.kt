package com.example.fooddeliveryapp.ui.profile.user_history_order.more_detailed_order

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fooddeliveryapp.databinding.RvItemMoreDetailedOrderBinding
import com.example.fooddeliveryapp.domain.model.CartProduct

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