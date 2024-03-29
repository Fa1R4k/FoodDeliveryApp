package com.example.fooddeliveryapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.fooddeliveryapp.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.yandex.mapkit.MapKitFactory

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        (applicationContext as DaggerApp).appComponent.inject(this)
        super.onCreate(savedInstanceState)

        MapKitFactory.setApiKey("212ecddb-0799-443e-8d89-633034a1cb8c")
        MapKitFactory.initialize(this)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        navView.setupWithNavController(navController)
        val badge = navView.getOrCreateBadge(R.id.navigation_cart)
        badge.backgroundColor = applicationContext.getColor(R.color.orange_300)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            badge.isVisible = true
            when (destination.id) {
                R.id.navigation_profile -> {
                    navView.isVisible = true
                }
                R.id.navigation_cart -> {
                    navView.isVisible = true
                }
                R.id.navigation_home -> {
                    navView.isVisible = true
                }
                R.id.navigation_notifications -> {
                    navView.isVisible = true
                }
                else -> {
                    navView.isVisible = false
                }
            }
        }
    }
}