package com.example.fooddeliveryapp.ui.cart.purchase

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.fooddeliveryapp.DaggerApp
import com.example.fooddeliveryapp.R
import com.example.fooddeliveryapp.databinding.FragmentPurchaseSuccessBinding
import com.example.fooddeliveryapp.ui.DeliveryForegroundService
import com.google.android.material.bottomnavigation.BottomNavigationView

class SuccessPurchaseFragment : Fragment() {
    private var _binding: FragmentPurchaseSuccessBinding? = null
    private val binding get() = _binding!!

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().applicationContext as DaggerApp).appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentPurchaseSuccessBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        startForegroundService()
        setupButtonNavigateToMenu()
    }

    private fun startForegroundService() {
        val intent = Intent(requireContext(), DeliveryForegroundService::class.java)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            requireContext().startForegroundService(intent)
        } else {
            requireContext().startService(intent)
        }
    }

    private fun setupButtonNavigateToMenu() {
        binding.btnToMenu.setOnClickListener {
            navigateToMenu()
        }
    }

    private fun navigateToMenu() {
        val navController =
            requireActivity().findNavController(R.id.nav_host_fragment_activity_main)

        navController.popBackStack(R.id.navigation_cart, false)

        val bottomNavigationView =
            requireActivity().findViewById<BottomNavigationView>(R.id.nav_view)
        bottomNavigationView.menu.findItem(R.id.navigation_home).let {
            it.isChecked = true
        }
        bottomNavigationView.selectedItemId = R.id.navigation_home
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}