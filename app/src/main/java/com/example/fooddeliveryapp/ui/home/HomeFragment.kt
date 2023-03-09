package com.example.fooddeliveryapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fooddeliveryapp.R
import com.example.fooddeliveryapp.ui.home.category.CategoryAdapter
import com.example.fooddeliveryapp.ui.home.menu_recycler.MenuAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val viewModel by viewModels<HomeViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate((R.layout.fragment_home), container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val menuRecycler = view.findViewById<RecyclerView>(R.id.rvHomeMenu)
        val categoryRecycler = view.findViewById<RecyclerView>(R.id.rvHomeCategory)

        viewModel.apply {
            liveData.observe(viewLifecycleOwner) {
                menuRecycler.adapter = MenuAdapter(it, openProductItemClick())
                menuRecycler.layoutManager = LinearLayoutManager(requireContext(),
                    LinearLayoutManager.VERTICAL,
                    false)
            }

            categoryLiveData.observe(viewLifecycleOwner) {
                val itemCategoryClick: (String) -> Unit = { category ->
                    getProduct(category)
                }
                categoryRecycler.adapter = CategoryAdapter(it, itemCategoryClick)
                categoryRecycler.layoutManager = LinearLayoutManager(requireContext(),
                    LinearLayoutManager.HORIZONTAL,
                    false)

            }
            getCategory()
            getProduct()
        }
    }

    private fun openProductItemClick(): (Int) -> Unit = { id ->
        val action = HomeFragmentDirections.actionNavigationHomeToProductDescriptionFragment(id)
        findNavController().navigate(action)
    }
}
