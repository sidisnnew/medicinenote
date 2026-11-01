package com.example.kotlin

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.annotation.RequiresPermission


class Receiver: BroadcastReceiver() {
    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    override fun onReceive(context: Context, intent: Intent) {
        val medicineId = intent.getIntExtra("medicine_id", -1)
        val medicineName = intent.getStringExtra("medicine_name")
            ?: "藥物"

        if (medicineId != -1) {
            NotificationHelper.showNotification(context, medicineId, medicineName)
        }
    }
}