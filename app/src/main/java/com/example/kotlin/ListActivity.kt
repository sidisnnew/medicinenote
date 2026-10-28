package com.example.kotlin

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlin.data.MedDB
import com.example.kotlin.data.MedicineHistory
import kotlinx.coroutines.launch

class ListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_list)
        //DB
        val database = MedDB.getDatabase(this)
        val medDao = database.medDao()
        val historyDao = database.historyDao()

        lifecycleScope.launch {
            medDao.getAllMed().collect { medicines ->
                println(medicines)
            }
            historyDao.getAllHistory().collect { histories ->
                println(histories)
            }
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = ""

        val recycle = findViewById<RecyclerView>(R.id.recycle)
        val adapter = Adaptor(this, emptyList())
        adapter.setTakenClickListener { medicine ->

            val history = MedicineHistory(
                med_id = medicine.id,
                takenAt = System.currentTimeMillis()
            )

            lifecycleScope.launch {
                historyDao.insertHistory(history)
            }
        }
        recycle?.adapter = adapter
        recycle?.layoutManager = LinearLayoutManager(this)

        lifecycleScope.launch {
            medDao.getAllMed().collect { medicines ->
                adapter.updateData(medicines)
            }
        }
        lifecycleScope.launch {
            historyDao.getLatestHistory().collect { historyList ->
                adapter.updateLatestHistory(historyList)
            }
        }

        adapter.setItemClickListener(object : OnRecyclerViewClickListener {
            override fun OnItemClickListener(v: View) {
                val position: Int = recycle!!.getChildAdapterPosition(v)
                Toast.makeText(this@ListActivity, "position $position", Toast.LENGTH_SHORT).show()
            }
        })

        val btn_add = findViewById<ImageButton>(R.id.btn_add)
        btn_add.setOnClickListener {
            val intent = Intent(this, InsertActivity::class.java)
            startActivity(intent)
        }
    }

    //menu
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.setting -> {
                val intent = Intent(this, SettingActivity::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}