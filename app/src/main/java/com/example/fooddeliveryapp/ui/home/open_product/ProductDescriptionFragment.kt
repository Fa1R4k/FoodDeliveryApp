package com.example.fooddeliveryapp.ui.home.open_product

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.fooddeliveryapp.R
import com.example.fooddeliveryapp.databinding.FragmentProductDescriptionBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductDescriptionFragment : Fragment(R.layout.fragment_product_description) {

    private var _binding: FragmentProductDescriptionBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<ProductDescriptionViewModel>()
    private val args: ProductDescriptionFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentProductDescriptionBinding.inflate(inflater, container, false)
        return binding.root
    }

    private var price = 0.0
    private var selectedParameter = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpListeners()
        observeLiveData()
        viewModel.getProduct(args.id)
    }

    private fun setUpListeners() {
        binding.btnToCart.setOnClickListener {
            viewModel.liveData.value?.let { product ->
                viewModel.addToCard(product, price, 1, selectedParameter)
            }
            navigateToHomeScreen()
        }

        binding.rgFirstSelect.setOnCheckedChangeListener { _, checkedId ->
            val selectedRadioButton = view?.findViewById<RadioButton>(checkedId)
            selectedParameter = selectedRadioButton?.text.toString()
            viewModel.getPriceByParameter(args.id, selectedParameter)
        }

        binding.ivBack.setOnClickListener {
            backToHome()
        }
    }

    private fun observeLiveData() {
        viewModel.liveData.observe(viewLifecycleOwner) { product ->
            binding.tvProductName.text = product.name
            binding.tvProductDescription.text = product.description
            setImage(product.imageUrl, binding.ivProductImage)
        }

        viewModel.firstPriceLiveData.observe(viewLifecycleOwner) { price ->
            this.price = price
            binding.btnToCart.text =
                "${resources.getText(R.string.in_cart)} $price ${resources.getText(R.string.currency)}"
        }

        viewModel.parametersLiveData.observe(viewLifecycleOwner) { parametersList ->
            setRadioBottom(parametersList)
        }

        viewModel.priceLiveData.observe(viewLifecycleOwner) { price ->
            this.price = price
            binding.btnToCart.text =
                "${resources.getText(R.string.in_cart)} $price ${resources.getText(R.string.currency)}"
        }
    }

    private fun setRadioBottom(parametersList: List<String>) {
        try {
            for (buttonNumber in 0 until binding.rgFirstSelect.childCount) {
                val radioButton = binding.rgFirstSelect.getChildAt(buttonNumber) as RadioButton
                radioButton.text = parametersList[buttonNumber]
            }
            selectedParameter = parametersList[0]
        } catch (_: Exception) {
            binding.rgFirstSelect.isVisible = false
        }
    }

    private fun backToHome() {
        requireActivity().onBackPressed()
    }

    private fun navigateToHomeScreen() {
        val navHostFragment =
            requireActivity().supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment
        val navOptions = NavOptions.Builder().setExitAnim(R.anim.add_to_cart_anim).build()
        navHostFragment.navController.navigate(R.id.navigation_home, null, navOptions)
    }

    private fun setImage(url: String, image: ImageView) {
        Glide.with(image).load(url).into(image)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
