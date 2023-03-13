package com.example.fooddeliveryapp.ui.cart

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.Button
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fooddeliveryapp.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CartFragment : Fragment() {

    private val viewModel by viewModels<CartViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_cart, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val cartRecyclerView = view.findViewById<RecyclerView>(R.id.cartRecyclerView)
        val btnForBuy = view.findViewById<Button>(R.id.btnForBuy)
        viewModel.apply {
            liveData.observe(viewLifecycleOwner) {
                cartRecyclerView.adapter = CartAdapter(it, changeCart())
                cartRecyclerView.layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            }
            priceLiveData.observe(viewLifecycleOwner) {
                btnForBuy.text = "${resources.getText(R.string.buy)} ${
                    String.format("%.2f", it)
                } ${resources.getText(R.string.currency)}"
            }
        }
        viewModel.getCartFromData()

        btnForBuy.setOnClickListener {
            viewModel.deleteCart()
        }

    }

    private fun changeCart(): (Int, String, CART_CHANGES) -> Unit = { id, parameter, change ->
        viewModel.changeCart(id,parameter, change)
    }
}