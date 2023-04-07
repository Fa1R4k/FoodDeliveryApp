package com.example.fooddeliveryapp.ui.home.menu_recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fooddeliveryapp.databinding.RvItemProductBinding
import com.example.fooddeliveryapp.databinding.TextItemBinding
import com.example.fooddeliveryapp.domain.model.ProductItem

class MenuAdapter(
    private val openProductItemClick: (Int) -> Unit,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val listProductItem: MutableList<ProductItem> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        when (viewType) {
            PRODUCT_TYPE -> {
                val item =
                    RvItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                MenuViewHolder(item, openProductItemClick)
            }
            TITLE_TYPE -> {
                val item =
                    TextItemBinding.inflate(LayoutInflater.from(parent.context),
                        parent,
                        false)
                TitleViewHolder(item)
            }
            else -> throw Exception("Invalid Type!")
        }

    override fun getItemCount(): Int = listProductItem.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is MenuViewHolder -> holder.onBind(listProductItem[position] as ProductItem.ProductData)
            is TitleViewHolder -> holder.onBind(listProductItem[position] as ProductItem.ProductTitleItem)
        }
    }

    override fun getItemViewType(position: Int): Int = when (listProductItem[position]) {
        is ProductItem.ProductData -> PRODUCT_TYPE
        is ProductItem.ProductTitleItem -> TITLE_TYPE
    }

    fun setItems(newListProductData: List<ProductItem>) {
        if (newListProductData != listProductItem) {
            listProductItem.clear()
            listProductItem.addAll(newListProductData)
            notifyDataSetChanged()
        }
    }

    companion object {
        private const val TITLE_TYPE = 1
        private const val PRODUCT_TYPE = 2
    }
}