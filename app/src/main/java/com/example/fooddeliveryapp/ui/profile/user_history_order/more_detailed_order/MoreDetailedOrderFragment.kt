package com.example.fooddeliveryapp.ui.profile.user_history_order.more_detailed_order

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fooddeliveryapp.DaggerApp
import com.example.fooddeliveryapp.R
import com.example.fooddeliveryapp.databinding.FragmentMoreDetailedOrderBinding
import com.example.fooddeliveryapp.di.viewModel.ViewModelFactory
import com.google.android.material.bottomnavigation.BottomNavigationView
import javax.inject.Inject

class MoreDetailedOrderFragment : Fragment() {

    @Inject
    lateinit var factory: ViewModelFactory
    private val viewModel: MoreDetailedOrderViewModel by viewModels { factory }
    private var _binding: FragmentMoreDetailedOrderBinding? = null
    private val binding get() = _binding!!
    private val args: MoreDetailedOrderFragmentArgs by navArgs()
    private val adapterMoreDetailedOrder = MoreDetailedOrderAdapter()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().applicationContext as DaggerApp).appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentMoreDetailedOrderBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        args.orderTime.let {
            binding.tvOrderTime.text = it
            viewModel.getUserOrder(it)
        }
        setupButtons()
        setupRecyclerView()
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.userOrderHistoryLiveData.observe(viewLifecycleOwner) {
            adapterMoreDetailedOrder.setItems(it)
        }

        viewModel.orderRescheduledLiveData.observe(viewLifecycleOwner) {
            if (it) navigateToCart()
        }
    }


    private fun setupButtons() {
        binding.ivBack.setOnClickListener { navigateBack() }
        binding.btnRepeatOrder.setOnClickListener {
            viewModel.addOrderToCart(adapterMoreDetailedOrder.getItems())
        }
    }

    private fun navigateToCart() {
        val navController =
            requireActivity().findNavController(R.id.nav_host_fragment_activity_main)

        navController.popBackStack(R.id.navigation_profile, false)

        val bottomNavigationView =
            requireActivity().findViewById<BottomNavigationView>(R.id.nav_view)
        bottomNavigationView.menu.findItem(R.id.navigation_cart).let {
            it.isChecked = true
        }
        bottomNavigationView.selectedItemId = R.id.navigation_cart
    }

    private fun navigateBack() {
        requireActivity().onBackPressedDispatcher.onBackPressed()
    }

    private fun setupRecyclerView() {
        binding.rvProducts.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rvProducts.adapter = adapterMoreDetailedOrder
    }
}