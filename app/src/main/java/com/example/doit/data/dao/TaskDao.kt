package com.example.doit.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.doit.data.model.Task
import kotlinx.coroutines.flow.Flow


@Dao
interface TaskDao {
    @Query("SELECT * FROM tasks WHERE isTrashed= 0 AND isArchived = 0 AND isCompleted=0")
    fun getAllTasks():Flow<List<Task>>
    @Query("SELECT * FROM tasks WHERE id=:taskId")
    fun getTaskById(taskId:Int):LiveData<Task>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task:Task)
    @Delete
    suspend fun  deleteTask(task:Task)
    @Update
    suspend fun updateTask(task: Task)
    @Query("SELECT * FROM tasks WHERE isTrashed = 1")
    fun getTrashedTasks():Flow<List<Task>>
    @Query("SELECT * FROM tasks WHERE isArchived=1 AND isTrashed=0")
    fun getArchivedTasks():Flow<List<Task>>
    @Query("DELETE FROM tasks WHERE isTrashed = 1")
    suspend fun emptyTrash()
    @Query("DELETE FROM tasks WHERE isTrashed=1 AND deletedAt IS NOT NULL  AND deletedAt <= :timeLimit")
    suspend fun deleteTasksInTrash(timeLimit:Long)
    @Query("SELECT * FROM tasks WHERE isTrashed=0 AND isArchived=0 AND isCompleted=1")
    fun getCompletedTask():Flow<List<Task>>
}