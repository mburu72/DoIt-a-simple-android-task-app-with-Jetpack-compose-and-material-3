package com.example.doit.presentation.utils

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import com.example.doit.data.dao.TaskDao
import com.example.doit.data.db.TaskDatabase
import java.util.concurrent.TimeUnit

class TaskTrashCleaner(context: Context, params:WorkerParameters):CoroutineWorker(context, params) {
    private val taskDao:TaskDao=TaskDatabase.getDatabase(applicationContext).taskDao()
    override suspend fun doWork(): Result {
        val thirtyDaysInMilli = TimeUnit.DAYS.toMillis(30)
        val currentTime=System.currentTimeMillis()
        val trashTimeLimit = currentTime-thirtyDaysInMilli
        return Result.success()
    }
}


