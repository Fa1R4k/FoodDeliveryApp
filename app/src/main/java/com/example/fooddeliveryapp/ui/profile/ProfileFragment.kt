package com.example.fooddeliveryapp.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fooddeliveryapp.R
import com.example.fooddeliveryapp.databinding.FragmentProfileBinding
import com.example.fooddeliveryapp.domain.model.User
import com.example.fooddeliveryapp.ui.profile.menu_recycler.ProfileMenuAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<ProfileViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        viewModel.getUserAuthorizationState()
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeLiveData()
        setupButtonExit()
    }

    private fun observeLiveData() {
        viewModel.authorizedLiveData.observe(viewLifecycleOwner) {
            if (it) viewModel.updateUser() else navigateToNeedAuthentication()
        }
        viewModel.userLiveData.observe(viewLifecycleOwner) {
            setupRecyclerView(it)
            if (it.discount == 15) setInvisibleDiscountView()
            binding.tvUserSpend.text =
                getString(R.string.price_currency, String.format("%.2f", it.totalSpend))
            binding.tvProgress.text = it.discount.toString() + getString(R.string.procent)
            binding.tvUserNextDiscount.text =
                (it.discount + 1).toString() + getString(R.string.procent)
            binding.progressBar.progress = it.discount.toFloat()
            binding.tvUserAmountFromDiscount.text =
                "${String.format("%.2f", it.nextDiscountSum)} ${getString(R.string.RUB)}"
        }
    }

    private fun setupButtonExit() {
        binding.ivLogout.setOnClickListener {
            logout()
        }
    }


    private fun setInvisibleDiscountView() {
        val color = context?.getColor(R.color.white_back)
        color?.let {
            binding.tvUserNextDiscount.setTextColor(color)
            binding.tvFromDiscount.setTextColor(color)
            binding.tvFromDiscount.setTextColor(color)
            binding.tvFromDiscount.setTextColor(color)
            binding.tvMoneyDiscount.setTextColor(color)
            binding.tvUserAmountFromDiscount.setTextColor(color)
        }
    }

    private fun logout() {
        viewModel.logout()
        requireActivity().onBackPressedDispatcher.onBackPressed()
    }

    private fun setupRecyclerView(user: User) {
        binding.rvProfileMenu.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvProfileMenu.adapter =
            ProfileMenuAdapter(mutableListOf(hashMapOf("UserName" to user.name),
                hashMapOf("История заказов" to user.orderCount.toString()),
                hashMapOf("Адрес доставки" to user.address.size.toString())),
                user,
                openProductItemClick())
    }

    private fun openProductItemClick(): (String) -> Unit = { id ->
        val action =
            ProfileFragmentDirections.actionNavigationDashboardToUserProfileFragment(id)
        findNavController().navigate(action)
    }


    private fun navigateToNeedAuthentication() {
        val action =
            ProfileFragmentDirections.actionNavigationDashboardToNeedAuthenticationFragment()
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}