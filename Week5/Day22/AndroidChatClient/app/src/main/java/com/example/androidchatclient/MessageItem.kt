package com.example.androidchatclient

class MessageItem(content: String, timestamp: String) {
    private var messageContent = ""
    private var messageTimestamp = ""
    
    init {
        messageContent = content
        messageTimestamp = timestamp
    }
    
    fun getMessageContent(): String {
        return messageContent
    }
    
    fun getMessageTimestamp(): String {
        return messageTimestamp
    }
}
