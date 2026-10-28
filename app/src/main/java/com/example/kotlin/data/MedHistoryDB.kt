package com.example.kotlin.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [MedicineHistory::class], version = 1, exportSchema = false)
abstract class MedHistoryDB: RoomDatabase() {
    abstract fun historyDao() : HistoryDao
    companion object {
        @Volatile
        private var INSTANCE: MedHistoryDB? = null
        fun getDatabase(context: Context): MedHistoryDB{
            return MedHistoryDB.Companion.INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(context.applicationContext, MedHistoryDB::class.java, "history_database").build()
                MedHistoryDB.Companion.INSTANCE = instance
                return instance
            }
        }
    }
}