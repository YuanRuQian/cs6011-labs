package com.example.androidchat

import android.content.Intent
import android.os.Bundle
import android.os.MessageQueue
import android.util.Log
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity

class ChatRoomPage : AppCompatActivity() {
    private lateinit var messageQueue: ListView

    private fun testListView()
    {
        messageQueue = findViewById<ListView>(R.id.recipe_list_view)


        val listItems = arrayOfNulls<String>(3)

        for (i in 0 until 3) {
            listItems[i] = i.toString()
        }
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, listItems)
        messageQueue.adapter = adapter
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.chat_room_page)
        Log.i("chat-room", "current user: " + GlobalStateManager.getCurrentUserName())
        Log.i("chat-room", "current room: " + GlobalStateManager.getCurrentRoomName())

        GlobalStateManager.updateCurrentApplicationContext(applicationContext)

        // showing the back home button in action bar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setUpInfoDisplay()

        testListView()

        val sendMessageBtn = findViewById<Button>(R.id.sendMessageBtn)
        sendMessageBtn.setOnClickListener {
            sendMessageToServer()
        }
    }

    private fun sendMessageToServer() {
        val messageInput = findViewById<EditText>(R.id.messageInput)
        val messageContent = messageInput.text
        if(messageContent.isEmpty())
        {
            HelperTools.showMessageToast(applicationContext,"Oops you can't send empty message")
            return
        }
        HelperTools.sendMessageToServer(messageContent.toString())
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val homeButtonId = android.R.id.home.toString()
        if (item.itemId.toString() == homeButtonId) {
            HelperTools.sendLeaveMessageToServer()
            GlobalStateManager.updateUserName("")
            GlobalStateManager.updateRoomName("")
            intent = Intent(applicationContext, LoginPage::class.java)
            startActivity(intent)
            HelperTools.showMessageToast(applicationContext, "Successfully logged out")
        }
        return true
    }


    private fun getWelcomeMessage(): String {
        return "Welcome to ${GlobalStateManager.getCurrentRoomName()}, ${GlobalStateManager.getCurrentUserName()}!"
    }

    private fun setUpInfoDisplay() {
        HelperTools.showMessageToast(applicationContext, getWelcomeMessage())
        supportActionBar?.title = GlobalStateManager.getCurrentRoomName()
    }
}