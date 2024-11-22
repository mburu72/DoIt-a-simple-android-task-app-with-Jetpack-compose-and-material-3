package com.example.doit.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.doit.data.model.Task
import com.example.doit.presentation.utils.EmptyList
import com.example.doit.presentation.utils.LocalNavController
import com.example.doit.presentation.utils.TaskBottomBar
import com.example.doit.presentation.utils.TaskFAB
import com.example.doit.presentation.utils.TaskTopAppBar
import com.example.doit.taskviewmodel.TaskVm

@Composable
fun TasksScreen(viewModel: TaskVm){
    val tasks by viewModel.tasks.collectAsState()
    val navController = LocalNavController.current
    var displayInRow by remember {
        mutableStateOf(false)
    }

    Scaffold(modifier = Modifier,
     floatingActionButton = {

         TaskFAB(onClickAct = {
             navController.navigate("edit")
         })
    }, floatingActionButtonPosition = FabPosition.End, content = {
        innerPadding->
        Column(modifier = Modifier
            .padding()
            .fillMaxSize()
            .padding(innerPadding)) {

            TaskTopAppBar(gridTaskDisplay = displayInRow, onViewChange = {
                displayInRow = !displayInRow
            })
            if(tasks.isEmpty()){
                EmptyList("")
            }
            if (displayInRow){
                TaskGridView(tasksVM = tasks, navController = navController)

            }
            else{
                TaskColumnView(tasksVM = tasks, navController = navController)

            }

        }
    },
        bottomBar = {
            TaskBottomBar()
        })

}

@Composable
fun TaskGridView(tasksVM: List<Task>, navController: NavController){
    LazyVerticalGrid(columns = GridCells.Adaptive(minSize = 128.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        items(tasksVM){
            task->
            TaskListCard(task = task, onClick = {
                navController.navigate("editTask/${task.id}")
            })
        }
    }
}

@Composable
fun TaskColumnView(tasksVM: List<Task>, navController: NavController){
    LazyColumn(modifier = Modifier) {
        items(tasksVM){
task->
            TaskListCard(task = task, onClick = {
                navController.navigate("editTask/${task.id}")
            })
        }
    }//lc
}
