package com.example.fooddeliveryapp.ui.profile.user_profile

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.fooddeliveryapp.DaggerApp
import com.example.fooddeliveryapp.databinding.FragmentUserProfileBinding
import com.example.fooddeliveryapp.di.viewModel.ViewModelFactory
import javax.inject.Inject

class UserProfileFragment : Fragment() {

    @Inject
    lateinit var factory: ViewModelFactory
    private val viewModel: UserProfileViewModel by viewModels { factory }
    private var _binding: FragmentUserProfileBinding? = null
    private val binding get() = _binding!!

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().applicationContext as DaggerApp).appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentUserProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getUser()
        observeLiveData()
        setupButtons()
    }

    private fun setupButtons() {
        binding.ivBack.setOnClickListener { navigateBack() }
    }

    private fun observeLiveData() {
        viewModel.userLiveData.observe(viewLifecycleOwner) {
            binding.tvUserName.text = it.name
            binding.tvUserPhone.text = it.number
            binding.tvUserDate.text = it.date
            binding.tvUserDateReg.text = it.dateRegistration
        }
    }

    private fun navigateBack() {
        requireActivity().onBackPressedDispatcher.onBackPressed()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}