package com.example.fooddeliveryapp.ui.home.open_product

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.fooddeliveryapp.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductDescriptionFragment : Fragment() {

    private val viewModel by viewModels<ProductDescriptionViewModel>()
    private val args: ProductDescriptionFragmentArgs by navArgs()
    private var price = 0.0
    private var parameter = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate((R.layout.fragment_product_description), container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val productName = view.findViewById<TextView>(R.id.tvProductName)
        val productDescription = view.findViewById<TextView>(R.id.tvProductDescription)
        val productImage = view.findViewById<ImageView>(R.id.ivProductImage)
        val productBtnToCart = view.findViewById<Button>(R.id.btnToCart)
        val productParameter = view.findViewById<RadioGroup>(R.id.rgFirstSelect)


        viewModel.liveData.observe(viewLifecycleOwner) {
            productBtnToCart.setOnClickListener {
                viewModel.liveData.value?.let { product ->
                    viewModel.addToCard(product, price, 1, parameter)
                }
                val navHostFragment = requireActivity().supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment
                val navOptions = NavOptions.Builder()
                    .setExitAnim(R.anim.add_to_cart_anim)
                    .build()
                navHostFragment.navController.navigate(R.id.navigation_home, null, navOptions)
            }
            productName.text = it.name
            productDescription.text = it.description
            setImage(it.imageUrl, productImage)
        }

        viewModel.firstPriceLiveData.observe(viewLifecycleOwner) {
            price = it
            productBtnToCart.text =
                "${resources.getText(R.string.in_cart)} $price ${resources.getText(R.string.currency)}"

        }

        viewModel.parametersLiveData.observe(viewLifecycleOwner) { parametersList ->
            for (buttonNumber in 0 until productParameter.childCount) {
                val radioButton = productParameter.getChildAt(buttonNumber) as RadioButton
                radioButton.text = parametersList[buttonNumber]
            }
            parameter = parametersList[0]
        }

        viewModel.priceLiveData.observe(viewLifecycleOwner) { price ->
            productBtnToCart.text =
                "${resources.getText(R.string.in_cart)} $price ${resources.getText(R.string.currency)}"
            this.price = price
        }

        productParameter.setOnCheckedChangeListener { _, checkedId ->
            parameter = view.findViewById<RadioButton>(checkedId).text.toString()
            viewModel.getPriceByParameter(args.id, parameter)
        }


        viewModel.getProduct(args.id)
    }

    private fun setImage(url: String, image: ImageView) {
        Glide.with(image)
            .load(url)
            .into(image)
    }
}