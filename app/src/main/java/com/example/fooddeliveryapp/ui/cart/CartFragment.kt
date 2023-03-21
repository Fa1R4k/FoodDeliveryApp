package com.example.fooddeliveryapp.ui.cart

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fooddeliveryapp.R
import com.example.fooddeliveryapp.databinding.FragmentCartBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CartFragment : Fragment() {

    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<CartViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentCartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupButton()
        observeViewModel()
        viewModel.getCartFromData()
    }

    private fun setupRecyclerView() {
        binding.cartRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
    }

    private fun setupButton() {
        binding.btnForBuy.setOnClickListener {
            viewModel.deleteCart()
        }
    }

    private fun observeViewModel() {
        viewModel.liveData.observe(viewLifecycleOwner) {
            binding.cartRecyclerView.adapter = CartAdapter(it, changeCart())
        }
        viewModel.priceLiveData.observe(viewLifecycleOwner) {
            binding.btnForBuy.text = "${resources.getText(R.string.buy)} ${
                String.format("%.2f", it)
            } ${resources.getText(R.string.currency)}"
        }
    }

    private fun changeCart(): (Int, String, CART_CHANGES) -> Unit = { id, parameter, change ->
        viewModel.changeCart(id, parameter, change)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
