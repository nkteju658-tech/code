package com.example.chatapp

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NewChatActivity : AppCompatActivity() {
    private lateinit var chatNameInput: EditText
    private lateinit var createButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_chat)

        chatNameInput = findViewById(R.id.chatNameInput)
        createButton = findViewById(R.id.createButton)

        createButton.setOnClickListener {
            createChat()
        }
    }

    private fun createChat() {
        val chatName = chatNameInput.text.toString().trim()
        if (chatName.isEmpty()) {
            Toast.makeText(this, "Please enter a chat name", Toast.LENGTH_SHORT).show()
            return
        }

        val currentUser = Firebase.auth.currentUser ?: return
        val chatId = Firebase.database.reference.push().key ?: return

        val chat = Chat(
            id = chatId,
            name = chatName,
            lastMessage = "",
            lastUpdated = System.currentTimeMillis()
        )

        CoroutineScope(Dispatchers.Default).launch {
            Firebase.database.reference
                .child("chats")
                .child(currentUser.uid)
                .child(chatId)
                .setValue(chat)
                .addOnSuccessListener {
                    Toast.makeText(this@NewChatActivity, "Chat created!", Toast.LENGTH_SHORT).show()
                    finish()
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this@NewChatActivity, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                }
        }
    }
}
