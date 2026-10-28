package com.example.kotlin.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "history_table")
data class MedicineHistory(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "medicine_id")
    val med_id: Int,
    @ColumnInfo(name = "taken_at")
    val takenAt: Long
)
