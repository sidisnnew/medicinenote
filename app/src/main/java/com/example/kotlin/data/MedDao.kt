package com.example.kotlin.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface MedDao {
    @Query("SELECT * FROM med_table")
    fun getAllMed(): Flow<List<Medicine>>

    @Insert
    suspend fun insertMed(medicine: Medicine)

    @Update
    suspend fun updateMed(medicine: Medicine)

    @Delete
    suspend fun deleteMed(medicine: Medicine)
}