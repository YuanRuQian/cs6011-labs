package com.example.androidchatclient

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ChatRoomPage : AppCompatActivity() {

    companion object {
        private lateinit var room_: String
        private lateinit var user_: String
        private var messageList_: ArrayList<MessageItem> = ArrayList()
        private lateinit var adapter_: CustomAdapter
        private lateinit var messageView_: RecyclerView

        fun getAdapter(): CustomAdapter {
            return adapter_
        }

        fun addNewMessage(messageItem: MessageItem) {
            messageList_.add(messageItem)
        }

        fun resetMessages() {
            messageList_.clear()
        }

        fun getMessageView(): RecyclerView {
            return messageView_
        }

        fun getMessageListSize(): Int {
            return messageList_.size
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.chat_room_page)

        // initialize the page
        val extras = intent.extras
        if (extras != null) {
            room_ = extras.getString("roomName").toString()
            user_ = extras.getString("userName").toString()
        }

        messageView_ = findViewById(R.id.messageView)
        messageView_.layoutManager = LinearLayoutManager(this)
        adapter_ = CustomAdapter(messageList_)
        messageView_.adapter = adapter_


        // showing the back home button in action bar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        // set application bar title as the room name
        supportActionBar?.title = "Current Room: $room_"
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // log out when hit application bar back button
        val homeButtonId = android.R.id.home.toString()
        if (item.itemId.toString() == homeButtonId) {
            logOut()
        }
        return true
    }

    private fun logOut() {
        val intent = Intent(this, LoginPage::class.java)
        startActivity(intent)
        LoginPage.webSocket_.sendText(HelperTools.getLeaveMessage(user_, room_))
        LoginPage.webSocket_.sendClose()
        LoginPage.webSocket_.disconnect()
        resetMessages()
    }

    private fun getUserName(): String {
        return user_
    }

    private fun getRoomName(): String {
        return room_
    }

    private fun clearMessageEditText() {
        val messageText = findViewById<TextView>(R.id.messageEditText)
        messageText.text = ""
    }

    fun sendMessages(view: View?) {
        val messageText = findViewById<EditText>(R.id.messageEditText)
        val messageString = messageText.text.toString()
        val userName = getUserName()
        val roomName = getRoomName()
        LoginPage.webSocket_.sendText(
            HelperTools.getMessageToSend(
                userName,
                roomName,
                messageString
            )
        )
        if (view != null) {
            HelperTools.hideSoftKeyBoard(applicationContext, view)
        }
        clearMessageEditText()
    }

}