package com.example.doit.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.doit.data.dao.TaskDao
import com.example.doit.data.model.Task

@Database(entities = [Task::class], version = 1)
abstract class TaskDatabase:RoomDatabase() {
    abstract fun taskDao():TaskDao
    companion object{
        @Volatile private var INSTANCE:TaskDatabase?=null
        fun getDatabase(context: Context):TaskDatabase{
            return INSTANCE?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext, TaskDatabase::class.java, "tasks_db"
                ).build()
                INSTANCE=instance
                instance
            }
        }
    }
}