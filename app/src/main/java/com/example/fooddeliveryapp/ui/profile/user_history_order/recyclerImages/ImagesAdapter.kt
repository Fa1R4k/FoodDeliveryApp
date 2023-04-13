package com.example.fooddeliveryapp.ui.profile.user_history_order.recyclerImages

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fooddeliveryapp.databinding.RvItemImagesBinding
import com.example.fooddeliveryapp.databinding.RvItemOrderHistoryBinding
import com.example.fooddeliveryapp.domain.model.HistoryOrderData

class ImagesAdapter(
    private val addressList: List<String>
) :
    RecyclerView.Adapter<ImagesHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImagesHolder {
        val item =
            RvItemImagesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ImagesHolder(item)
    }

    override fun getItemCount(): Int = addressList.size

    override fun onBindViewHolder(holder: ImagesHolder, position: Int) {
        holder.onBind(addressList[position])
    }
}