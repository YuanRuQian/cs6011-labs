package com.example.androidchatclient

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.neovisionaries.ws.client.WebSocket
import com.neovisionaries.ws.client.WebSocketFactory

class LoginPage : AppCompatActivity() {

    companion object {
        lateinit var webSocket_: WebSocket

        // FIXME: Do not place Android context classes in static fields; this is a memory leak
        private lateinit var userName_: String
        private lateinit var roomName_: String

        // Android Studio emulator localhost: 10.0.2.2
        const val webSocketAddress = "ws://10.0.2.2:8080"

        fun getCurrentUser(): String {
            return userName_
        }

        fun getCurrentRoom(): String {
            return roomName_
        }

    }

    private fun startIntentSwitchToChatRoomPage() {
        DevTools.devModeLogger("start activity to chat room page")
        val toChatRoomPageIntent = Intent(applicationContext, ChatRoomPage::class.java)
        toChatRoomPageIntent.putExtra("userName", getCurrentUser())
        toChatRoomPageIntent.putExtra("roomName", getCurrentRoom())
        startActivity(toChatRoomPageIntent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.log_in_page)
        setUpWebSocket()
        setUpDefaultUserAndRoomName()
    }

    override fun onResume() {
        super.onResume()
        setUpWebSocket()
    }

    private fun setUpWebSocket() {
        webSocket_ = WebSocketFactory().createSocket(webSocketAddress)
        webSocket_.addListener(ConnectionHandler())
        webSocket_.connectAsynchronously()
    }

    private fun setUpDefaultUserAndRoomName() {
        val userInput = findViewById<TextInputEditText>(R.id.userInput)
        val roomInput = findViewById<TextInputEditText>(R.id.roomInput)
        userInput.setText(HelperTools.getRandomUserName())
        roomInput.setText(HelperTools.getRandomRoomName())
    }

    private fun getUserName(): String {
        val userInput = findViewById<TextInputEditText>(R.id.userInput)
        return userInput.text.toString()
    }

    private fun getRoomName(): String {
        val roomInput = findViewById<TextInputEditText>(R.id.roomInput)
        return roomInput.text.toString()
    }

    private fun doesStringContainsSpaces(str: String): Boolean {
        return str.split(" ").size > 1
    }

    private fun showAlertDialog(title: String, message: String) {
        val dialogBuilder = AlertDialog.Builder(this)
        dialogBuilder.setMessage(message)
            .setCancelable(false)
            .setPositiveButton("OK") { dialog, _ ->
                dialog.cancel()
            }
        val alert = dialogBuilder.create()
        alert.setTitle(title)
        alert.show()
    }

    private fun isUserNameValid(): Boolean {
        if (userName_.isEmpty()) {
            showAlertDialog(
                "Invalid User Name",
                "Please enter your user name.",
            )
            return false
        } else if (doesStringContainsSpaces(userName_)) {
            showAlertDialog(
                "Invalid User Name",
                "No spaces please."
            )
            return false
        }
        return true
    }

    private fun isRoomNameValid(): Boolean {
        if (roomName_.isEmpty()) {
            showAlertDialog(
                "Invalid Room Name",
                "Please enter your room name."
            )
            return false
        } else if (doesStringContainsSpaces(roomName_)) {
            showAlertDialog(
                "Invalid Room Name",
                "No spaces please."
            )
            return false
        }
        return true
    }

    fun handleLogIn(view: View) {
        userName_ = getUserName()
        roomName_ = getRoomName()
        if (!isUserNameValid() || !isRoomNameValid()) {
            return
        }
        if (webSocket_.isOpen) {
            startIntentSwitchToChatRoomPage()
            webSocket_.sendText(HelperTools.getJoinMessage(getUserName(), getRoomName()))
        } else {
            showAlertDialog(
                "Web Socket Is Closed",
                "Refresh the page to try again!"
            )
        }

    }
}