package com.example.fooddeliveryapp.ui.authentication

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.view.allViews
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.fooddeliveryapp.R
import com.example.fooddeliveryapp.databinding.FragmentSignUpBinding
import dagger.hilt.android.AndroidEntryPoint
import ru.tinkoff.decoro.MaskImpl
import ru.tinkoff.decoro.parser.UnderscoreDigitSlotsParser
import ru.tinkoff.decoro.watchers.FormatWatcher
import ru.tinkoff.decoro.watchers.MaskFormatWatcher
import java.util.*

@AndroidEntryPoint
class SignUpFragment : Fragment() {

    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<SingUpViewModel>()
    private val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(
            s: CharSequence?,
            start: Int,
            count: Int,
            after: Int,
        ) {
        }
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            checkButton()
        }
        override fun afterTextChanged(s: Editable?) {
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        hasTextIn()
        setupButton()
        decorateEditText()
        observeLiveDate()
    }

    private fun observeLiveDate() {
        viewModel.liveData.observe(viewLifecycleOwner) {
            binding.tvSignUpError.isVisible = !it
            if (it) navigateToSuccessAuthenticationFragment()
        }
    }

    private fun navigateToSuccessAuthenticationFragment() {
        val action =
            SignUpFragmentDirections.actionSingUpFragmentToSuccessAuthenticationFragment()
        findNavController().navigate(action)
    }

    private fun decorateEditText() {
        val numberMask =
            MaskImpl.createTerminated(UnderscoreDigitSlotsParser().parseSlots(FORMAT_NUMBER))
        numberMask.isHideHardcodedHead = true
        val numberFormatWatcher: FormatWatcher = MaskFormatWatcher(numberMask)
        numberFormatWatcher.installOn(binding.etPhone)

        val dateMask =
            MaskImpl.createTerminated(UnderscoreDigitSlotsParser().parseSlots(FORMAT_DATE))
        numberMask.isHideHardcodedHead = true
        val dateFormatWatcher: FormatWatcher = MaskFormatWatcher(dateMask)
        dateFormatWatcher.installOn(binding.etDate)
    }

    private fun setupButton() {
        with(binding) {
            button.setOnClickListener {
                if (areAllEditTextsFilled() && isNumberRight() && isPasswordRight() && isDateRight()) {
                    viewModel.createNewUser(etUserName.text.toString(),
                        etPhone.text.toString(),
                        etPassword.text.toString(),
                        etDate.text.toString())
                }
            }
            tvHaveAccount.setOnClickListener { navigateToLogIn() }
        }
    }

    private fun isDateRight(): Boolean {
        if (!binding.etDate.text.matches(REGEX_DATE.toRegex()) || binding.etDate.text.split(".")
                .last().toInt() > CURRENT_YEAR || binding.etDate.text.split(".").last()
                .toInt() < MIN_YEAR
        ) {
            binding.etDate.error = getString(R.string.data_error)
            return false
        }
        return true
    }

    private fun isPasswordRight(): Boolean {
        if (!binding.etPassword.text.matches(REGEX_PASSWORD.toRegex())) {
            binding.etPassword.error = getString(R.string.password_error)
            return false
        }
        return true
    }

    private fun isNumberRight(): Boolean {
        if (!binding.etPhone.text.matches(REGEX_NUMBER.toRegex())) {
            binding.etPhone.error = getString(R.string.number_error)
            return false
        }
        return true
    }

    private fun navigateToLogIn() {
        val action = SignUpFragmentDirections.actionSingUpFragmentToLogInFragment()
        findNavController().navigate(action)
    }

    private fun hasTextIn() {
        checkEditText(binding.etUserName)
        checkEditText(binding.etPhone)
        checkEditText(binding.etPassword)
        checkEditText(binding.etDate)

    }

    private fun checkEditText(editText: EditText) {
        editText.addTextChangedListener(textWatcher)
    }

    private fun checkButton() {
        with(binding) {
            if (etUserName.text.isNotEmpty() && etPhone.text.isNotEmpty() && etPassword.text.isNotEmpty() && etDate.text.isNotEmpty()) {
                button.setBackgroundResource(R.drawable.button_authentication_enabled)
            } else {
                button.setBackgroundResource(R.drawable.button_authentication_disabled)
            }
        }
    }

    private fun areAllEditTextsFilled(): Boolean {
        var allFilled = true
        for (view in requireView().allViews) {
            if (view is EditText) {
                val text = view.text.toString().trim()
                if (text.isEmpty()) {
                    view.error = getString(R.string.erro_required_field)
                    allFilled = false
                }
            }
        }
        return allFilled
    }

    companion object {
        private const val REGEX_PASSWORD = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)\\w{8,}\$"
        private const val REGEX_DATE = "^(0[1-9]|[12]\\d|3[01])\\.(0[1-9]|1[0-2])\\.\\d{4}\$"
        private const val REGEX_NUMBER = "^\\+375 (25|44|33|29)\\s\\d{3}-\\d{2}-\\d{2}\$"
        private const val FORMAT_DATE = "__.__.____"
        private const val FORMAT_NUMBER = "+375 __ ___-__-__"
        private const val MAX_PEOPLE_LIVE = 100
        private val CURRENT_YEAR = Calendar.getInstance().get(Calendar.YEAR).toString().toInt()
        private val MIN_YEAR = CURRENT_YEAR - MAX_PEOPLE_LIVE
    }
}