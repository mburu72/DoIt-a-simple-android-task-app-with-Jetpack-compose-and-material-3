package com.example.doit.presentation.utils

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun TaskFAB(onClickAct:()-> Unit){
    FloatingActionButton(onClick = {onClickAct()}, modifier = Modifier.shadow(8.dp, shape = RoundedCornerShape(16.dp), ambientColor = Color.DarkGray),
        shape = RoundedCornerShape(16.dp)) {
        Icon(Icons.TwoTone.Add,contentDescription ="New Task" , modifier = Modifier.size(32.dp, 32.dp))
    }
}
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun TaskFABPreview(){
  //  TaskFAB(onClickAct = )
}