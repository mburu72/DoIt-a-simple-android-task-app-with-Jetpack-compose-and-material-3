package com.example.doit.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.PopupProperties
import androidx.navigation.NavController
import com.example.doit.R
import com.example.doit.data.model.Task
import com.example.doit.presentation.utils.EmptyList
import com.example.doit.presentation.utils.LocalCoroutineScope
import com.example.doit.presentation.utils.LocalDrawerState
import com.example.doit.presentation.utils.LocalNavController
import com.example.doit.taskviewmodel.TaskVm
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArchiveScreen(viewModel: TaskVm){
    var expanded by remember {
        mutableStateOf(false)
    }
    val drawerState = LocalDrawerState.current
    val scope= LocalCoroutineScope.current
    val archivedTasks by viewModel.archivedTasks.collectAsState()
    val navController = LocalNavController.current
    var displayInRow by remember { mutableStateOf(false) }
    val gridPainter = painterResource(id = R.drawable.baseline_grid_view_24)
    val colPainter = painterResource(id = R.drawable.outline_view_stream_24)

    Scaffold(
            modifier = Modifier,
            topBar = {
                TopAppBar(
                    actions = {
                        if (expanded){
                            DropdownMenu(modifier = Modifier,properties = PopupProperties(dismissOnClickOutside = true), expanded = expanded, onDismissRequest = {expanded= !expanded}) {
                                Column(modifier = Modifier) {

                                }
                            }
                        }
                        IconButton(onClick = {
                            displayInRow =! displayInRow
                        }) {
                            if (displayInRow){
                                Icon(colPainter, contentDescription ="Row/Column Display" )
                            }
                            else{
                                Icon(gridPainter, contentDescription ="Column view" )
                            }

                        }

                    },
                    title = {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            IconButton({
                                scope.launch {
                                    drawerState.apply {
                                        if (isClosed) open() else close()
                                    }
                                }
                            }) {Icon(Icons.Filled.Menu, contentDescription = "Menu")}
                            Spacer(modifier = Modifier.padding(2.dp))
                            Text(text = "Archive")
                        }
                    })
            },
            content = { innerPadding ->
                Column(
                    modifier = Modifier
                        .padding()
                        .fillMaxSize()
                        .padding(innerPadding)
                ) {
                    if (archivedTasks.isEmpty()) {
                        EmptyList(" Archived")
                    }
                    if (displayInRow) {
                        TaskArchiveGridView(tasksVM = archivedTasks, navController = navController)

                    } else {
                        TaskArchiveColumnView(
                            tasksVM = archivedTasks,
                            navController = navController
                        )

                    }

                }
            },
        )

}

@Composable
fun TaskArchiveGridView(tasksVM: List<Task>, navController: NavController){
    LazyVerticalGrid(columns = GridCells.Adaptive(minSize = 128.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        items(tasksVM){
                task->
            TaskListCard( task, onClick = {
                navController.navigate("editTask/${task.id}")
            })
        }
    }
}

@Composable
fun TaskArchiveColumnView(tasksVM: List<Task>, navController: NavController){
    LazyColumn(modifier = Modifier) {
        items(tasksVM){
                task->
            TaskListCard(task, onClick = {
                navController.navigate("editTask/${task.id}")
            })
        }
    }//lc
}
