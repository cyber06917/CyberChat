package com.example.cyberchat

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class MainActivity : AppCompatActivity() {
    private lateinit var userListRecyclerView: RecyclerView
    private var backPressedTime = 0L
    private val mAuth = FirebaseAuth.getInstance()
    private lateinit var userList: ArrayList<User>
    private lateinit var mDatabaseRef: DatabaseReference
    private val myAuth = FirebaseAuth.getInstance()
    val shareObj = LoginSignupActivity()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize views and adapters
        initializeViewsAndAdapters()

        // Set up the back button press handler
        setupBackPressedHandler()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.side_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_settings -> {
                mAuth.signOut()
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }


    private fun initializeViewsAndAdapters() {
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("user")
        userListRecyclerView = findViewById(R.id.recyclerView)
        val layoutManager = LinearLayoutManager(this)
        userListRecyclerView.layoutManager = layoutManager

        // Initialize userList as an empty ArrayList<User>
        userList = ArrayList()

        // Create and set the adapter with the initialized userList
        val adapter = MyAdapter(userList)
        userListRecyclerView.adapter = adapter

        mDatabaseRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Clear the existing data
                val previousSize = userList.size
                userList.clear()

                // Iterate through the data and add to userList


                for (a in dataSnapshot.children) {
                    val currentUser = a.getValue(User::class.java)
                    if(myAuth.currentUser?.uid != currentUser?.uid){
                        currentUser?.let { userList.add(it) }
                    }

                }

                // Notify the adapter that the data has changed
                adapter.notifyItemRangeRemoved(0, previousSize)
                adapter.notifyItemRangeInserted(0, userList.size)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle errors
                Log.e("FirebaseData", "Error: ${databaseError.message}")
            }
        })
    }

    private fun setupBackPressedHandler() {
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val doublePressInterval = 2000
                val currentTime = System.currentTimeMillis()

                // If the back button is pressed again within 2 seconds, exit the app
                if (currentTime - backPressedTime < doublePressInterval) {
                    finishAffinity() // Finish all activities and exit
                } else {
                    // Store the current time and display a toast message
                    backPressedTime = currentTime
                    Toast.makeText(this@MainActivity, "Press back again to exit", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
}
