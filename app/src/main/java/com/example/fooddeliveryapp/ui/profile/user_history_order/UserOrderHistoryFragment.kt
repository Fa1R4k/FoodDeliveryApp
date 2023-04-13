package com.example.fooddeliveryapp.ui.profile.user_history_order

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fooddeliveryapp.databinding.FragmentUserOrderHistoryBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserOrderHistoryFragment : Fragment() {

    private var _binding: FragmentUserOrderHistoryBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<UserOrderHistoryViewModel>()

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
        viewModel.userOrderHistoryLiveData.observe(viewLifecycleOwner){
            binding.rvOrder.adapter = OrderHistoryAdapter(it,navigate(),requireActivity())
            binding.rvOrder.layoutManager = LinearLayoutManager(
                requireContext(), LinearLayoutManager.VERTICAL, false
            )
        }
    }

    private fun navigate(){

    }
}