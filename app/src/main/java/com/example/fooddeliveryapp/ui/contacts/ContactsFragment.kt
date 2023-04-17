package com.example.fooddeliveryapp.ui.contacts

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.fooddeliveryapp.DaggerApp
import com.example.fooddeliveryapp.R
import com.example.fooddeliveryapp.databinding.FragmentContactsBinding

class ContactsFragment : Fragment() {

    private var _binding: FragmentContactsBinding? = null
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
        _binding = FragmentContactsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupButtons()
    }

    private fun setupButtons() {
        binding.ivPhone.setOnClickListener {
            performCall()
        }
        binding.ivTelegram.setOnClickListener {
            copyTelegramUrl()
        }
        binding.ivVk.setOnClickListener {
            openVkLink()
        }
        binding.buttonNavigateToMap.setOnClickListener {
            openMapFragment()
        }
    }

    private fun openMapFragment() {
        val action =
            ContactsFragmentDirections.actionNavigationNotificationsToMapsRestaurantsFragment()
        findNavController().navigate(action)
    }

    private fun copyTelegramUrl() {
        val textToCopy = getString(R.string.telegram_url)
        val clipboard =
            requireContext().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        clipboard.setPrimaryClip(ClipData.newPlainText(null, textToCopy))
        Toast.makeText(
            requireContext(),
            getString(R.string.telegram_name_copied),
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun performCall() {
        val intent = Intent(
            Intent.ACTION_DIAL,
            Uri.parse(getString(R.string.owner_phone_number))
        )
        startActivity(intent)
    }

    private fun openVkLink() {
        val url = getString(R.string.vk_link)
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}