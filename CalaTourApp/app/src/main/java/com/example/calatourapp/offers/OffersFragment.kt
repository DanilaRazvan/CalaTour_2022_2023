package com.example.calatourapp.offers

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.activity.addCallback
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.calatourapp.R
import com.example.calatourapp.databinding.FragmentOffersBinding
import java.util.*

class OffersFragment : Fragment() {

    private var _binding: FragmentOffersBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<OffersViewModel>()

    private val adapter = OffersAdapter(
        object : OffersAdapter.OffersClickListener {
            override fun onAdd(index: Int) {
                viewModel.addOffer(index)
            }

            override fun onRemove(id: UUID) {
                viewModel.removeOfferById(id)
            }

            override fun itemSelected(id: UUID) {
                val direction = OffersFragmentDirections.actionOffersFragmentToOfferDetailsFragment(
                    id.toString()
                )
                findNavController().navigate(direction)
            }
        }
    )

    private lateinit var onBackPressedCallback: OnBackPressedCallback
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        onBackPressedCallback = requireActivity().onBackPressedDispatcher.addCallback(this) {
            val dialogBuilder = AlertDialog.Builder(requireContext())
            dialogBuilder
                .setTitle("Please confirm")
                .setMessage("Are you sure?")
                .setPositiveButton("Sign out") { _, _ ->
                    findNavController().navigateUp()
                }
                .setNegativeButton("Cancel", null)
                .create()
                .show()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOffersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupView()
        observeState()
    }

    private fun setupView() {
        binding.list.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = this@OffersFragment.adapter
        }

        binding.toolbar.setNavigationOnClickListener {
            onBackPressedCallback.handleOnBackPressed()
        }

        binding.toolbar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.sign_out -> {
                    onBackPressedCallback.handleOnBackPressed()
                    return@setOnMenuItemClickListener true
                }
                R.id.reset_list -> {
                    viewModel.resetList()
                    return@setOnMenuItemClickListener true
                }
                R.id.clear_favorites -> {
                    return@setOnMenuItemClickListener true
                }
                R.id.chat_with_us -> {
                    val direction = OffersFragmentDirections.actionOffersFragmentToChatFragment()
                    findNavController().navigate(direction)
                    true
                }
                else -> false
            }
        }
    }

    private fun observeState() {
        viewLifecycleOwner.lifecycleScope.launchWhenResumed {
            viewModel.viewState.collect { viewState ->

                binding.loading.visibility =
                    if (viewState.isLoading) View.VISIBLE
                    else View.GONE
                binding.list.visibility =
                    if (viewState.isLoading) View.GONE
                    else View.VISIBLE


                adapter.setData(viewState.offers)
            }
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}