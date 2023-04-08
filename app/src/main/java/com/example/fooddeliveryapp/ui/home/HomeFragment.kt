package com.example.fooddeliveryapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fooddeliveryapp.R
import com.example.fooddeliveryapp.databinding.FragmentHomeBinding
import com.example.fooddeliveryapp.domain.model.ProductItem
import com.example.fooddeliveryapp.ui.home.category.CategoryAdapter
import com.example.fooddeliveryapp.ui.home.menu_recycler.MenuAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import java.text.FieldPosition

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<HomeViewModel>()
    private var menuAdapter = MenuAdapter(openProductItemClick())
    private var categoryAdapter = CategoryAdapter(::itemCategoryClick)
    private var menu = mutableListOf<ProductItem>()
    private var isSmoothScrolling = false
    private var position = 0
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

        observeLiveData()
        setupMenuRecyclerView()
        observeScrollMenuRecycler()
        observeCountProductInCartData()
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
            requireContext(), LinearLayoutManager.VERTICAL, false
        )
    }

    private fun setupCategoryRecyclerView() {
        binding.rvHomeCategory.adapter = categoryAdapter
        binding.rvHomeCategory.layoutManager = LinearLayoutManager(
            requireContext(), LinearLayoutManager.HORIZONTAL, false
        )
    }

    private fun observeLiveData() {
        viewModel.categoryLiveData.observe(viewLifecycleOwner) {
            categoryAdapter.setItems(it)
        }

        viewModel.liveData.observe(viewLifecycleOwner) {
            menu = it.toMutableList()
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
        if (menu.isNotEmpty()) {
            val item =
                menu.filterIsInstance<ProductItem.ProductTitleItem>().first { it.title == category }
            position = menu.indexOf(item)
            val layoutManager = binding.rvHomeMenu.layoutManager as LinearLayoutManager
            val visibleItemPosition = layoutManager.findFirstVisibleItemPosition()
            if (visibleItemPosition < position) position += 3

            isSmoothScrolling = true
            binding.rvHomeMenu.isNestedScrollingEnabled = false
            binding.rvHomeMenu.smoothScrollToPosition(position)
        }
    }

    private fun observeScrollMenuRecycler() {
        binding.rvHomeMenu.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
                val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()
                if (!isSmoothScrolling) {
                    if (menu.isNotEmpty()) {
                        if (menu[firstVisibleItemPosition] is ProductItem.ProductTitleItem) {
                            scrollRecyclerViewToNext(categoryAdapter.setSelectedItem((menu[firstVisibleItemPosition] as ProductItem.ProductTitleItem).title))
                        }
                        if (menu[lastVisibleItemPosition] is ProductItem.ProductTitleItem) {
                            scrollRecyclerViewToNext(categoryAdapter.setBackItem((menu[lastVisibleItemPosition] as ProductItem.ProductTitleItem).title))
                        }
                    }
                } else {
                    if (firstVisibleItemPosition in (position..position - 4)) {
                        if (menu[firstVisibleItemPosition] is ProductItem.ProductData) {
                            scrollRecyclerViewToNext(categoryAdapter.setSelectedItem((menu[firstVisibleItemPosition] as ProductItem.ProductData).category))
                        }
                        isSmoothScrolling = false
                    }
                    if (menu[lastVisibleItemPosition] is ProductItem.ProductData) {
                        scrollRecyclerViewToNext(categoryAdapter.setSelectedItem((menu[lastVisibleItemPosition] as ProductItem.ProductData).category))
                    }
                }
            }
        })
    }


    private fun scrollRecyclerViewToNext(position: Int) {
        binding.rvHomeCategory.smoothScrollToPosition(position)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
