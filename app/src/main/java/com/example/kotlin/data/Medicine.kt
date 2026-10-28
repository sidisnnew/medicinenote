package com.example.kotlin.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "med_table")
data class Medicine (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "name")
    val medName: String,
    @ColumnInfo(name = "time")
    val time: Int
)