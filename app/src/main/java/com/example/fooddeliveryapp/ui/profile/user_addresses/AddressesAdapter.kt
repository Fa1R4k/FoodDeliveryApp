package com.example.fooddeliveryapp.ui.profile.user_addresses

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.example.fooddeliveryapp.R
import com.example.fooddeliveryapp.databinding.RvItemAddressBinding
import com.example.fooddeliveryapp.domain.model.CartProduct

class AddressesAdapter(
    private val itemCategoryCLick: (String) -> Unit,
) :
    RecyclerView.Adapter<AddressesHolder>() {

    private val addressList: MutableList<String> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddressesHolder {
        val item = RvItemAddressBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AddressesHolder(item, ::updateList)
    }

    override fun getItemCount(): Int = addressList.size

    override fun onBindViewHolder(holder: AddressesHolder, position: Int) {
        holder.onBind(addressList[position])
    }

    private fun updateList(address: String, rowView: View) {
        itemCategoryCLick(address)

        val anim = AnimationUtils.loadAnimation(rowView.context, R.anim.to_right)
        rowView.startAnimation(anim)

        anim.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {}

            override fun onAnimationRepeat(animation: Animation?) {}

            override fun onAnimationEnd(animation: Animation?) {
                addressList.remove(address)
                notifyDataSetChanged()
            }
        })
    }

    fun setItems(newAddressList : MutableList<String>) {
        addressList.clear()
        addressList.addAll(newAddressList)
        notifyDataSetChanged()
    }
}