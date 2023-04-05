package com.example.fooddeliveryapp.ui.profile.menu_recycler

import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.fooddeliveryapp.databinding.RvItemProfileBinding
import com.example.fooddeliveryapp.domain.model.User

class ProfileMenuViewHolder(
    private val binding: RvItemProfileBinding,
    private val user: User,
    private val openUserItemClick: (String) -> Unit,
    private val NAME_MAX_LENGTH: Int = 15
) : RecyclerView.ViewHolder(binding.root) {
    fun onBind(item: HashMap<String, String>) {
        if (item.contains("UserName")) {
            binding.root.setOnClickListener {
                openUserItemClick(user.id)
            }
            val userName = item["UserName"]
            binding.imageView.isVisible = true
            binding.userName.text = if ((userName?.length ?: 0) < NAME_MAX_LENGTH) userName
            else userName?.split(" ")?.first()
        } else if (item.contains("История заказов")) {
            binding.title.text = "История\nзаказов"
            binding.count.text = item["История заказов"]
        } else if (item.contains("Адрес доставки")) {
            binding.title.text = "Адрес\nдоставки"
            binding.count.text = item["Адрес доставки"]
        }
    }
}