package com.example.cyberchat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class LoginSignupActivity : AppCompatActivity() {

    private lateinit var myAuth:FirebaseAuth
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var loginButton: Button
    private lateinit var signUpTextView: TextView
    private lateinit var mDatabaseRef : DatabaseReference
    private var email: String = ""
    private var password: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_signup)
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
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("user")

    }

    private fun signIn(email:String, password: String){

        if (email.isNotEmpty() && password.isNotEmpty()) {
            myAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in successful
                        chatWindow()
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

    private fun chatWindow(){

        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)

    }


    private fun signUp(email:String, password:String) {

        if (email.isNotEmpty() && password.isNotEmpty()) {
            myAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Registration successful
                        Toast.makeText(this, "Registration successful", Toast.LENGTH_SHORT).show()
                        addUserToDatabase(email, myAuth.currentUser?.uid!!)
                        signIn(email, password)

                    } else {
                        // Registration failed
                        Toast.makeText(this, "Registration failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                    }
                }

        } else {

            Toast.makeText(this, "Password or Email/Phone is empty", Toast.LENGTH_SHORT).show()
        }


    }

    private fun addUserToDatabase(email: String, uid: String) {
        // Regular expression pattern to match the name part before the "@" symbol
        val regex = Regex("([^@]+)@.*")

        // Match the email address against the regular expression
        val matchResult = regex.find(email)

        // Extract the captured group (name) from the match result
        val name = matchResult?.groupValues?.get(1) ?: ""

        mDatabaseRef.child(uid).setValue(User(name, email, uid))



    }


    // Validate a email
    private fun isEmailPasswordValid(){
        // signup email & password
        email = emailEditText.text.toString()
        password = passwordEditText.text.toString()

        val emailPattern = getString(R.string.email_pattern)
        val passwordPattern = getString(R.string.password_pattern)

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
