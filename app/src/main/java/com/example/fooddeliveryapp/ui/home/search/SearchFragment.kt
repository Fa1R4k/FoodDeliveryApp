package com.example.fooddeliveryapp.ui.home.search

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fooddeliveryapp.databinding.FragmentSearchBinding
import com.example.fooddeliveryapp.ui.home.menu_recycler.MenuAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : DialogFragment() {
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<SearchViewModel>()
    private val adapter = MenuAdapter(openProductItemClick())

    private val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            viewModel.getProduct(s.toString())
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        override fun afterTextChanged(s: Editable?) {}
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        openSearchView()
        setupMenuRecyclerView()
        observeLiveData()
    }

    private fun openSearchView() {
        binding.searchView.requestFocus()
        val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(binding.searchView, InputMethodManager.SHOW_IMPLICIT)
        binding.searchView.requestFocus()
        binding.searchView.addTextChangedListener(textWatcher)
    }

    private fun observeLiveData() {
        viewModel.liveData.observe(viewLifecycleOwner) { products ->
            adapter.setItems(products)
        }
    }

    private fun setupMenuRecyclerView() {
        binding.rvSearchElements.adapter = adapter
        binding.rvSearchElements.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.VERTICAL,
            false
        )
    }

    private fun openProductItemClick(): (Int) -> Unit = { id ->
        val action = SearchFragmentDirections.actionSearchFragmentToProductDescriptionFragment(id)
        findNavController().navigate(action)
    }
}