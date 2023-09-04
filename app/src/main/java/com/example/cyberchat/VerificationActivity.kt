package com.example.cyberchat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class VerificationActivity : AppCompatActivity() {

    private lateinit var myAuth:FirebaseAuth
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var loginButton: Button
    private lateinit var signUpTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verification)
        initializeViews()


        signUpTextView.setOnClickListener {
            isEmailValid()
        }
    }

    private fun initializeViews() {
        emailEditText = findViewById(R.id.emailEditTextId)
        passwordEditText = findViewById(R.id.passwordEditTextId)
        loginButton = findViewById(R.id.loginButtonId)
        signUpTextView = findViewById(R.id.signupLinkId)
        signUpTextView.isClickable = true
        myAuth = FirebaseAuth.getInstance()

    }

    private fun signUp(emailPhone:String, password:String) {

        if (emailPhone.isNotEmpty() && password.isNotEmpty()) {
            myAuth.createUserWithEmailAndPassword(emailPhone, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Registration successful
                        Toast.makeText(this, "Registration successful", Toast.LENGTH_SHORT).show()
                    } else {
                        // Registration failed
                        Toast.makeText(this, "Registration failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                    }
                }

        } else {

            Toast.makeText(this, "Password or Email/Phone is empty", Toast.LENGTH_SHORT).show()
        }


    }
    // Validate a email
    private fun isEmailValid(){
        // signup email & password
        val email = emailEditText.text.toString()
        val password = passwordEditText.text.toString()

        val emailPattern = "[a-zA-Z0-9._%+-]+@(gmail\\.com|outlook\\.com)"
        val valid = email.matches(emailPattern.toRegex())

        // Email validator
        if (valid) {
            signUp(email, password)
        } else {
            Toast.makeText(this, "email is not valid", Toast.LENGTH_SHORT).show()
        }
    }

    // Validate a password
    private fun isPasswordValid(password: String): Boolean {
        val passwordPattern = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@#$%^&+=])(?=\\S+$).{8,}$"
        return password.matches(passwordPattern.toRegex())
    }



}
