package com.example.fooddeliveryapp.ui.cart

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.fooddeliveryapp.R
import com.example.fooddeliveryapp.databinding.RvItemCartBinding
import com.example.fooddeliveryapp.domain.model.CartProduct

class CartHolder(
    private val binding: RvItemCartBinding,
    private val changeCountForProduct: (Int, String, CartChanges) -> Unit,
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
            tvPrice.text = getPriseText(itemView, item)
        }
    }

    private fun setUpListeners(item: CartProduct) {
        with(binding) {
            btnAdd.setOnClickListener {
                clickAdd(item, binding.tvProductCount, binding.tvPrice)
            }
            btnRemove.setOnClickListener {
                clickRemove(
                    item, binding.tvProductCount, binding.tvPrice
                )
            }
        }
    }

    private fun clickAdd(item: CartProduct, count: TextView, price: TextView) {
        changeCountForProduct(item.id, item.productParameter, CartChanges.ADD)
        count.text = item.countProductInCart.toString()
        price.text = getPriseText(itemView, item)
    }

    private fun clickRemove(item: CartProduct, count: TextView, price: TextView) {
        if (item.countProductInCart == 1) {
            changeCountForProduct(item.id, item.productParameter, CartChanges.DELETE)
            cartAdapter.updateList(item, itemView)
            this.binding.btnRemove.isEnabled = false
        } else {
            changeCountForProduct(item.id, item.productParameter, CartChanges.REMOVE)
            count.text = item.countProductInCart.toString()
            price.text = getPriseText(itemView, item)
        }
    }

    private fun setImage(url: String, image: ImageView) {
        Glide.with(image).load(url).into(image)
    }

    private fun getPriseText(itemView: View, item: CartProduct): String {
        return itemView.resources.getString(
            R.string.price_all_currency, String.format("%.2f", item.prise * item.countProductInCart)
        )
    }
}