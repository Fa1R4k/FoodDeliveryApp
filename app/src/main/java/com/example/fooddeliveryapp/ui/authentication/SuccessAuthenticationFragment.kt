package com.example.fooddeliveryapp.ui.authentication

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.fooddeliveryapp.DaggerApp
import com.example.fooddeliveryapp.R
import com.example.fooddeliveryapp.databinding.FragmentAuthenticationSuccessBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class SuccessAuthenticationFragment : Fragment() {

    private var _binding: FragmentAuthenticationSuccessBinding? = null
    private val binding get() = _binding!!

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().applicationContext as DaggerApp).appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentAuthenticationSuccessBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupButtons()
    }

    private fun setupButtons() {
        binding.btnToProfile.setOnClickListener { navigateToProfile() }
        binding.btnToCart.setOnClickListener { navigateToCart() }
    }

    private fun navigateToProfile() {
        val navController =
            requireActivity().findNavController(R.id.nav_host_fragment_activity_main)

        navController.popBackStack(R.id.navigation_profile, false)
        navController.popBackStack(R.id.navigation_cart, false)
        navController.popBackStack(R.id.navigation_home, false)

        val bottomNavigationView =
            requireActivity().findViewById<BottomNavigationView>(R.id.nav_view)
        bottomNavigationView.menu.findItem(R.id.navigation_profile).let {
            it.isChecked = true
        }
        bottomNavigationView.selectedItemId = R.id.navigation_profile
    }

    private fun navigateToCart() {
        val navController =
            requireActivity().findNavController(R.id.nav_host_fragment_activity_main)

        navController.popBackStack(R.id.navigation_profile, false)
        navController.popBackStack(R.id.navigation_cart, false)
        navController.popBackStack(R.id.navigation_home, false)

        val bottomNavigationView =
            requireActivity().findViewById<BottomNavigationView>(R.id.nav_view)
        bottomNavigationView.menu.findItem(R.id.navigation_cart).let {
            it.isChecked = true
        }
        bottomNavigationView.selectedItemId = R.id.navigation_cart
    }
}