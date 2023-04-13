package com.example.fooddeliveryapp.ui.profile.user_addresses

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.fooddeliveryapp.databinding.RvItemAddressBinding

class AddressesHolder(
    private val binding: RvItemAddressBinding,
    private val itemCategoryCLick: (String, View) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {
    fun onBind(item: String) {
        binding.tvAddress.text = item
        binding.ivDelete.setOnClickListener {
            itemCategoryCLick(item, binding.root)
        }
    }
}