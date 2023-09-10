package com.example.cyberchat

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class MyAdapter(private val userList:ArrayList<User>) : RecyclerView.Adapter<MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        // Inflate the item layout using the context
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.user_list, parent, false)
        return MyViewHolder(itemView)

    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val user = userList[position]

        // Bind data to the views in the ViewHolder
        holder.bindUser(user)
    }

    override fun getItemCount(): Int {
        return userList.size
    }

}