package com.example.androidchat

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import java.text.SimpleDateFormat
import java.time.Instant
import java.util.*

class EchoWebSocketListener : WebSocketListener() {
    override fun onOpen(webSocket: WebSocket, response: Response) {

    }

    override fun onMessage(webSocket: WebSocket, text: String) {
        if (text.isEmpty()) {
            return
        }
        output("Receiving : $text")
        val json = HelperTools.getJSONFromString(text)

        if (json["type"] == "error") {
            // TODO: alert error message
            // TODO: prevent log in
            GlobalStateManager.setLoggedInAsFalse()
            HelperTools.showMessageToast(
                GlobalStateManager.getCurrentApplicationContext(),
                json["error"] as String
            )
        }

        if (json["type"] == "join" && json["user"] == GlobalStateManager.getCurrentUserName()) {
            GlobalStateManager.setLoggedInAsTrue()
            // TODO: login to chat room page
        }
    }

    override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
        webSocket.close(NORMAL_CLOSURE_STATUS, null)
        output("Closing : $code / $reason")
    }

    override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
        output("Error : " + t.message)
    }

    companion object {
        private const val NORMAL_CLOSURE_STATUS = 1000
    }

    private fun output(txt: String) {
        Log.i("WSS", txt)
    }

    private fun getJoinMessage(json: Map<String, *>): String {
        val userName = json["user"]
        return "$userName enters the room"
    }

    private fun getLeaveMessage(json: Map<String, *>): String {
        val userName = json["user"]
        return "$userName leaves the room"
    }

    private fun getUserSentMessage(json: Map<String, *>): String {
        val userName = json["user"]
        val messageContent = json["message"]
        return "$userName: $messageContent"
    }


    private fun getTimeStampString(unixTimestamp: String): String {

        return ""
    }
}
