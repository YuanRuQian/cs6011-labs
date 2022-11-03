package com.example.androidchat
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText

class LoginPage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_page)
        GlobalStateManager.updateCurrentApplicationContext(applicationContext)
        val logInBtn = findViewById<Button>(R.id.logInBtn)
        logInBtn.setOnClickListener {
            handleLogIn()
        }
        GlobalStateManager.setupWebSocket()
    }

    private fun isLogInInfoValid(userName: String, roomName: String): Boolean {
        if (userName.isEmpty()) {
            HelperTools.showMessageToast(applicationContext, "Oops empty user name")
            return false
        }
        if (roomName.isEmpty()) {
            HelperTools.showMessageToast(applicationContext, "Oops empty room name")
            return false
        }
        if (userName.matches("\\s".toRegex())) {
            HelperTools.showMessageToast(applicationContext, "No empty spaces in user name plz")
            return false
        }
        if (roomName.matches("\\s".toRegex())) {
            HelperTools.showMessageToast(applicationContext, "No empty spaces in room name plz")
            return false
        }
        if (roomName.lowercase() != roomName) {
            HelperTools.showMessageToast(applicationContext, "No upper cases in room name plz")
            return false
        }
        return true
    }

    private fun handleLogIn() {
        val userName = findViewById<TextInputEditText>(R.id.userNameText).text.toString()
        val roomName = findViewById<TextInputEditText>(R.id.roomNameText).text.toString()
        if (isLogInInfoValid(userName, roomName)) {
            GlobalStateManager.updateUserName(userName)
            GlobalStateManager.updateRoomName(roomName)
            HelperTools.sendJoinMessageToServer()
            intent = Intent(applicationContext, ChatRoomPage::class.java)
            startActivity(intent)
        }
    }

}