package com.example.helloandroid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var isFirstClick = true

        val clickBtn = findViewById<Button>(R.id.clickBtn)
        clickBtn.setOnClickListener {
            if(isFirstClick)
            {
                val textView = findViewById<TextView>(R.id.textView)
                textView.text = "You just clicked the button"
                isFirstClick = false
            }
            else
            {
                intent = Intent(applicationContext, MainActivity2::class.java)
                startActivity(intent)
            }
        }


    }


}