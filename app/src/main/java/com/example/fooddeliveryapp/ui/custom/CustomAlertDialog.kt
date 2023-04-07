package com.example.fooddeliveryapp.ui.custom

import android.app.Dialog
import android.content.Context
import android.view.Gravity
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import com.example.fooddeliveryapp.R

class CustomAlertDialog(context: Context) : Dialog(context) {

    private lateinit var streetEditText: EditText
    private lateinit var houseEditText: EditText
    private lateinit var apartmentEditText: EditText
    private lateinit var addAddressButton: Button
    private lateinit var onAddAddressListener: () -> Unit
    private val address = ""


    init {
        setContentView(R.layout.add_address_alert_dialog)

        window?.setBackgroundDrawableResource(android.R.color.transparent)
        window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        window?.setGravity(Gravity.CENTER)
        window?.decorView?.setPadding(50, 0, 50, 0)

        streetEditText = findViewById(R.id.street_edit_text)
        houseEditText = findViewById(R.id.house_edit_text)
        apartmentEditText = findViewById(R.id.apartment_edit_text)
        addAddressButton = findViewById(R.id.add_address_button)
        addAddressButton.setOnClickListener {
            onAddAddressListener.invoke()
        }
    }

    fun getAddress(): String {
        return context.getString(R.string.new_address,
            "${streetEditText.text}",
            "${houseEditText.text}",
            "${apartmentEditText.text}")
    }

    fun setOnAddAddressListener(callback: () -> Unit) {
        onAddAddressListener = callback
    }
}



