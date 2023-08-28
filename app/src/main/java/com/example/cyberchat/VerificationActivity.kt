package com.example.cyberchat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class VerificationActivity : AppCompatActivity() {

    private lateinit var loginUserName: EditText
    private lateinit var loginPassword: EditText
    private lateinit var loginButton: Button
    private lateinit var outputResult: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verification)
        initializeViews()
        setupLoginButton()
    }

    private fun initializeViews() {
        loginUserName = findViewById(R.id.usernameEditText)
        loginPassword = findViewById(R.id.passwordEditText)
        loginButton = findViewById(R.id.loginButton)
        outputResult = findViewById(R.id.output)
    }

    private fun setupLoginButton() {
        loginButton.setOnClickListener {
            val welcomeMessage = "Welcome ${loginUserName.text}"
            outputResult.text = welcomeMessage
        }
    }
}
