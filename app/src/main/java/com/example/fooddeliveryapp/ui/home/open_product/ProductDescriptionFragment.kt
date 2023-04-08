package com.example.fooddeliveryapp.ui.home.open_product

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.RadioButton
import androidx.core.view.allViews
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.fooddeliveryapp.R
import com.example.fooddeliveryapp.databinding.FragmentProductDescriptionBinding
import com.example.fooddeliveryapp.domain.model.ProductItem
import com.google.android.material.bottomnavigation.BottomNavigationView
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
    private lateinit var productItem: ProductItem.ProductData

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeLoadingLiveData()
        observeLiveData()
        setUpListeners()
        viewModel.getProduct(args.productId)
    }

    private fun observeLoadingLiveData() {
        viewModel.loadingLiveData.observe(viewLifecycleOwner) {
            binding.loading.isVisible = it
            binding.fragment.isVisible = !it
        }
    }

    private fun setUpListeners() {
        binding.btnToCart.setOnClickListener {
            viewModel.liveData.value?.let { product ->
                viewModel.addToCard(product, price, 1, selectedParameter)
            }
            backToHomeWithAnimateToCart()
        }

        binding.rgFirstSelect.setOnCheckedChangeListener { _, checkedId ->
            val selectedRadioButton = binding.rgFirstSelect.findViewById<RadioButton>(checkedId)
            selectedParameter = selectedRadioButton?.text.toString()
            this.price = productItem.price[selectedParameter] ?: 0.0

            binding.btnToCart.text =
                getString(R.string.in_cart, price.toString())
        }


        binding.ivBack.setOnClickListener {
            backToHome()
        }
    }

    private fun backToHomeWithAnimateToCart() {
        navigateToHomeScreen()
    }

    private fun observeLiveData() {
        viewModel.liveData.observe(viewLifecycleOwner) {
            productItem = it
            binding.tvProductName.text = productItem.name
            binding.tvProductDescription.text = productItem.description
            setImage(productItem.imageUrl, binding.ivProductImage)
            price = productItem.price.values.first()
            binding.btnToCart.text = getString(R.string.in_cart, price.toString())
        }

        viewModel.parametersLiveData.observe(viewLifecycleOwner) { parametersList ->
            setRadioBottom(parametersList)
        }

        viewModel.priceLiveData.observe(viewLifecycleOwner) { price ->
            this.price = price
            binding.btnToCart.text = getString(R.string.in_cart, price.toString())
        }
    }

    private fun setRadioBottom(parametersList: List<String>) {
        if (parametersList.size == 1) {
            binding.rgFirstSelect.isVisible = false
        } else {
            for (buttonNumber in 0 until binding.rgFirstSelect.childCount) {
                val radioButton = binding.rgFirstSelect.getChildAt(buttonNumber) as RadioButton
                radioButton.text = parametersList[buttonNumber]
            }
            selectedParameter = parametersList[0]
        }
    }

    private fun backToHome() {
        requireActivity().onBackPressedDispatcher.onBackPressed()
    }

    private fun navigateToHomeScreen() {
        binding.fragment.isVisible = false
        binding.ivProductImage.isVisible = true
        val animation: Animation =
            AnimationUtils.loadAnimation(context, R.anim.add_to_cart_anim)
        binding.ivProductImage.startAnimation(animation)
        findNavController().popBackStack()
    }

    private fun setImage(url: String, image: ImageView) {
        Glide.with(image).load(url).into(image)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
