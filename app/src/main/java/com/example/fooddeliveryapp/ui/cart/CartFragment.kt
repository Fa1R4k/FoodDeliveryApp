package com.example.fooddeliveryapp.ui.cart

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fooddeliveryapp.R
import com.example.fooddeliveryapp.ui.home.HomeFragmentDirections
import com.example.fooddeliveryapp.ui.home.HomeViewModel
import com.example.fooddeliveryapp.ui.home.menu_recycler.MenuAdapter
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
        val cartPrice = view.findViewById<TextView>(R.id.tvFullPrice)

        viewModel.apply {
            liveData.observe(viewLifecycleOwner) {
                cartRecyclerView.adapter = CartAdapter(it, openProductItemClick())
                cartRecyclerView.layoutManager = LinearLayoutManager(requireContext(),
                    LinearLayoutManager.VERTICAL,
                    false)
            }
            priceLiveData.observe(viewLifecycleOwner) {
                cartPrice.text = String.format("%.2f", it)
            }
        }
        viewModel.getCartFromData()
    }

    private fun openProductItemClick(): (Int) -> Unit = { id ->
        val action = HomeFragmentDirections.actionNavigationHomeToProductDescriptionFragment(id)
        findNavController().navigate(action)
    }
}