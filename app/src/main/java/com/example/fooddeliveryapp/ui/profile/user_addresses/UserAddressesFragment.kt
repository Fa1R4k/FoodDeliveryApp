package com.example.fooddeliveryapp.ui.profile.user_addresses

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fooddeliveryapp.databinding.FragmentProfileBinding
import com.example.fooddeliveryapp.databinding.FragmentUserAddressesBinding
import com.example.fooddeliveryapp.ui.custom.CustomAlertDialog
import com.example.fooddeliveryapp.ui.profile.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserAddressesFragment : Fragment() {

    private var _binding: FragmentUserAddressesBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<UserAddressesViewModel>()
    private val addressesAdapter = AddressesAdapter(::deleteAddress)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentUserAddressesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        viewModel.getUserAddresses()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeLifeData()
        setupRecyclerView()
        setupAddButton()
    }

    private fun setupRecyclerView() {
        binding.recyclerView.adapter = addressesAdapter
        binding.recyclerView.layoutManager = LinearLayoutManager(
            requireContext(), LinearLayoutManager.VERTICAL, false
        )
    }

    private fun setupAddButton() {
        binding.button2.setOnClickListener { openDialogAddNewAddress() }
    }

    private fun openDialogAddNewAddress() {
        val customDialog = CustomAlertDialog(requireContext())
        customDialog.setOnAddAddressListener {
            val address = customDialog.getAddress()
            customDialog.dismiss()
            viewModel.addUserAddress(address)
        }
        customDialog.show()
    }

    private fun observeLifeData() {
        viewModel.userAddressesLiveData.observe(viewLifecycleOwner) {
            addressesAdapter.setItems(it.toMutableList())
        }
    }

    private fun deleteAddress(address: String) {
        viewModel.deleteAddress(address)
    }
}