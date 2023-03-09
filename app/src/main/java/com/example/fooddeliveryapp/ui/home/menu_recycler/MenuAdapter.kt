package com.example.fooddeliveryapp.ui.home.menu_recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fooddeliveryapp.R
import com.example.fooddeliveryapp.domain.ProductItem
import java.lang.Exception

class MenuAdapter(
    private val listProductData: List<ProductItem>,
    private val openProductItemClick: (Int) -> Unit,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        when (viewType) {
            PRODUCT_TYPE -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.rv_item_product, parent, false)
                MenuViewHolder(view,openProductItemClick)
            }
            TITLE_TYPE -> {
                val view =
                    LayoutInflater.from(parent.context).inflate(R.layout.text_item, parent, false)
                TitleViewHolder(view)
            }
            else -> throw Exception("Invalid Type!")
        }

    override fun getItemCount(): Int = listProductData.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is MenuViewHolder -> holder.onBind(listProductData[position] as ProductItem.ProductData)
            is TitleViewHolder -> holder.onBind(listProductData[position] as ProductItem.ProductTitleItem)
        }
    }

    override fun getItemViewType(position: Int): Int = when (listProductData[position]) {
        is ProductItem.ProductData -> PRODUCT_TYPE
        is ProductItem.ProductTitleItem -> TITLE_TYPE
    }

    companion object {
        private const val TITLE_TYPE = 1
        private const val PRODUCT_TYPE = 2
    }
}