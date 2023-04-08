package com.example.fooddeliveryapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fooddeliveryapp.R
import com.example.fooddeliveryapp.databinding.FragmentHomeBinding
import com.example.fooddeliveryapp.ui.home.category.CategoryAdapter
import com.example.fooddeliveryapp.ui.home.menu_recycler.MenuAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<HomeViewModel>()
    private var menuAdapter = MenuAdapter(openProductItemClick())
    private var categoryAdapter = CategoryAdapter(::itemCategoryClick)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        viewModel.getCountProductInCart()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupCategoryRecyclerView()
        println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!")
        println(categoryAdapter.lastCheckedPosition)

        observeLiveData()
        setupMenuRecyclerView()
        observeCountProductInCartData()
        observeLoadingLiveData()

        binding.ivHomeSearch.setOnClickListener {
            openSearchItemClick()
        }
        viewModel.getCategory()
    }

    private fun observeLoadingLiveData() {
        viewModel.loadingLiveData.observe(viewLifecycleOwner) {
            binding.loading.isVisible = it
            binding.fragment.isVisible = !it
        }
    }

    private fun observeCountProductInCartData() {
        viewModel.countProductInCartData.observe(viewLifecycleOwner) {
            val bottomNavigationView =
                requireActivity().findViewById<BottomNavigationView>(R.id.nav_view)
            val badge = bottomNavigationView.getOrCreateBadge(R.id.navigation_cart)
            badge.number = it
        }
    }

    private fun setupMenuRecyclerView() {
        binding.rvHomeMenu.adapter = menuAdapter
        binding.rvHomeMenu.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.VERTICAL,
            false
        )
    }

    private fun scrollRecyclerToTop() {
        binding.rvHomeMenu.scrollToPosition(0)
    }

    private fun setupCategoryRecyclerView() {
        binding.rvHomeCategory.adapter = categoryAdapter
        binding.rvHomeCategory.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.HORIZONTAL,
            false
        )
    }

    private fun observeLiveData() {
        viewModel.categoryLiveData.observe(viewLifecycleOwner) {
            categoryAdapter.setItems(it)
        }

        viewModel.liveData.observe(viewLifecycleOwner) {
            menuAdapter.setItems(it)
        }
    }

    private fun openProductItemClick(): (Int) -> Unit = { id ->
        val action = HomeFragmentDirections.actionNavigationHomeToProductDescriptionFragment(id)
        findNavController().navigate(action)
    }


    private fun openSearchItemClick() {
        val action = HomeFragmentDirections.actionNavigationHomeToSearchFragment()
        findNavController().navigate(action)
    }

    private fun itemCategoryClick(category: String) {
        if (category.isEmpty()) {
            viewModel.getProduct()
        }
        viewModel.getProduct(category)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
