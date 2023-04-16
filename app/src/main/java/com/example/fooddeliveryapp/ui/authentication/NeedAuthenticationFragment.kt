package com.example.fooddeliveryapp.ui.authentication

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.fooddeliveryapp.DaggerApp
import com.example.fooddeliveryapp.databinding.FragmentNeedAuthenticationBinding

class NeedAuthenticationFragment : Fragment() {
    private var _binding: FragmentNeedAuthenticationBinding? = null
    private val binding get() = _binding!!

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().applicationContext as DaggerApp).appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentNeedAuthenticationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupButtons()
    }

    private fun setupButtons() {
        binding.btnSignUpUser.setOnClickListener { navigateToSignUp() }
        binding.tvLogInUser.setOnClickListener { navigateToLogIn() }
        binding.ivBack.setOnClickListener { back() }
    }

    private fun navigateToSignUp() {
        val action =
            NeedAuthenticationFragmentDirections.actionNeedAuthenticationFragmentToSingUpFragment()
        findNavController().navigate(action)
    }

    private fun navigateToLogIn() {
        val action =
            NeedAuthenticationFragmentDirections.actionNeedAuthenticationFragmentToLogInFragment()
        findNavController().navigate(action)
    }

    private fun back() {
        requireActivity().onBackPressedDispatcher.onBackPressed()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
