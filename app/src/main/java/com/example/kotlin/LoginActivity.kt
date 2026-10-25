package com.example.kotlin

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlin.jvm.java

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val tempPw: String = "123456"
        val button = findViewById<Button>(R.id.login)
        val pwInput = findViewById<EditText>(R.id.passwordInput)
        val settings = getSharedPreferences("app_settings", MODE_PRIVATE)
        val pw = settings.getString("password", "")

        button.setOnClickListener {
            if(pwInput.text.toString()==pw){
                val intent = Intent(this, ListActivity::class.java)
                startActivity(intent)
            } else{
                Toast.makeText(this, "Wrong password", Toast.LENGTH_SHORT).show()
                pwInput.text.clear()
            }
        }
    }
}