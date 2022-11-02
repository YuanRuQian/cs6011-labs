package com.example.androidchat

import android.widget.Toast
import org.json.JSONArray
import org.json.JSONObject

class HelperTools {
    companion object {
        fun showMessageToast(applicationContext: android.content.Context, message: String) {
            Toast.makeText(
                applicationContext,
                message,
                Toast.LENGTH_LONG
            ).show()
        }

        private fun getCurrentUnixTimestamp(): Long {
            return System.currentTimeMillis()
        }

        private fun getJoinMessage(): String {
            val userName = GlobalStateManager.getCurrentUserName()
            val roomName = GlobalStateManager.getCurrentRoomName()
            val timestamp = getCurrentUnixTimestamp()
            return "join $userName $roomName $timestamp"
        }

        private fun getLeaveMessage(): String {
            val userName = GlobalStateManager.getCurrentUserName()
            val roomName = GlobalStateManager.getCurrentRoomName()
            val timestamp = getCurrentUnixTimestamp()
            return "leave $userName $roomName $timestamp"
        }

        private fun getMessageToSend(message: String): String {
            val userName = GlobalStateManager.getCurrentUserName()
            val timestamp = getCurrentUnixTimestamp()
            return "$userName $timestamp $message"
        }

        fun sendJoinMessageToServer() {
            val webSocket = GlobalStateManager.getWebSocket()
            webSocket.send(getJoinMessage())
        }

        fun sendLeaveMessageToServer() {
            val webSocket = GlobalStateManager.getWebSocket()
            webSocket.send(getLeaveMessage())
        }

        fun sendMessageToServer(messageContent: String) {
            val webSocket = GlobalStateManager.getWebSocket()
            webSocket.send(getMessageToSend(messageContent))
        }

        fun getJSONFromString(string: String): Map<String, *> {
            val jsonObj = JSONObject(string)
            return jsonObj.toMap()
        }
    }
}


// reference: https://stackoverflow.com/a/64002903
fun JSONObject.toMap(): Map<String, *> = keys().asSequence().associateWith { it ->
    when (val value = this[it]) {
        is JSONArray -> {
            val map = (0 until value.length()).associate { Pair(it.toString(), value[it]) }
            JSONObject(map).toMap().values.toList()
        }
        is JSONObject -> value.toMap()
        JSONObject.NULL -> null
        else -> value
    }
}
