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
import com.example.fooddeliveryapp.databinding.FragmentHomeBinding
import com.example.fooddeliveryapp.ui.home.category.CategoryAdapter
import com.example.fooddeliveryapp.ui.home.menu_recycler.MenuAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<HomeViewModel>()
    private var menuAdapter = MenuAdapter(openProductItemClick())

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeLiveData()
        setupMenuRecyclerView()
        setupCategoryRecyclerView()
        observeLoadingLiveData()

        binding.ivHomeSearch.setOnClickListener {
            openSearchItemClick()
        }
        viewModel.getCategory()
        viewModel.getProduct()
    }

    private fun observeLoadingLiveData() {
        viewModel.loadingLiveData.observe(viewLifecycleOwner) {
            binding.loading.isVisible = it
            binding.fragment.isVisible = !it
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

    private fun setupCategoryRecyclerView() {
        binding.rvHomeCategory.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.HORIZONTAL,
            false
        )
    }

    private fun observeLiveData() {
        viewModel.categoryLiveData.observe(viewLifecycleOwner) {
            binding.rvHomeCategory.adapter = CategoryAdapter(it, ::itemCategoryClick)
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
