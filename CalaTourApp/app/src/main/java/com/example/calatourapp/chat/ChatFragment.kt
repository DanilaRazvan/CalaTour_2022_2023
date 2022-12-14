package com.example.calatourapp.chat

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.calatourapp.databinding.FragmentChatBinding
import java.util.*

class ChatFragment : Fragment() {

    private var _binding: FragmentChatBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ChatViewModel by viewModels()

    private lateinit var chatMessageAdapter: ChatMessageAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChatBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupView()
        observeState()
    }

    private fun setupView() {
        chatMessageAdapter = ChatMessageAdapter()
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = chatMessageAdapter
        }

        binding.etMessage.doOnTextChanged { text, start, before, count ->
            viewModel.onMessageChanged(text.toString())
        }

        binding.btnSend.setOnClickListener {
            viewModel.onSendMessage()
            binding.etMessage.text.clear()
        }

        Timer().scheduleAtFixedRate(object: TimerTask() {
            override fun run() {
                viewModel.onReadMessages()
            }
        }, 0, 3000)
    }

    private fun observeState() {
        viewLifecycleOwner.lifecycleScope.launchWhenResumed {
            viewModel.viewState.collect { state ->
                binding.tvUsername.text = state.user
                chatMessageAdapter.insertMessages(state.messages)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}