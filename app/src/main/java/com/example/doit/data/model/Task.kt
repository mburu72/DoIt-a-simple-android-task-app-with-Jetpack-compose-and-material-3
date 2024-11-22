package com.example.doit.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks")
data class Task(
    @PrimaryKey(autoGenerate = true) val id:Int=0,
    val title:String,
    val description:String,
    val isTrashed:Boolean=false,
    val isArchived:Boolean=false,
    val createdAt:Long = System.currentTimeMillis(),
    val deletedAt:Long?=null,
    val isCompleted:Boolean=false


)
