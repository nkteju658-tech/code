package com.example.chatapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ChatListAdapter(
    private val chats: List<Chat>,
    private val onChatClick: (Chat) -> Unit
) : RecyclerView.Adapter<ChatListAdapter.ChatViewHolder>() {

    inner class ChatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameTextView: TextView = itemView.findViewById(R.id.chatName)
        private val messageTextView: TextView = itemView.findViewById(R.id.lastMessage)

        fun bind(chat: Chat) {
            nameTextView.text = chat.name
            messageTextView.text = chat.lastMessage
            itemView.setOnClickListener { onChatClick(chat) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_chat, parent, false)
        return ChatViewHolder(view)
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        holder.bind(chats[position])
    }

    override fun getItemCount() = chats.size
}

class MessageAdapter(
    private val messages: List<Message>,
    private val currentUserId: String
) : RecyclerView.Adapter<MessageAdapter.MessageViewHolder>() {

    inner class MessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val messageTextView: TextView = itemView.findViewById(R.id.messageText)
        private val senderTextView: TextView = itemView.findViewById(R.id.senderName)

        fun bind(message: Message) {
            messageTextView.text = message.text
            senderTextView.text = message.senderName

            if (message.senderId == currentUserId) {
                itemView.setBackgroundColor(itemView.context.getColor(android.R.color.holo_blue_light))
            } else {
                itemView.setBackgroundColor(itemView.context.getColor(android.R.color.holo_green_light))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_message, parent, false)
        return MessageViewHolder(view)
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        holder.bind(messages[position])
    }

    override fun getItemCount() = messages.size
}
