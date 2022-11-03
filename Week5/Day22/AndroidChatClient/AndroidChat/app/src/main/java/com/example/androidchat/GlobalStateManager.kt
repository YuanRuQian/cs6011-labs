package com.example.androidchat

import android.app.Application
import android.content.Context
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket
import java.util.concurrent.TimeUnit

class GlobalStateManager : Application() {

    companion object {
        private var userName = ""
        private var roomName = ""

        private var isLoggedIn = false

        private lateinit var currentApplicationContext: Context

        private lateinit var webSocket: WebSocket

        // Android Studio emulator localhost address is 10.0.2.2
        private var emulatorLocalhostAddress = "ws://10.0.2.2:8080"

        fun getCurrentApplicationContext(): Context {
            return currentApplicationContext
        }

        fun updateCurrentApplicationContext(currentContext: Context)
        {
            currentApplicationContext = currentContext
        }

        fun getWebSocket(): WebSocket {
            return webSocket
        }

        // OkHttp use an async call to deal a WebSocket connect to prevent blocking main thread
        // So I don't need to handle the multi threading by myself
        fun setupWebSocket() {
            val client = OkHttpClient.Builder()
                .readTimeout(3, TimeUnit.SECONDS)
                .build()
            val request = Request.Builder()
                .url(emulatorLocalhostAddress)
                .build()
            val wsListener = EchoWebSocketListener()
            webSocket =
                client.newWebSocket(request, wsListener)
        }

        fun updateUserName(newUserName: String) {
            userName = newUserName
        }

        fun updateRoomName(newRoomName: String) {
            roomName = newRoomName
        }

        fun getCurrentUserName(): String {
            return userName
        }

        fun getCurrentRoomName(): String {
            return roomName
        }

        fun setLoggedInAsFalse() {
            isLoggedIn = false
        }

        fun setLoggedInAsTrue() {
            isLoggedIn = true
        }
    }
}
