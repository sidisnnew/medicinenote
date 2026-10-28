package com.example.kotlin

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.kotlin.data.MedDB
import com.example.kotlin.data.Medicine
import kotlinx.coroutines.launch

class InsertActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_insert)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val database = MedDB.getDatabase(this)
        val medDao = database.medDao()

        val med = findViewById<EditText>(R.id.medicine)
        val time = findViewById<EditText>(R.id.time)
        val btn = findViewById<Button>(R.id.button)

        btn.setOnClickListener {
            if(med.text.toString() != "" && time.text.toString() != ""){
                val medName = med.text.toString()
                val medTime = time.text.toString().toInt()

                val medicine = Medicine(medName=medName, time=medTime)
                lifecycleScope.launch {
                    medDao.insertMed(medicine)
                    Toast.makeText(this@InsertActivity, "Saved", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }
            else{
                Toast.makeText(this, "Empty input", Toast.LENGTH_SHORT).show()
            }
        }
    }
}