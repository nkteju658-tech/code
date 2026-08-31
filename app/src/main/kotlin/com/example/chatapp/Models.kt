package com.example.chatapp

data class Chat(
    val id: String? = null,
    val name: String = "",
    val lastMessage: String = "",
    val lastUpdated: Long = 0
)

data class Message(
    val id: String = "",
    val senderId: String = "",
    val senderName: String = "",
    val text: String = "",
    val timestamp: Long = 0
)
