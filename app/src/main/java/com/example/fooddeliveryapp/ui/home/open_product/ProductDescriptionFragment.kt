package com.example.fooddeliveryapp.ui.home.open_product

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.fooddeliveryapp.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductDescriptionFragment : Fragment(R.layout.fragment_product_description) {

    private val viewModel by viewModels<ProductDescriptionViewModel>()
    private val args: ProductDescriptionFragmentArgs by navArgs()

    private lateinit var productNameTextView: TextView
    private lateinit var productDescriptionTextView: TextView
    private lateinit var productImageView: ImageView
    private lateinit var productToCartButton: Button
    private lateinit var productParameterRadioGroup: RadioGroup
    private lateinit var backImageView: ImageView

    private var price = 0.0
    private var selectedParameter = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindViews(view)
        setUpListeners()
        observeLiveData()
        viewModel.getProduct(args.id)
    }

    private fun bindViews(view: View) {
        productNameTextView = view.findViewById(R.id.tvProductName)
        productDescriptionTextView = view.findViewById(R.id.tvProductDescription)
        productImageView = view.findViewById(R.id.ivProductImage)
        productToCartButton = view.findViewById(R.id.btnToCart)
        productParameterRadioGroup = view.findViewById(R.id.rgFirstSelect)
        backImageView = view.findViewById(R.id.ivBack)
    }

    private fun setUpListeners() {
        productToCartButton.setOnClickListener {
            viewModel.liveData.value?.let { product ->
                viewModel.addToCard(product, price, 1, selectedParameter)
            }
            navigateToHomeScreen()
        }

        productParameterRadioGroup.setOnCheckedChangeListener { _, checkedId ->
            val selectedRadioButton = view?.findViewById<RadioButton>(checkedId)
            selectedParameter = selectedRadioButton?.text.toString()
            viewModel.getPriceByParameter(args.id, selectedParameter)
        }

        backImageView.setOnClickListener {
            back()
        }
    }

    private fun observeLiveData() {
        viewModel.liveData.observe(viewLifecycleOwner) { product ->
            productNameTextView.text = product.name
            productDescriptionTextView.text = product.description
            setImage(product.imageUrl, productImageView)
        }

        viewModel.firstPriceLiveData.observe(viewLifecycleOwner) { price ->
            this.price = price
            productToCartButton.text =
                "${resources.getText(R.string.in_cart)} $price ${resources.getText(R.string.currency)}"
        }

        viewModel.parametersLiveData.observe(viewLifecycleOwner) { parametersList ->
            setRadioBottom(parametersList)
        }

        viewModel.priceLiveData.observe(viewLifecycleOwner) { price ->
            this.price = price
            productToCartButton.text =
                "${resources.getText(R.string.in_cart)} $price ${resources.getText(R.string.currency)}"
        }
    }

    private fun setRadioBottom(parametersList: List<String>) {
        try {
            for (buttonNumber in 0 until productParameterRadioGroup.childCount) {
                val radioButton = productParameterRadioGroup.getChildAt(buttonNumber) as RadioButton
                radioButton.text = parametersList[buttonNumber]
            }
            selectedParameter = parametersList[0]
        } catch (_: Exception) {
            productParameterRadioGroup.isVisible = false
        }
    }

    private fun back() {
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
}
