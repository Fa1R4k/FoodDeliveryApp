package com.example.fooddeliveryapp.ui.profile.user_history_order.more_detailed_order

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fooddeliveryapp.DaggerApp
import com.example.fooddeliveryapp.databinding.FragmentMoreDetailedOrderBinding
import com.example.spinnercat.di.ViewModel.ViewModelFactory
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
        setupRecyclerView()
        viewModel.userOrderHistoryLiveData.observe(viewLifecycleOwner) {
            adapterMoreDetailedOrder.setItems(it)
        }
    }

    private fun setupRecyclerView() {
        binding.rvProducts.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rvProducts.adapter = adapterMoreDetailedOrder
    }
}