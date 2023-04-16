package com.example.fooddeliveryapp.ui.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fooddeliveryapp.DaggerApp
import com.example.fooddeliveryapp.R
import com.example.fooddeliveryapp.databinding.FragmentHomeBinding
import com.example.fooddeliveryapp.domain.model.ProductItem
import com.example.fooddeliveryapp.ui.home.category.CategoryAdapter
import com.example.fooddeliveryapp.ui.home.menu_recycler.MenuAdapter
import com.example.fooddeliveryapp.di.viewModel.ViewModelFactory
import com.google.android.material.bottomnavigation.BottomNavigationView
import javax.inject.Inject

class HomeFragment : Fragment() {

    @Inject
    lateinit var factory: ViewModelFactory
    private val viewModel: HomeViewModel by viewModels { factory }
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private var menuAdapter = MenuAdapter(openProductItemClick())
    private var categoryAdapter = CategoryAdapter(::itemCategoryClick)
    private var menu = mutableListOf<ProductItem>()
    private var position = 0
    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().applicationContext as DaggerApp).appComponent.inject(this)
    }

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
        viewModel.getNetworkState()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeLiveData()

        setupCategoryRecyclerView()
        setupMenuRecyclerView()
        trackScrollMenuRecycler()
        setupButtons()
    }

    private fun initViewModel() {
        viewModel.getCategory()
        viewModel.getProduct()
    }

    private fun setupButtons() {
        binding.ivHomeSearch.setOnClickListener {
            openSearchItemClick()
        }
        binding.btnReloadNetwork.setOnClickListener { viewModel.getNetworkState() }
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
        viewModel.networkStateLiveData.observe(viewLifecycleOwner) {
            if (it) initViewModel()
            binding.groupConnectionError.isVisible = !it
        }

        viewModel.categoryLiveData.observe(viewLifecycleOwner) {
            categoryAdapter.setItems(it)
        }

        viewModel.liveData.observe(viewLifecycleOwner) {
            menu = it.toMutableList()
            menuAdapter.setItems(it)
        }

        viewModel.loadingLiveData.observe(viewLifecycleOwner) {
            binding.loading.isVisible = it
            binding.fragment.isVisible = !it
        }

        viewModel.countProductInCartData.observe(viewLifecycleOwner) {
            setBadge(it)
        }
    }

    private fun setBadge(number: Int) {
        val bottomNavigationView =
            requireActivity().findViewById<BottomNavigationView>(R.id.nav_view)
        val badge = bottomNavigationView.getOrCreateBadge(R.id.navigation_cart)
        badge.number = number
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
            val item = findProductTitleItemByTitle(category)
            position = getPositionOfItem(item)
            scrollMenuRvToPosition(position)
        }
    }

    private fun getPositionOfItem(item: ProductItem.ProductTitleItem): Int {
        position = menu.indexOf(item)

        (binding.rvHomeMenu.layoutManager as LinearLayoutManager).let {
            val itemsVisibleOnScreen =
                it.findLastVisibleItemPosition() - it.findFirstVisibleItemPosition() - ELEMENT_PRODUCT_TITTLE
            if (it.findLastVisibleItemPosition() < position) position += itemsVisibleOnScreen
        }
        return position
    }

    private fun findProductTitleItemByTitle(title: String): ProductItem.ProductTitleItem {
        return menu.filterIsInstance<ProductItem.ProductTitleItem>().first { it.title == title }
    }

    private fun scrollMenuRvToPosition(position: Int) {
        binding.rvHomeMenu.smoothScrollToPosition(position)
    }

    private fun scrollCategoryRvToNext(position: Int) {
        binding.rvHomeCategory.smoothScrollToPosition(position)
    }

    private fun trackScrollMenuRecycler() {
        binding.rvHomeMenu.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (menu.isNotEmpty()) {
                    val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                    val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
                    val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()
                    if (firstVisibleItemPosition in (position..position - lastVisibleItemPosition - firstVisibleItemPosition + ELEMENT_PRODUCT_TITTLE)) {
                        if (menu[firstVisibleItemPosition] is ProductItem.ProductData) {
                            scrollCategoryRvToNext(categoryAdapter.setSelectedItem((menu[firstVisibleItemPosition] as ProductItem.ProductData).category))
                        }
                    } else {
                        if (menu[lastVisibleItemPosition] is ProductItem.ProductData) {
                            scrollCategoryRvToNext(categoryAdapter.setSelectedItem((menu[lastVisibleItemPosition] as ProductItem.ProductData).category))
                        }
                    }
                }
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val ELEMENT_PRODUCT_TITTLE = 1
    }
}
