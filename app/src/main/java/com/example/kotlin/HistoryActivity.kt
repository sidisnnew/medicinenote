package com.example.kotlin

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.kotlin.data.MedDB
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class HistoryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_history)

        //DB
        val database = MedDB.getDatabase(this)
        val medDao = database.medDao()
        val historyDao = database.historyDao()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val medName = findViewById<TextView>(R.id.medName)
        val medHistory = findViewById<TextView>(R.id.history)
        val id = intent.getIntExtra("id", 0)

        lifecycleScope.launch {
            medName.text = medDao.getName(id)
            historyDao.getMedHistory(id).collect {histories ->
                val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
                val result = histories.joinToString(separator = "\n") { time ->
                    dateFormat.format(Date(time))
                }
                medHistory.text = result
            }
        }
    }
}