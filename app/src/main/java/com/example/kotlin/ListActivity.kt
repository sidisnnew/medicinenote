package com.example.kotlin

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresPermission
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlin.data.MedDB
import com.example.kotlin.data.MedicineHistory
import kotlinx.coroutines.launch

class ListActivity : AppCompatActivity() {
    private val requestNotificationPermission = registerForActivityResult(ActivityResultContracts.RequestPermission()){
        isGranted ->
        if(isGranted){
            println("Granted")
        } else {
            println("Denied")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_list)
        //DB
        val database = MedDB.getDatabase(this)
        val medDao = database.medDao()
        val historyDao = database.historyDao()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {

            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                requestNotificationPermission.launch(
                    Manifest.permission.POST_NOTIFICATIONS
                )
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
                ReminderScheduler.scheduleReminder(this@ListActivity, medicine.id, medicine.medName)
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
                val id = adapter.mData[position].id
                val intent = Intent(this@ListActivity, HistoryActivity::class.java)
                intent.putExtra("id", id)
                startActivity(intent)

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