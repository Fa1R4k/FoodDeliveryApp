package com.example.fooddeliveryapp.ui.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fooddeliveryapp.R
import com.example.fooddeliveryapp.databinding.FragmentCartBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CartFragment : Fragment() {

    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<CartViewModel>()
    private var userIsAuthentication = false


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentCartBinding.inflate(inflater, container, false)
        return binding.root
    }

    private var purchasePrice = 0.0
    private var adapter = CartAdapter(changeCart())

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupButton()
        observeLiveData()
        observeIsEmptyCartLiveData()
        viewModel.isEmptyCart()
        viewModel.getCartFromData()
    }

    private fun observeIsEmptyCartLiveData() {
        viewModel.isEmptyCartViewModel.observe(viewLifecycleOwner) {
            binding.cartEmpty.isVisible = it
            binding.cartNotEmpty.isVisible = !it
        }
    }

    private fun setupRecyclerView() {
        binding.cartRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.cartRecyclerView.adapter = adapter

    }

    private fun setupButton() {
        binding.btnForBuy.setOnClickListener {
            viewModel.getUserAuthorizationState()
            if (userIsAuthentication) {
                navigateConfirmPurchase()
            } else {
                navigateToNeedAuthentication()
            }
        }

        binding.btnNavigateToMenu.setOnClickListener {
            navigateToMenu()
        }
    }

    private fun navigateToMenu() {
        val navController =
            requireActivity().findNavController(R.id.nav_host_fragment_activity_main)

        navController.popBackStack(R.id.navigation_cart, false)
        val bottomNavigationView =
            requireActivity().findViewById<BottomNavigationView>(R.id.nav_view)
        bottomNavigationView.menu.findItem(R.id.navigation_home).let {
            it.isChecked = true
        }
        bottomNavigationView.selectedItemId = R.id.navigation_home
    }

    private fun navigateToNeedAuthentication() {
        val action = CartFragmentDirections.actionNavigationCartToNeedAuthenticationFragment()
        findNavController().navigate(action)
    }

    private fun navigateConfirmPurchase() {
        val action = CartFragmentDirections.actionNavigationCartToConfirmPurchaseFragment()
        findNavController().navigate(action)
    }

    private fun observeLiveData() {
        viewModel.liveData.observe(viewLifecycleOwner) {
            adapter.setItems(it)
        }
        viewModel.priceLiveData.observe(viewLifecycleOwner) {
            purchasePrice = it
            binding.btnForBuy.text = "${resources.getText(R.string.buy)} ${
                String.format("%.2f", it)
            } ${resources.getText(R.string.currency)}"
        }
        viewModel.authorizedLiveData.observe(viewLifecycleOwner) {
            userIsAuthentication = it
        }

        viewModel.loadingLiveData.observe(viewLifecycleOwner) {
            binding.loading.isVisible = it
        }

        viewModel.changeLiveData.observe(viewLifecycleOwner) {
            if (!it) viewModel.isEmptyCart()
        }
    }

    private fun changeCart(): (Int, String, CartChanges) -> Unit = { id, parameter, change ->
        viewModel.changeCart(id, parameter, change)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
