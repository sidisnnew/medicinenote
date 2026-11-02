package com.example.kotlin.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface HistoryDao {
    @Query("SELECT * FROM history_table")
    fun getAllHistory(): Flow<List<MedicineHistory>>

    @Query("""
        SELECT medicine_id, MAX(taken_at) AS taken_at 
        FROM history_table
        GROUP BY medicine_id
        """)
    fun getLatestHistory(): Flow<List<LatestHistory>>

    @Query("SELECT taken_at FROM history_table WHERE medicine_id = :medId ORDER BY taken_at DESC")
    fun getMedHistory(medId: Int): Flow<List<Long>>

    @Insert
    suspend fun insertHistory(history: MedicineHistory)

    @Update
    suspend fun updateHistory(history: MedicineHistory)

    @Delete
    suspend fun deleteMed(history: MedicineHistory)
}