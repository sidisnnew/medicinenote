package com.example.kotlin

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.kotlin.data.MedDB
import com.example.kotlin.data.Medicine
import kotlinx.coroutines.launch

class EditActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_edit)
        //DB
        val database = MedDB.getDatabase(this)
        val medDao = database.medDao()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val medName = findViewById<EditText>(R.id.medName)
        val time = findViewById<EditText>(R.id.time)
        val saveBtn = findViewById<Button>(R.id.saveBtn)
        val id = intent.getIntExtra("id", 0)

        lifecycleScope.launch {
            medName.setText(medDao.getName(id))
            time.setText(medDao.getTime(id).toString())
        }

        saveBtn.setOnClickListener {
            if (medName.text.toString() != "" && time.text.toString() !=""){
                val medicine = Medicine(id=id, medName=medName.text.toString(), time=time.text.toString().toInt())
                lifecycleScope.launch {
                    medDao.updateMed(medicine)
                }
                finish()
            }
            else{
                Toast.makeText(this, "Cannot empty", Toast.LENGTH_SHORT).show()
            }
        }
    }
}