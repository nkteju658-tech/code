package com.example.chatapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class MainActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var emailInput: EditText
    private lateinit var passwordInput: EditText
    private lateinit var loginButton: Button
    private lateinit var signupButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = Firebase.auth
        emailInput = findViewById(R.id.emailInput)
        passwordInput = findViewById(R.id.passwordInput)
        loginButton = findViewById(R.id.loginButton)
        signupButton = findViewById(R.id.signupButton)

        // Check if user is already logged in
        if (auth.currentUser != null) {
            navigateToChatList()
        }

        loginButton.setOnClickListener {
            login()
        }

        signupButton.setOnClickListener {
            signup()
        }
    }

    private fun login() {
        val email = emailInput.text.toString().trim()
        val password = passwordInput.text.toString().trim()

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please enter email and password", Toast.LENGTH_SHORT).show()
            return
        }

        CoroutineScope(Dispatchers.Main).launch {
            try {
                auth.signInWithEmailAndPassword(email, password).await()
                navigateToChatList()
            } catch (e: Exception) {
                Toast.makeText(this@MainActivity, "Login failed: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun signup() {
        val email = emailInput.text.toString().trim()
        val password = passwordInput.text.toString().trim()

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please enter email and password", Toast.LENGTH_SHORT).show()
            return
        }

        CoroutineScope(Dispatchers.Main).launch {
            try {
                auth.createUserWithEmailAndPassword(email, password).await()
                navigateToChatList()
            } catch (e: Exception) {
                Toast.makeText(this@MainActivity, "Signup failed: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun navigateToChatList() {
        startActivity(Intent(this, ChatListActivity::class.java))
        finish()
    }
}
