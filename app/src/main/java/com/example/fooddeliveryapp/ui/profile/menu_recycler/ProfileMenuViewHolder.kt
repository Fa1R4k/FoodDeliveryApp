package com.example.fooddeliveryapp.ui.profile.menu_recycler

import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.fooddeliveryapp.databinding.RvItemProfileBinding
import com.example.fooddeliveryapp.domain.model.User

class ProfileMenuViewHolder(
    private val binding: RvItemProfileBinding,
    private val user: User,
    private val openUserItemClick: (String) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {
    fun onBind(item: Pair<String, String>) {
        when (item.first) {
            "UserName" -> {
                binding.root.setOnClickListener {
                    openUserItemClick(user.id)
                }
                binding.imageView.isVisible = true
                binding.userName.text = item.second
            }
            "История заказов" -> {
                binding.title.text = "История\nзаказов"
                binding.count.text = item.second
            }
            "Адрес доставки" -> {
                binding.title.text = "Адрес\nдоставки"
                binding.count.text = item.second
            }
        }
    }
}