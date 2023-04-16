package com.example.fooddeliveryapp.ui.authentication

import android.content.Context
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
import com.example.fooddeliveryapp.DaggerApp
import com.example.fooddeliveryapp.R
import com.example.fooddeliveryapp.databinding.FragmentLogInBinding
import com.example.fooddeliveryapp.di.viewModel.ViewModelFactory
import ru.tinkoff.decoro.MaskImpl
import ru.tinkoff.decoro.parser.UnderscoreDigitSlotsParser
import ru.tinkoff.decoro.watchers.FormatWatcher
import ru.tinkoff.decoro.watchers.MaskFormatWatcher
import javax.inject.Inject

class LogInFragment : Fragment() {

    @Inject
    lateinit var factory: ViewModelFactory
    private val viewModel: LogInViewModel by viewModels { factory }
    private var _binding: FragmentLogInBinding? = null
    private val binding get() = _binding!!
    private val textWatcher =
        object : TextWatcher {
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

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().applicationContext as DaggerApp).appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentLogInBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun navigateToSignUp() {
        val action = LogInFragmentDirections.actionLogInFragmentToSingUpFragment()
        findNavController().navigate(action)
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
            binding.tvLogInError.isVisible = !it
            if (it) {
                navigateToSuccessAuthenticationFragment()
            }
        }
    }

    private fun navigateToSuccessAuthenticationFragment() {
        val action =
            LogInFragmentDirections.actionLogInFragmentToSuccessAuthenticationFragment()
        findNavController().navigate(action)
    }

    private fun hasTextIn() {
        checkEditText(binding.etNumber)
        checkEditText(binding.etPassword)
    }

    private fun decorateEditText() {
        val numberMask =
            MaskImpl.createTerminated(UnderscoreDigitSlotsParser().parseSlots(FORMAT_NUMBER))
        numberMask.isHideHardcodedHead = true
        val numberFormatWatcher: FormatWatcher = MaskFormatWatcher(numberMask)
        numberFormatWatcher.installOn(binding.etNumber)
    }

    private fun setupButton() {
        with(binding) {
            button.setOnClickListener {
                if (areAllEditTextsFilled() && isNumberRight() && isPasswordRight()) {
                    viewModel.login(etNumber.text.toString(), etPassword.text.toString())
                }
            }
            tvHaveAccount.setOnClickListener { navigateToSignUp() }
        }
    }

    private fun isPasswordRight(): Boolean {
        if (!binding.etPassword.text.matches(REGEX_PASSWORD.toRegex())) {
            binding.etPassword.error = getString(R.string.password_error)
            return false
        }
        return true
    }

    private fun isNumberRight(): Boolean {
        if (!binding.etNumber.text.matches(REGEX_NUMBER.toRegex())) {
            binding.etNumber.error = getString(R.string.number_error)
            return false
        }
        return true
    }

    private fun areAllEditTextsFilled(): Boolean {
        var allFilled = true
        for (view in requireView().allViews) {
            if (view is EditText) {
                val text = view.text.toString().trim()
                if (text.isEmpty()) {
                    view.error = getString(R.string.error_required_field)
                    allFilled = false
                }
            }
        }
        return allFilled
    }

    private fun checkEditText(editText: EditText) {
        editText.addTextChangedListener(textWatcher)
    }

    private fun checkButton() {
        with(binding) {
            if (etNumber.text.isNotEmpty() &&
                etPassword.text.isNotEmpty()
            ) {
                button.setBackgroundResource(R.drawable.bg_button_authentication_enabled)
            } else {
                button.setBackgroundResource(R.drawable.bg_button_authentication_disabled)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val REGEX_PASSWORD = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)\\w{8,}\$"
        private const val REGEX_NUMBER = "^\\+375 (25|44|33|29)\\s\\d{3}-\\d{2}-\\d{2}\$"
        private const val FORMAT_NUMBER = "+375 __ ___-__-__"
    }
}
