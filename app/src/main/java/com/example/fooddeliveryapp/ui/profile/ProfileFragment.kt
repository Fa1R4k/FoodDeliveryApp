package com.example.fooddeliveryapp.ui.profile

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.fooddeliveryapp.DaggerApp
import com.example.fooddeliveryapp.R
import com.example.fooddeliveryapp.databinding.FragmentProfileBinding
import com.example.fooddeliveryapp.ui.home.HomeFragmentDirections
import com.example.fooddeliveryapp.di.viewModel.ViewModelFactory
import com.google.android.material.bottomnavigation.BottomNavigationView
import javax.inject.Inject

class ProfileFragment : Fragment() {

    @Inject
    lateinit var factory: ViewModelFactory
    private val viewModel: ProfileViewModel by viewModels { factory }
    private var _binding: FragmentProfileBinding? = null
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
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        viewModel.getNetworkState()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeLiveData()
        setupButtons()
    }

    private fun observeLiveData() {
        viewModel.networkStateLiveData.observe(viewLifecycleOwner) {
            binding.groupConnectionError.isVisible = !it
            if (it) viewModel.getUserAuthorizationState()
        }

        val mediatorLiveData = MediatorLiveData<Boolean>()

        mediatorLiveData.addSource(viewModel.loadingAuthorizedLiveData) { loadingAuthorized ->
            if (!loadingAuthorized) {
                mediatorLiveData.addSource(viewModel.authorizedLiveData) { authorized ->
                    if (authorized) {
                        viewModel.getUser()
                    } else {
                        navigateToNeedAuthentication()
                    }
                }
            }
        }

        mediatorLiveData.observe(viewLifecycleOwner, Observer { })

        viewModel.userLiveData.observe(viewLifecycleOwner) {
            with(binding) {
                if (it.discount == getString(R.string.max_discount_int).toInt()) setInvisibleDiscountView()
                tvOrderHistory.text = it.orderHistory.size.toString()
                tvUserName.text = it.name
                tvUserAddresses.text = it.address.size.toString()
                tvUserSpend.text =
                    getString(R.string.price_currency, String.format("%.2f", it.totalSpend))
                tvProgress.text = getString(R.string.price_percent, it.discount)
                tvUserNextDiscount.text =
                    getString(R.string.price_percent, it.discount + 1)
                progressBar.progress = it.discount.toFloat()
                tvUserAmountFromDiscount.text =
                    getString(R.string.RUB, String.format("%.2f", it.nextDiscountSum))
            }
        }
        viewModel.loadingLiveData.observe(viewLifecycleOwner) {
            binding.loading.isVisible = it
            binding.fragment.isVisible = !it
        }
    }

    private fun setupButtons() {
        binding.llNavigateToUserProfile.setOnClickListener { openUserProfile() }
        binding.ivLogout.setOnClickListener { logout() }
        binding.llNavigateToUserAddresses.setOnClickListener { navigateToUserAddresses() }
        binding.llNavigateToHistoryOrder.setOnClickListener { navigateToUserOrderHistory() }
        binding.btnReloadNetwork.setOnClickListener { viewModel.getNetworkState() }
    }

    private fun setInvisibleDiscountView() {
        val color = requireContext().getColor(R.color.white_back)
        color.let {
            with(binding) {
                tvAdvertising.setTextColor(it)
                tvUserNextDiscount.setTextColor(it)
                tvFromDiscount.setTextColor(it)
                tvFromDiscount.setTextColor(it)
                tvFromDiscount.setTextColor(it)
                tvMoneyDiscount.setTextColor(it)
                tvUserAmountFromDiscount.setTextColor(it)
            }
        }
    }

    private fun logout() {
        viewModel.logout()
        requireActivity().onBackPressedDispatcher.onBackPressed()
    }

    private fun openUserProfile() {
        val action = ProfileFragmentDirections.actionNavigationDashboardToUserProfileFragment()
        findNavController().navigate(action)
    }

    private fun navigateToUserAddresses() {
        val action = ProfileFragmentDirections.actionNavigationProfileToUserAddressesFragment()
        findNavController().navigate(action)
    }

    private fun navigateToUserOrderHistory() {
        val action =
            ProfileFragmentDirections.actionNavigationProfileToUserOrderHistoryFragment()
        findNavController().navigate(action)
    }

    private fun navigateToNeedAuthentication() {
        val navController =
            requireActivity().findNavController(R.id.nav_host_fragment_activity_main)

        navController.popBackStack(R.id.navigation_profile, false)

        val bottomNavigationView =
            requireActivity().findViewById<BottomNavigationView>(R.id.nav_view)
        bottomNavigationView.menu.findItem(R.id.navigation_home).let {
            it.isChecked = true
        }
        bottomNavigationView.selectedItemId = R.id.navigation_home
        val action =
            HomeFragmentDirections.actionNavigationHomeToNeedAuthenticationFragment()
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}