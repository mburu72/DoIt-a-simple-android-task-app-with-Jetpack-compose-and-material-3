package com.example.doit.taskviewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class TaskVMFactory(private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T{
        if(modelClass.isAssignableFrom(TaskVm::class.java)){
            @Suppress("UNCHECKED_CAST")
            return TaskVm(application)as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")

    }}



