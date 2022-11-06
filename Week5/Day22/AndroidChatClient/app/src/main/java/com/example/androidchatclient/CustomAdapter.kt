package com.example.androidchatclient

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CustomAdapter(private val messageList: ArrayList<MessageItem>) : RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.message_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val messageItem = messageList[position]
        holder.contextTextView.text = messageItem.getMessageContent()
        holder.timestampTextView.text = messageItem.getMessageTimestamp()

    }

    override fun getItemCount(): Int {
        return messageList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val contextTextView = itemView.findViewById<TextView>(R.id.messageContent)
        val timestampTextView = itemView.findViewById<TextView>(R.id.messageTimestamp)
    }
}
