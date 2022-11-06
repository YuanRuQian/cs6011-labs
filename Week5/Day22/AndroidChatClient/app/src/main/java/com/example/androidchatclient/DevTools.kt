package com.example.androidchatclient

import android.util.Log

class DevTools {
    companion object {
        fun devModeLogger(message: String) {
            Log.d("dev-mode", message)
        }
    }
}