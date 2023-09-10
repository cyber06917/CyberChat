package com.example.cyberchat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private lateinit var userListRecyclerView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        userListRecyclerView = findViewById(R.id.recyclerView)
        val layoutManager = LinearLayoutManager(this)
        userListRecyclerView.layoutManager = layoutManager
        userListRecyclerView.adapter = MyAdapter()
    }
}