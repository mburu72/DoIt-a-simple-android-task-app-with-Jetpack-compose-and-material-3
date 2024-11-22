package com.example.doit.presentation.utils

import android.content.Context
import androidx.compose.material3.TimeInput
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit

class TrashCleanUpSchedule (private val context: Context){
    fun scheduleTaskCleanup(){
        val cleanupRequest = PeriodicWorkRequestBuilder<TaskTrashCleaner>(1,TimeUnit.DAYS).build()
        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            "TaskCleanup",
            ExistingPeriodicWorkPolicy.KEEP,
            cleanupRequest
        )

    }
}
