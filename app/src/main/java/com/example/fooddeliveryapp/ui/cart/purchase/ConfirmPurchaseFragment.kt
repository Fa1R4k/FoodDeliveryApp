package com.example.fooddeliveryapp.ui.cart.purchase

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.fooddeliveryapp.DaggerApp
import com.example.fooddeliveryapp.R
import com.example.fooddeliveryapp.databinding.FragmentConfirmPurchaseBinding
import com.example.fooddeliveryapp.ui.custom_view.CustomAlertDialog
import com.example.fooddeliveryapp.di.viewModel.ViewModelFactory
import javax.inject.Inject

class ConfirmPurchaseFragment : Fragment() {
    @Inject
    lateinit var factory: ViewModelFactory
    private val viewModel: ConfirmPurchaseViewModel by viewModels { factory }
    private var _binding: FragmentConfirmPurchaseBinding? = null
    private val binding get() = _binding!!
    private lateinit var customDialog: CustomAlertDialog

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().applicationContext as DaggerApp).appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentConfirmPurchaseBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
        setupButtons()
        observeViewModel()
    }

    private fun initViewModel() {
        viewModel.getCartPrice()
        viewModel.getUser()
    }

    private fun setupButtons() {
        with(binding) {
            btnAddAddress.setOnClickListener { openDialogAddNewAddress() }

            ivBack.setOnClickListener { navigateBack() }

            btnConfirmOrder.setOnClickListener {
                when (autoCompleteTextView.text.toString()) {
                    getString(R.string.please_add_new_address) ->
                        tiDropdownMenu.error = getString(R.string.please_add_new_address)

                    getString(R.string.please_choose_address) ->
                        tiDropdownMenu.error = getString(R.string.please_choose_address)

                    else -> viewModel.updateUser()
                }
            }
        }
    }

    private fun observeViewModel() {
        viewModel.userLiveData.observe(viewLifecycleOwner) {
            binding.discount.isVisible = it.discount > 0
            if (it.address.isEmpty()) {
                binding.autoCompleteTextView.setText(getString(R.string.please_add_new_address))
                binding.autoCompleteTextView.setOnClickListener { openDialogAddNewAddress() }
            } else {
                val arrayAdapter =
                    ArrayAdapter(requireContext(), R.layout.dropdown_item, it.address)
                binding.autoCompleteTextView.setAdapter(arrayAdapter)
                binding.autoCompleteTextView.setOnClickListener(null)
            }
        }

        viewModel.isUserUpdate.observe(viewLifecycleOwner) {
            if (!it) navigateToSuccessPurchase() else binding.groupLoading.isVisible = true
        }

        viewModel.priceLiveData.observe(viewLifecycleOwner) {
            binding.tvOrderPrice.text = String.format("%.2f", it)
        }

        viewModel.loadingLiveData.observe(viewLifecycleOwner) {
            binding.groupLoading.isVisible = it
        }
    }

    private fun navigateBack() {
        requireActivity().onBackPressedDispatcher.onBackPressed()
    }

    private fun navigateToSuccessPurchase() {
        val action =
            ConfirmPurchaseFragmentDirections.actionConfirmPurchaseFragmentToSuccessPurchaseFragment()
        findNavController().navigate(action)
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}