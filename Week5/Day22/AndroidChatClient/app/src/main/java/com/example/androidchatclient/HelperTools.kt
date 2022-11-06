package com.example.androidchatclient

import android.content.Context
import android.content.DialogInterface
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AlertDialog
import java.text.SimpleDateFormat
import java.util.*

class HelperTools {

    companion object {
        private fun getCurrentUnixTimestamp(): Long {
            return System.currentTimeMillis()
        }

        private val simpleDateFormat = SimpleDateFormat("yyyy/MM/dd hh:mm:ss a", Locale.US)

        // "1667442594063" => 2022/11/02 08:29 PM
        fun getDateString(time: Long): String = simpleDateFormat.format(time)

        fun getJoinMessage(userName: Any, roomName: String): String {
            return "join $userName $roomName ${getCurrentUnixTimestamp()}"
        }

        fun getLeaveMessage(userName: String?, roomName: String?): String {
            return "leave $userName $roomName ${getCurrentUnixTimestamp()}"
        }

        fun getMessageToSend(userName: String, roomName: String, messageString: String): String {
            DevTools.devModeLogger("message to send: " + " \"$userName $roomName ${getCurrentUnixTimestamp()} $messageString\"")
            return "$userName $roomName ${getCurrentUnixTimestamp()} $messageString"
        }

        fun hideSoftKeyBoard(context: Context, view: View) {
            try {
                val imm =
                    context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(view.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        private fun getRandomString(): String {
            val charset = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890"
            val rnd = Random(System.currentTimeMillis())
            var strLength = rnd.nextInt(10).coerceAtLeast(1)
            var str = ""
            while (strLength > 0) {
                val index = (rnd.nextFloat() * charset.length).toInt()
                str += charset[index]
                strLength--
            }
            return str
        }

        fun getRandomUserName(): String {
            return getRandomString()
        }

        fun getRandomRoomName(): String {
            val rnd = Random(System.currentTimeMillis())
            return "room${rnd.nextInt(100)}"
        }
    }


}



