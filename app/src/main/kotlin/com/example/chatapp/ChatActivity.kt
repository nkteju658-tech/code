package com.example.chatapp

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ChatActivity : AppCompatActivity() {
    private lateinit var messageRecyclerView: RecyclerView
    private lateinit var messageInput: EditText
    private lateinit var sendButton: Button
    private lateinit var adapter: MessageAdapter
    private val messages = mutableListOf<Message>()
    private var chatId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        chatId = intent.getStringExtra("chatId")
        val chatName = intent.getStringExtra("chatName")
        supportActionBar?.title = chatName

        messageRecyclerView = findViewById(R.id.messageRecyclerView)
        messageInput = findViewById(R.id.messageInput)
        sendButton = findViewById(R.id.sendButton)

        adapter = MessageAdapter(messages, Firebase.auth.currentUser?.uid ?: "")
        messageRecyclerView.layoutManager = LinearLayoutManager(this)
        messageRecyclerView.adapter = adapter

        sendButton.setOnClickListener {
            sendMessage()
        }

        if (chatId != null) {
            loadMessages()
        }
    }

    private fun sendMessage() {
        val messageText = messageInput.text.toString().trim()
        if (messageText.isEmpty()) return

        val currentUser = Firebase.auth.currentUser ?: return
        val messageId = Firebase.database.reference.push().key ?: return

        val message = Message(
            id = messageId,
            senderId = currentUser.uid,
            senderName = currentUser.email ?: "Unknown",
            text = messageText,
            timestamp = System.currentTimeMillis()
        )

        CoroutineScope(Dispatchers.Default).launch {
            Firebase.database.reference
                .child("messages")
                .child(chatId!!)
                .child(messageId)
                .setValue(message)
        }

        messageInput.text.clear()
    }

    private fun loadMessages() {
        val messagesRef = Firebase.database.reference.child("messages").child(chatId!!)

        messagesRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                messages.clear()
                for (child in snapshot.children) {
                    val message = child.getValue(Message::class.java)
                    if (message != null) {
                        messages.add(message)
                    }
                }
                adapter.notifyDataSetChanged()
                messageRecyclerView.scrollToPosition(messages.size - 1)
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }
}
