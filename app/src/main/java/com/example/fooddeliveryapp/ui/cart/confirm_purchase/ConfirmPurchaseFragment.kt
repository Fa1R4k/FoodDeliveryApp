package com.example.fooddeliveryapp.ui.cart.confirm_purchase

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.fooddeliveryapp.R
import com.example.fooddeliveryapp.databinding.FragmentConfirmPurchaseBinding
import com.example.fooddeliveryapp.ui.custom.CustomAlertDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ConfirmPurchaseFragment : Fragment() {
    private var _binding: FragmentConfirmPurchaseBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<ConfirmPurchaseViewModel>()
    private lateinit var customDialog: CustomAlertDialog
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentConfirmPurchaseBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getCartPrice()
        viewModel.getUser()
        binding.btnAddAddress.setOnClickListener { openDialogAddNewAddress() }


        viewModel.userLiveData.observe(viewLifecycleOwner) {
            if (it.address.isEmpty()) {
                binding.autoCompleteTextView.setText(getString(R.string.please_add_new_address))
                binding.tiDropdownMenu.setOnClickListener { openDialogAddNewAddress() }
            } else {
                val arrayAdapter =
                    ArrayAdapter(requireContext(), R.layout.dropdown_item, it.address)
                binding.autoCompleteTextView.setAdapter(arrayAdapter)
            }
        }

        viewModel.priceLiveData.observe(viewLifecycleOwner) {
            binding.tvOrderPrice.text = String.format("%.2f", it)
        }

        binding.btnConfirmOrder.setOnClickListener {
            viewModel.updateUser()
        }
    }

    private fun openDialogAddNewAddress() {
        customDialog = CustomAlertDialog(requireContext())
        customDialog.setOnAddAddressListener {
            val address = customDialog.getAddress()
            customDialog.dismiss()
            viewModel.addUserAddress(address)
            binding.autoCompleteTextView.setText(address)
        }
        customDialog.show()
    }
}