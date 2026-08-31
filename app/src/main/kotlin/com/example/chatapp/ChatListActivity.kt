package com.example.chatapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class ChatListActivity : AppCompatActivity() {
    private lateinit var chatRecyclerView: RecyclerView
    private lateinit var newChatButton: Button
    private lateinit var logoutButton: Button
    private lateinit var adapter: ChatListAdapter
    private val chatList = mutableListOf<Chat>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_list)

        chatRecyclerView = findViewById(R.id.chatRecyclerView)
        newChatButton = findViewById(R.id.newChatButton)
        logoutButton = findViewById(R.id.logoutButton)

        adapter = ChatListAdapter(chatList) { chat ->
            openChat(chat)
        }
        chatRecyclerView.layoutManager = LinearLayoutManager(this)
        chatRecyclerView.adapter = adapter

        newChatButton.setOnClickListener {
            startActivity(Intent(this, NewChatActivity::class.java))
        }

        logoutButton.setOnClickListener {
            Firebase.auth.signOut()
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        loadChats()
    }

    private fun loadChats() {
        val currentUser = Firebase.auth.currentUser ?: return
        val chatsRef = Firebase.database.reference.child("chats").child(currentUser.uid)

        chatsRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                chatList.clear()
                for (child in snapshot.children) {
                    val chat = child.getValue(Chat::class.java)
                    if (chat != null) {
                        chat.id = child.key
                        chatList.add(chat)
                    }
                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }

    private fun openChat(chat: Chat) {
        val intent = Intent(this, ChatActivity::class.java)
        intent.putExtra("chatId", chat.id)
        intent.putExtra("chatName", chat.name)
        startActivity(intent)
    }
}
