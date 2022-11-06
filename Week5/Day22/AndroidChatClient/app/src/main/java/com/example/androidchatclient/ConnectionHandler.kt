package com.example.androidchatclient

import com.neovisionaries.ws.client.WebSocket
import com.neovisionaries.ws.client.WebSocketAdapter
import com.neovisionaries.ws.client.WebSocketException
import com.neovisionaries.ws.client.WebSocketFrame
import kotlinx.coroutines.Runnable
import org.json.JSONObject

class ConnectionHandler : WebSocketAdapter() {
    override fun onConnected(
        websocket: WebSocket?,
        headers: MutableMap<String, MutableList<String>>?
    ) {
        super.onConnected(websocket, headers)
        ChatRoomPage.resetMessages()
    }

    override fun onConnectError(websocket: WebSocket?, exception: WebSocketException?) {
        super.onConnectError(websocket, exception)
        if (exception != null) {
            DevTools.devModeLogger("web socket connection error: " + exception.localizedMessage)
        }
    }

    override fun onCloseFrame(websocket: WebSocket?, frame: WebSocketFrame?) {
        super.onCloseFrame(websocket, frame)
        DevTools.devModeLogger("getting close frame")
    }

    override fun onTextMessage(websocket: WebSocket?, text: String?) {
        DevTools.devModeLogger("get message from server: $text")
        val jsonObject = text?.let { JSONObject(it) }
        val type = jsonObject?.get("type") as String
        val room = jsonObject["room"] as String
        val user = jsonObject["user"] as String
        val time = jsonObject["timestamp"] as String
        val timestamp = HelperTools.getDateString(time.toLong())

        var message = ""
        when (type) {
            "error" -> {
                message = "ERROR:\r\n${jsonObject["error"]}"
            }
            "join" -> {
                message = "$user joined $room"
            }
            "message" -> {
                var messageJson = jsonObject["message"] as String
                messageJson = messageJson.replace("\\[newline\\]".toRegex(), "\r\n")
                message = "$user: $messageJson"
            }
            "leave" -> {
                message = "$user left $room"
            }
        }
        ChatRoomPage.addNewMessage(MessageItem(message, timestamp))
        ChatRoomPage.getMessageView().post(Runnable {
            ChatRoomPage.getAdapter().notifyDataSetChanged()
            ChatRoomPage.getMessageView().scrollToPosition(ChatRoomPage.getMessageListSize() - 1)
        })
    }
}
