package com.example.fooddeliveryapp.ui.cart

import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.fooddeliveryapp.R
import com.example.fooddeliveryapp.databinding.RvItemCartBinding
import com.example.fooddeliveryapp.domain.ProductInCart

class CartViewHolder(
    private val binding: RvItemCartBinding,
    private val addCountForProduct: (Int, String, CART_CHANGES) -> Unit,
    private val cartAdapter: CartAdapter,
) : RecyclerView.ViewHolder(binding.root) {
    fun onBind(item: ProductInCart) {
        updateProductUI(item)
        setUpListeners(item)
    }

    private fun updateProductUI(item: ProductInCart) {
        with(binding) {
            setImage(item.imageUrl, ivProductImage)
            tvProductName.text = item.name
            tvParameter.text = item.productParameter
            tvProductCount.text = item.countProductInCart.toString()
            tvPrice.text =
                "${item.prise * item.countProductInCart} ${itemView.resources.getText(R.string.currency)}"
        }
    }

    private fun setUpListeners(item: ProductInCart) {
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

    private fun clickAdd(item: ProductInCart, count: TextView, price: TextView) {
        if (item.countProductInCart != 99) {
            addCountForProduct(item.id, item.productParameter, CART_CHANGES.ADD)
            count.text = item.countProductInCart.toString()
            price.text =
                "${item.prise * item.countProductInCart} ${itemView.resources.getText(R.string.currency)}"
        }
    }

    private fun clickRemove(item: ProductInCart, count: TextView, price: TextView) {
        if (item.countProductInCart == 1) {
            addCountForProduct(item.id, item.productParameter, CART_CHANGES.DELETE)
            cartAdapter.updateList(item, itemView)
        } else {
            addCountForProduct(item.id, item.productParameter, CART_CHANGES.REMOVE)
            count.text = item.countProductInCart.toString()
            price.text =
                "${item.prise * item.countProductInCart} ${itemView.resources.getText(R.string.currency)}"
        }
    }

    private fun setImage(url: String, image: ImageView) {
        Glide.with(image)
            .load(url)
            .into(image)
    }
}