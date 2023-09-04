package com.example.cyberchat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class LoginSignupActivity : AppCompatActivity() {

    private lateinit var myAuth:FirebaseAuth
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var loginButton: Button
    private lateinit var signUpTextView: TextView
    private var email: String = ""
    private var password: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verification)
        initializeViews()

        loginButton.setOnClickListener {
            email = emailEditText.text.toString()
            password = passwordEditText.text.toString()
            signIn(email, password)

        }
        signUpTextView.setOnClickListener {
            isEmailPasswordValid()
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

    private fun signIn(email:String, password: String){

        if (email.isNotEmpty() && password.isNotEmpty()) {
            myAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in successful
                        Toast.makeText(this, "Sign in successful", Toast.LENGTH_SHORT).show()
                    } else {
                        // Sign in failed
                        Toast.makeText(this, "Sign in failed", Toast.LENGTH_SHORT).show()
                    }
                }
        } else {

            Toast.makeText(this, "Password or Email/Phone is empty", Toast.LENGTH_SHORT).show()
        }
    }
    private fun signUp(email:String, password:String) {

        if (email.isNotEmpty() && password.isNotEmpty()) {
            myAuth.createUserWithEmailAndPassword(email, password)
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
    private fun isEmailPasswordValid(){
        // signup email & password
        email = emailEditText.text.toString()
        password = passwordEditText.text.toString()

        val emailPattern = "[a-zA-Z0-9._%+-]+@(gmail\\.com|outlook\\.com)"
        val passwordPattern = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@#$%^&+=])(?=\\S+$).{8,}$"

        val passwordValidated = password.matches(passwordPattern.toRegex())
        val emailValidated = email.matches(emailPattern.toRegex())


        // Email & password validator
        if (emailValidated && passwordValidated ) {
            signUp(email, password)
        } else {
            Toast.makeText(this, " Email $emailValidated & password $passwordValidated", Toast.LENGTH_SHORT).show()
        }
    }

}
