package com.example.doit

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.doit.presentation.screens.ArchiveScreen
import com.example.doit.presentation.screens.TaskEditScreen
import com.example.doit.presentation.screens.TasksScreen
import com.example.doit.presentation.screens.TrashScreen
import com.example.doit.presentation.utils.DrawerItem
import com.example.doit.presentation.utils.LocalCoroutineScope
import com.example.doit.presentation.utils.LocalDrawerState
import com.example.doit.presentation.utils.LocalNavController
import com.example.doit.taskviewmodel.TaskVMFactory
import com.example.doit.taskviewmodel.TaskVm
import com.example.doit.ui.theme.AppTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Log.d(TAG, "onCreate")
            val viewModel: TaskVm = viewModel()
            val navController = rememberNavController()
            val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
            val scope = rememberCoroutineScope()
            AppTheme {
                CompositionLocalProvider(LocalNavController provides navController, LocalDrawerState provides drawerState, LocalCoroutineScope provides scope) {
                    Column(modifier = Modifier.fillMaxSize()) {
                        TaskApp(viewModel = viewModel)
                }

            }

         }



        }}
    @Composable
    fun TaskApp(viewModel: TaskVm){
        var selectedNavItem by remember {
            mutableStateOf(0)
        }
       val drawerState= LocalDrawerState.current
        val scope= LocalCoroutineScope.current
        val navController= LocalNavController.current
        var navItems = listOf(
           DrawerItem("Home", Icons.Outlined.Home),
            DrawerItem("Archive", Icons.Default.KeyboardArrowDown),
            DrawerItem("Trash", Icons.Default.Delete),
        )
        ModalNavigationDrawer(drawerState = drawerState, gesturesEnabled = true, drawerContent = {
            ModalDrawerSheet(modifier = Modifier
                .requiredWidth(320.dp), drawerTonalElevation = 8.dp) {
                Text(modifier = Modifier.padding(horizontal = 16.dp), text = "Do It", fontSize = 24.sp
                , fontStyle = FontStyle.Italic, fontWeight = FontWeight.Bold)
                     navItems.forEachIndexed {index, item ->
                         NavigationDrawerItem(
                             modifier = Modifier.height(48.dp),
                             colors = NavigationDrawerItemDefaults.colors(unselectedContainerColor = if (selectedNavItem == index)MaterialTheme.colorScheme.tertiaryContainer else  Color.Transparent ),
                             icon = { Icon(item.iconRes, "") },
                             label = { Text(item.label)},
                             selected = selectedNavItem==index,
                             onClick = {
                                 when(item.label){
                                     "Home"->{
                                         navController.navigate("home")
                                     }
                                     "Archive"->{
                                         navController.navigate("archive")
                                     }
                                     "Trash"->{
                                         navController.navigate("trash")
                                     }
                                 }
                                 selectedNavItem=index
                                 scope.launch {
                                     drawerState.close()
                                 }
                             })
                     }

            }
        }) {

                MyApp(viewModel =viewModel)


        }
    }
    @Composable
    fun MyApp(viewModel: TaskVm){
        val navController = LocalNavController.current
        NavHost(navController = navController, startDestination = "home") {
            composable("home"){
                TasksScreen(viewModel = viewModel)
            }
            composable("edit"){
                TaskEditScreen(viewModel = viewModel,
                    taskId = taskId
                )
            }
            composable("editTask/{taskId}", arguments = listOf(navArgument("taskId"){type=
                NavType.IntType}))
            {
                    navBackStackEntry ->
                val taskId = navBackStackEntry.arguments?.getInt("taskId")
                TaskEditScreen(viewModel = viewModel,taskId=taskId)
            }
            composable("archive"){
                ArchiveScreen(viewModel = viewModel)
            }
            composable("trash"){
                TrashScreen(viewModel)
            }
        }
    }
    companion object{
        private const val TAG = "MainActivity"
    }
}