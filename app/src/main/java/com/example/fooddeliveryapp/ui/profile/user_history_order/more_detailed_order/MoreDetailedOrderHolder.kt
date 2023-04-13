package com.example.fooddeliveryapp.ui.profile.user_history_order.more_detailed_order

import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.fooddeliveryapp.R
import com.example.fooddeliveryapp.databinding.RvItemCartBinding
import com.example.fooddeliveryapp.databinding.RvItemMoreDetailedOrderBinding
import com.example.fooddeliveryapp.domain.model.CartProduct
import com.example.fooddeliveryapp.ui.cart.CartChanges

class MoreDetailedOrderHolder(
    private val binding: RvItemMoreDetailedOrderBinding,
) : RecyclerView.ViewHolder(binding.root) {
    fun onBind(item: CartProduct) {
        updateProductUI(item)
    }

    private fun updateProductUI(item: CartProduct) {
        with(binding) {
            setImage(item.imageUrl, ivProductImage)
            tvProductName.text = item.name
            tvParameter.text = item.productParameter
            tvProductCount.text = item.countProductInCart.toString()
            tvPrice.text =
                "${
                    String.format(
                        "%.2f",
                        item.prise * item.countProductInCart
                    )
                } ${itemView.resources.getText(R.string.currency)}"
        }
    }


    private fun setImage(url: String, image: ImageView) {
        Glide.with(image)
            .load(url)
            .into(image)
    }
}