package com.example.doit.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.PopupProperties
import com.example.doit.data.model.Task
import com.example.doit.presentation.utils.LocalNavController
import com.example.doit.taskviewmodel.TaskVm

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskEditScreen(viewModel: TaskVm,taskId:Int?){
  /*  val snackBarState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()*/
    val task by viewModel.getTaskById(taskId).observeAsState()
    val menuItems = listOf("Delete", "Archive")
    var taskTitle by remember {
        mutableStateOf(task?.title?:"")
    }
    var taskDesc by remember {
        mutableStateOf(task?.description?:"")
    }
    val showSnackBar = remember {
        mutableStateOf(false
        )
    }
    val showSavingProgressSnackBar = remember {
        mutableStateOf(false)
    }
    var expanded by remember {
        mutableStateOf(false)
    }
    val navController= LocalNavController.current
    LaunchedEffect(task) {
        task?.let {
            taskTitle=it.title
            taskDesc=it.description

        }
    }


    Scaffold(modifier = Modifier,
        topBar = {

            CenterAlignedTopAppBar(title = {
            }, navigationIcon = {
                IconButton(onClick = {
                    navController.popBackStack()
                    val newTask = Task( title = taskTitle, description = taskDesc)
                    if(taskTitle.isNotEmpty() || taskDesc.isNotEmpty()){
                        if (task==null){
                            viewModel.insertTask(newTask)
                            showSavingProgressSnackBar.value=true
                        }
                        else{
                            val updatedTask = task!!.copy(title = taskTitle, description = taskDesc)
                            viewModel.updateTask(updatedTask)

                        }
                    }
                    else{
                        showSnackBar.value=true
                    }
                }) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                }

            }
            )
        },
       content = {
            innerPadding->
            Box(modifier = Modifier
                .padding(innerPadding)
                .fillMaxWidth()
                ){
                TaskEdit(modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
                    , taskTitle = taskTitle, taskDesc = taskDesc, onTaskTitleChange = {
                        taskTitle=it }, onTaskDescChange = {taskDesc=it})

            }//box

        },//content
        bottomBar = {
            if (showSavingProgressSnackBar.value){
                Snackbar(modifier = Modifier.fillMaxWidth(),shape = RoundedCornerShape(16.dp),
                 content = {
                        Text(text = "Saving Task")
                    })
            }
            BottomAppBar{

                Row(modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
                    Icon(Icons.Filled.Add, contentDescription = "Add Image")
                   IconButton(onClick = {
                      expanded = true
                   }) {
                       Icon(Icons.Default.MoreVert, contentDescription ="more" )
                   }
                }
                if (expanded){
                    DropdownMenu(modifier = Modifier.fillMaxWidth(),properties = PopupProperties(dismissOnClickOutside = true), expanded = expanded, onDismissRequest = {expanded= !expanded}) {
                        Column(modifier = Modifier) {

                        }
                        menuItems.forEach { item ->
                            DropdownMenuItem(text = { Text(text = item)}, onClick = {
                                navController.popBackStack()
                                expanded = false
                                when(item){
                                    "Delete"->{
                                        task?.let {
                                            viewModel.updateTask(it.copy(deletedAt = System.currentTimeMillis()))
                                            viewModel.updateTask(it.copy(isTrashed = true))
                                        }

                                    }
                                    "Archive"->{
                                        task?.let {
                                            viewModel.updateTask(it.copy(isArchived = true))
                                        }
                                    }

                                }
                            })
                        }
                    }
                }
            }
        }
    )
     //->end of scaffold

}
