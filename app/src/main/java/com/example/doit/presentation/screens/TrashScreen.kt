package com.example.doit.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHostState
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.PopupProperties
import androidx.navigation.NavController
import com.example.doit.data.model.Task
import com.example.doit.presentation.utils.EmptyList
import com.example.doit.presentation.utils.LocalCoroutineScope
import com.example.doit.presentation.utils.LocalDrawerState
import com.example.doit.presentation.utils.LocalNavController
import com.example.doit.presentation.utils.TaskSnackBar
import com.example.doit.taskviewmodel.TaskVm
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrashScreen(viewModel: TaskVm){
    var expanded by remember {
        mutableStateOf(false)
    }
    var showSnackBar by remember {
        mutableStateOf(false)
    }
    val menuItems = listOf("Empty Trash")
    val drawerState = LocalDrawerState.current
    val scope= LocalCoroutineScope.current
    val trashedTasks by viewModel.trashedTasks.collectAsState()
    val navController = LocalNavController.current

    Scaffold(
            modifier = Modifier,
            topBar = { TopAppBar(
                actions = {
                    if (showSnackBar){
                        Snackbar(modifier = Modifier.fillMaxWidth().padding(2.dp).background(Color.Red)) {
                            Text(text = "Can't edit in trash")
                        }
                    }
                    if (expanded){
                        DropdownMenu(modifier = Modifier,properties = PopupProperties(dismissOnClickOutside = true), expanded = expanded, onDismissRequest = {expanded= !expanded}) {
                            Column(modifier = Modifier) {

                            }
                            menuItems.forEach { item ->
                                DropdownMenuItem(text = { Text(text = item)}, onClick = {
                                    when(item){
                                        "Empty Trash"->{
                                             viewModel.emptyTrash()
                                            expanded = false
                                        }
                                    }
                                })
                            }
                        }
                    }
                    IconButton(onClick = {
                        expanded = true
                    }) {
                        Icon(Icons.Default.MoreVert, contentDescription ="more" )
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
                  Text(text = "Trash")
              }
                }) },
            
            content = { innerPadding ->

                Column(
                    modifier = Modifier
                        .padding()
                        .fillMaxSize()
                        .padding(innerPadding)
                ) {
                    if (trashedTasks.isEmpty()) {
                        EmptyList( " Deleted")
                    }

                    TrashView(tasksVM = trashedTasks,showSnackBar = showSnackBar, clicked = {showSnackBar=true})


                }
            },
        )

}
@Composable
fun TrashView(tasksVM: List<Task>,showSnackBar:Boolean,clicked:(Boolean)->Unit){
    LazyColumn(modifier = Modifier) {
        items(tasksVM){
                task->
            TaskListCard(task,onClick ={
          clicked(showSnackBar)
            })
        }
    }//lc
}