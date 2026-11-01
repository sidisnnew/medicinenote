package com.example.kotlin

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.SystemClock
import kotlin.jvm.java

object ReminderScheduler {
    private const val EXTRA_MEDICINE_ID = "medicine_id"
    private const val EXTRA_MEDICINE_NAME = "medicine_name"

    fun scheduleReminder(context: Context, medicineId: Int, medicineName: String){
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val intent = Intent(context, Receiver::class.java).apply{
            putExtra(EXTRA_MEDICINE_ID, medicineId)
            putExtra(EXTRA_MEDICINE_NAME, medicineName)
        }

        val pendingIntent = PendingIntent.getBroadcast(context, medicineId, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)

        val triggerTime = SystemClock.elapsedRealtime() + 24 * 60 * 60 * 1000L

        alarmManager.setAndAllowWhileIdle(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerTime, pendingIntent)
    }
}