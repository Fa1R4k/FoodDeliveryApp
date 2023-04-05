package com.example.fooddeliveryapp.ui.profile.menu_recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fooddeliveryapp.databinding.RvItemProfileBinding
import com.example.fooddeliveryapp.domain.model.User

class ProfileMenuAdapter(
    private val categoryList: MutableList<HashMap<String, String>>,
    private val user: User,
    private val openUserItemClick: (String) -> Unit,
) :
    RecyclerView.Adapter<ProfileMenuViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileMenuViewHolder {
        val item = RvItemProfileBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProfileMenuViewHolder(item,user,openUserItemClick)
    }

    override fun getItemCount(): Int = categoryList.size

    override fun onBindViewHolder(holder: ProfileMenuViewHolder, position: Int) {
        holder.onBind(categoryList[position])
    }
}