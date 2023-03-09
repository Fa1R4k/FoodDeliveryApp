package com.example.fooddeliveryapp.ui.home.open_product

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.fooddeliveryapp.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductDescriptionFragment : Fragment() {

    private val viewModel by viewModels<ProductDescriptionViewModel>()
    private val args: ProductDescriptionFragmentArgs by navArgs()

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
        val firstProductParameter = view.findViewById<RadioGroup>(R.id.rgFirstSelect)
        val secondProductParameter = view.findViewById<RadioGroup>(R.id.rgSecondSelect)
        val productPrice = 0.0
        val productParameter = ""

        firstProductParameter.setOnCheckedChangeListener { _, checkedId ->
            val button = view.findViewById<RadioButton>(checkedId)
            Toast.makeText(requireContext(), "Выбран вариант ${button.text}", Toast.LENGTH_SHORT).show()
        }

        viewModel.liveData.observe(viewLifecycleOwner) {
            productBtnToCart.setOnClickListener {
                viewModel.liveData.value?.let { product ->
                    viewModel.addToCard(product, 1, "sd")
                }
            }

            productName.text = it.name
            productDescription.text = it.description
            productBtnToCart.text = it.prise.toString()
            setImage(it.imageUrl, productImage)
        }
        viewModel.getProduct(args.id)
    }

    private fun setImage(url: String, image: ImageView) {
        Glide.with(image)
            .load(url)
            .into(image)
    }



    private fun checkButton(v : View){
        val firstProductParameter = v.findViewById<RadioGroup>(R.id.rgFirstSelect)
        val rad = v.findViewById<RadioButton>(firstProductParameter.checkedRadioButtonId)

        Toast.makeText(requireContext(), rad.text.toString(),Toast.LENGTH_SHORT).show()
    }
}