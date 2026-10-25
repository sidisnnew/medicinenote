package com.example.kotlin

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.content.edit

class SettingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_setting)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val settings = getSharedPreferences("app_settings", MODE_PRIVATE)

        val pwSwitch: SwitchCompat = findViewById(R.id.password_switch)
        pwSwitch.isChecked = settings.getBoolean("password_enabled", false)

        val pw = findViewById<EditText>(R.id.password)
        pw.setText(settings.getString("password", ""))

        val saveBtn = findViewById<Button>(R.id.save)
        saveBtn.setOnClickListener {
            settings.edit().putString("password", pw.text.toString())
                .putBoolean("password_enabled", pwSwitch.isChecked)
                .apply()
            Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show()
        }
    }
}