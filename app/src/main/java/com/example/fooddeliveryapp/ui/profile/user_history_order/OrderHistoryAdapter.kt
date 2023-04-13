package com.example.fooddeliveryapp.ui.profile.user_history_order

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.fooddeliveryapp.R
import com.example.fooddeliveryapp.databinding.RvItemAddressBinding
import com.example.fooddeliveryapp.databinding.RvItemOrderHistoryBinding
import com.example.fooddeliveryapp.domain.model.HistoryOrderData

class OrderHistoryAdapter(
    private val addressList: List<HistoryOrderData>,
    private val itemCategoryCLick: (String) -> Unit
) :
    RecyclerView.Adapter<OrderHistoryHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderHistoryHolder {
        val item =
            RvItemOrderHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OrderHistoryHolder(item,itemCategoryCLick)
    }

    override fun getItemCount(): Int = addressList.size

    override fun onBindViewHolder(holder: OrderHistoryHolder, position: Int) {
        holder.onBind(addressList[position])
    }
}