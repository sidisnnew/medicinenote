package com.example.kotlin.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Medicine::class, MedicineHistory::class], version = 2, exportSchema = false)
abstract class MedDB: RoomDatabase(){
    abstract fun medDao() : MedDao
    abstract fun historyDao() : HistoryDao
    companion object{
        @Volatile
        private var INSTANCE: MedDB? = null
        fun getDatabase(context: Context): MedDB{
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(context.applicationContext, MedDB::class.java, "medicine_database").build()
                INSTANCE = instance
                return instance
            }
        }
    }
}