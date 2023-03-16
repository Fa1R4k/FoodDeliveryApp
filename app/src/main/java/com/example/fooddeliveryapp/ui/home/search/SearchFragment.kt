package com.example.fooddeliveryapp.ui.home.search

import android.os.Bundle
import android.view.*
import androidx.fragment.app.DialogFragment
import com.example.fooddeliveryapp.R

class SearchFragment : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.search_fragment, container, false)
    }

}