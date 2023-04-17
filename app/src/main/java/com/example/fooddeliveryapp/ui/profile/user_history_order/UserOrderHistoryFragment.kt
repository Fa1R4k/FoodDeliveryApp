package com.example.fooddeliveryapp.ui.profile.user_history_order

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fooddeliveryapp.DaggerApp
import com.example.fooddeliveryapp.databinding.FragmentUserOrderHistoryBinding
import com.example.fooddeliveryapp.di.viewModel.ViewModelFactory
import javax.inject.Inject

class UserOrderHistoryFragment : Fragment() {

    @Inject
    lateinit var factory: ViewModelFactory
    private val viewModel: UserOrderHistoryViewModel by viewModels { factory }
    private var _binding: FragmentUserOrderHistoryBinding? = null
    private val binding get() = _binding!!

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().applicationContext as DaggerApp).appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentUserOrderHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getUserOrderHistory()
        observeUserOrderHistoryLiveData()
        setupButtons()
        }

    private fun setupButtons() {
        binding.ivBack.setOnClickListener{ navigateBack() }
    }

    private fun navigateBack() {
        requireActivity().onBackPressedDispatcher.onBackPressed()
    }

    private fun observeUserOrderHistoryLiveData() {
        viewModel.userOrderHistoryLiveData.observe(viewLifecycleOwner) {
            binding.rvOrder.adapter =
                OrderHistoryAdapter(it, ::navigateToMoreDetailedOrder)
            binding.rvOrder.layoutManager = LinearLayoutManager(
                requireContext(), LinearLayoutManager.VERTICAL, false
            )
    }
}

    private fun navigateToMoreDetailedOrder(orderTime: String) {
        val action =
            UserOrderHistoryFragmentDirections.actionUserOrderHistoryFragmentToMoreDetailedOrderFragment(
                orderTime
            )
        findNavController().navigate(action)
    }
}
