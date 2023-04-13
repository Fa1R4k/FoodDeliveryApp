package com.example.fooddeliveryapp.ui.profile.user_history_order

import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fooddeliveryapp.R
import com.example.fooddeliveryapp.databinding.RvItemOrderHistoryBinding
import com.example.fooddeliveryapp.domain.model.HistoryOrderData
import com.example.fooddeliveryapp.ui.profile.user_history_order.recyclerImages.ImagesAdapter

class OrderHistoryHolder(
    private val binding: RvItemOrderHistoryBinding,
    private val fragmentActivity: FragmentActivity
) : RecyclerView.ViewHolder(binding.root) {
    fun onBind(item: HistoryOrderData) {
        binding.tvOrderTime.text = item.orderTime
        binding.tvOrderPrice.text = "${
            String.format(
                "%.2f", item.orderPrice
            )
        } ${itemView.resources.getText(R.string.currency)}"

        binding.rvImages.adapter = ImagesAdapter(item.cartProducts.map { it.imageUrl }.toList())
        binding.rvImages.layoutManager = LinearLayoutManager(
            fragmentActivity, LinearLayoutManager.HORIZONTAL, false
        )
    }
}