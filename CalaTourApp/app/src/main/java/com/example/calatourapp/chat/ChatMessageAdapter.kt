package com.example.calatourapp.chat

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.calatourapp.api.data.ChatMessage
import com.example.calatourapp.databinding.FragmentChatListItemBinding

class ChatMessageAdapter : RecyclerView.Adapter<ChatMessageAdapter.ChatMessageViewHolder>() {

    private val dataSource = mutableListOf<ChatMessage>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatMessageViewHolder {
        val binding = FragmentChatListItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return ChatMessageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ChatMessageViewHolder, position: Int) {
        with(holder) {
            val item = dataSource[position]
            binding.tvAuthor.text = item.sender
            binding.tvMessage.text = item.text
            binding.tvTimestamp.text = item.timestamp
//            binding.ivStar.visibility = if (item.isImportant) View.VISIBLE else View.GONE
//
//            binding.root.setOnClickListener {
//                item.isImportant = !item.isImportant
//                notifyDataSetChanged()
//            }
        }
    }

    override fun getItemCount(): Int = dataSource.size

    fun insertMessages(messages: List<ChatMessage>) {
        dataSource.clear()
        dataSource.addAll(messages)
        this.notifyDataSetChanged()
    }

    inner class ChatMessageViewHolder(val binding: FragmentChatListItemBinding) : RecyclerView.ViewHolder(binding.root)
}