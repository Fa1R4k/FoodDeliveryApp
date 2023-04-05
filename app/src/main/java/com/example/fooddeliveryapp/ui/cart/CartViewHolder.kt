package com.example.fooddeliveryapp.ui.cart

import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.fooddeliveryapp.R
import com.example.fooddeliveryapp.databinding.RvItemCartBinding
import com.example.fooddeliveryapp.domain.model.CartProduct

class CartViewHolder(
    private val binding: RvItemCartBinding,
    private val addCountForProduct: (Int, String, CartChanges) -> Unit,
    private val cartAdapter: CartAdapter,
) : RecyclerView.ViewHolder(binding.root) {
    fun onBind(item: CartProduct) {
        this.binding.btnRemove.isEnabled = true
        updateProductUI(item)
        setUpListeners(item)
    }

    private fun updateProductUI(item: CartProduct) {
        with(binding) {
            setImage(item.imageUrl, ivProductImage)
            tvProductName.text = item.name
            tvParameter.text = item.productParameter
            tvProductCount.text = item.countProductInCart.toString()
            tvPrice.text =
                "${
                    String.format("%.2f",
                        item.prise * item.countProductInCart)
                } ${itemView.resources.getText(R.string.currency)}"
        }
    }

    private fun setUpListeners(item: CartProduct) {
        with(binding) {
            btnAdd.setOnClickListener {
                clickAdd(item,
                    binding.tvProductCount,
                    binding.tvPrice)
            }
            btnRemove.setOnClickListener {
                clickRemove(item,
                    binding.tvProductCount,
                    binding.tvPrice)
            }
        }
    }

    private fun clickAdd(item: CartProduct, count: TextView, price: TextView) {
        if (item.countProductInCart != 99) {
            addCountForProduct(item.id, item.productParameter, CartChanges.ADD)
            count.text = item.countProductInCart.toString()
            price.text =
                "${
                    String.format("%.2f",
                        item.prise * item.countProductInCart)
                } ${itemView.resources.getText(R.string.currency)}"
        }
    }

    private fun clickRemove(item: CartProduct, count: TextView, price: TextView) {
        if (item.countProductInCart == 1) {
            addCountForProduct(item.id, item.productParameter, CartChanges.DELETE)
            cartAdapter.updateList(item, itemView)
            this.binding.btnRemove.isEnabled = false
        } else {
            addCountForProduct(item.id, item.productParameter, CartChanges.REMOVE)
            count.text = item.countProductInCart.toString()
            price.text =
                "${
                    String.format("%.2f",
                        item.prise * item.countProductInCart)
                } ${itemView.resources.getText(R.string.currency)}"
        }
    }

    private fun setImage(url: String, image: ImageView) {
        Glide.with(image)
            .load(url)
            .into(image)
    }
}