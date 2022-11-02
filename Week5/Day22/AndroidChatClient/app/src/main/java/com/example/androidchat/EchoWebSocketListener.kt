package com.example.androidchat

import android.content.Intent
import android.util.Log
import androidx.core.content.ContextCompat.startActivity
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener

class EchoWebSocketListener : WebSocketListener() {
    override fun onOpen(webSocket: WebSocket, response: Response) {

    }

    override fun onMessage(webSocket: WebSocket, text: String) {
        if (text.isEmpty()) {
            return
        }
        output("Receiving : $text")
        val json = HelperTools.getJSONFromString(text)

        if(json["type"] == "error")
        {
            // TODO: alert error message
            // TODO: prevent log in
            GlobalStateManager.setLoggedInAsFalse()
            HelperTools.showMessageToast(GlobalStateManager.getCurrentApplicationContext(),
                json["error"] as String
            )
        }
        if(json["type"] == "join" && json["user"] == GlobalStateManager.getCurrentUserName())
        {
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
        private val NORMAL_CLOSURE_STATUS = 1000
    }

    private fun output(txt: String) {
        Log.i("WSS", txt)
    }
}
