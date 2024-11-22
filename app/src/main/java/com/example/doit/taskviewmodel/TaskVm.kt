package com.example.doit.taskviewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.doit.data.db.TaskDatabase
import com.example.doit.data.model.Task
import com.example.doit.presentation.utils.TrashCleanUpSchedule
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class TaskVm(application: Application):AndroidViewModel(application) {
    private val taskDao = TaskDatabase.getDatabase(application).taskDao()
    private val _tasks = MutableStateFlow<List<Task>>(emptyList())
    val tasks:StateFlow<List<Task>>get() = _tasks
    private val _trashedTasks = MutableStateFlow<List<Task>>(emptyList())
    val trashedTasks:StateFlow<List<Task>>get() = _trashedTasks
    private val _archivedTasks = MutableStateFlow<List<Task>>(emptyList())
    val archivedTasks:StateFlow<List<Task>>get() = _archivedTasks
    private val _completedTask = MutableStateFlow<List<Task>>(emptyList())


    init {
        val workerScheduler = TrashCleanUpSchedule(application)
        workerScheduler.scheduleTaskCleanup()
        viewModelScope.launch {

            taskDao.getAllTasks().collect{
                _tasks.value=it
            }
        }
        viewModelScope.launch {
            taskDao.getArchivedTasks().collect{
                _archivedTasks.value=it
            }
        }
        viewModelScope.launch {
            taskDao.getTrashedTasks().collect{
                _trashedTasks.value=it
            }
        }
        viewModelScope.launch {
            taskDao.getCompletedTask().collect{
                _completedTask.value=it
            }
        }
    }
    fun insertTask(task: Task){
        viewModelScope.launch {
            taskDao.insertTask(task)
        }
    }
    fun updateTask(task: Task){
        viewModelScope.launch {
            taskDao.updateTask(task)
        }
    }
    fun getTaskById(taskId:Int?):LiveData<Task>{
return taskId?.let { taskDao.getTaskById(it)}?:MutableLiveData<Task>().apply { value=null }
    }
    fun deleteTask(task: Task){
        viewModelScope.launch {
            taskDao.deleteTask(task)
        }
    }
    fun emptyTrash(){
        viewModelScope.launch {
            taskDao.emptyTrash()
        }
    }
    fun emptyTrashAuto(){
        viewModelScope.launch {
            val thirtyDays = TimeUnit.SECONDS.toMillis(10)
            val currentTime = System.currentTimeMillis()
            val timeLimit = currentTime-thirtyDays
            taskDao.deleteTasksInTrash(timeLimit)
        }
    }

}