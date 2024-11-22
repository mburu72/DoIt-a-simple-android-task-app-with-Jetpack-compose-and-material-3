package com.example.doit.presentation.utils

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.doit.R
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskTopAppBar(gridTaskDisplay: Boolean, onViewChange:(Boolean)->Unit){
    var taskSearchText by remember {
        mutableStateOf("")
    }
    val drawerState = LocalDrawerState.current
    val scope = LocalCoroutineScope.current

    val gridPainter = painterResource(id = R.drawable.baseline_grid_view_24)
    val colPainter = painterResource(id = R.drawable.outline_view_stream_24)

    TopAppBar(modifier = Modifier,title = { /*TODO*/ },
        actions = {
            Row (modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center){
                OutlinedTextField(modifier = Modifier
                    .fillMaxWidth(0.95f)
                    ,
                    leadingIcon = {
                        IconButton({
                            scope.launch {
                                drawerState.apply {
                                    if (isClosed) open() else close()
                                }
                            }
                        }) {
                            Icon(Icons.Filled.Menu, contentDescription = "Menu")
                        }

                    }, textStyle = TextStyle(fontWeight = FontWeight.SemiBold, fontSize = 16.sp),
                    placeholder = {
                        Text(modifier = Modifier, textAlign = TextAlign.Center, fontWeight = FontWeight.SemiBold, fontStyle = FontStyle.Italic, text = "Search Your Tasks")
                    }, shape = RoundedCornerShape(32.dp), value = taskSearchText, onValueChange = {
                        taskSearchText=it
                    }, trailingIcon = {
                        IconButton(onClick = {
                            onViewChange(gridTaskDisplay)
                        }) {
                            if (gridTaskDisplay){
                                Icon(colPainter, contentDescription ="Row/Column Display" )
                            }
                            else{
                                Icon(gridPainter, contentDescription ="Column view" )
                            }

                        }

                    })
            }

        }
    )//topBar
}

