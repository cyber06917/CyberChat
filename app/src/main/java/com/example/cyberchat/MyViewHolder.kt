package com.example.cyberchat

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MyViewHolder(private val itemView:View) : RecyclerView.ViewHolder(itemView){
    private val userNameTextView = itemView.findViewById<TextView>(R.id.userName)

    fun bindUser(user: User) {
        userNameTextView.text = user.name
    }

}