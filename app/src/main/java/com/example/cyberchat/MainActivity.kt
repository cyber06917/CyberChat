package com.example.cyberchat

import android.os.Bundle
import android.view.Menu
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private lateinit var userListRecyclerView: RecyclerView
    private var backPressedTime = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize views and adapters
        initializeViewsAndAdapters()

        // Set up the back button press handler
        setupBackPressedHandler()
    }

    //added a top right menu for logout
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // Inflate the menu resource file (e.g., menu_main.xml) into the menu object
        menuInflater.inflate(R.menu.side_menu, menu)
        return true
    }




    private fun initializeViewsAndAdapters() {
        userListRecyclerView = findViewById(R.id.recyclerView)
        val layoutManager = LinearLayoutManager(this)
        userListRecyclerView.layoutManager = layoutManager
        userListRecyclerView.adapter = MyAdapter()
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
