package com.example.doit.presentation.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun TaskEdit(modifier: Modifier,
             taskTitle:String,
             taskDesc:String,
             onTaskTitleChange:(String)->Unit,
             onTaskDescChange:(String)->Unit){
    Column(modifier=Modifier) {
     TextField(modifier = Modifier.fillMaxWidth(),
         placeholder = { Text(text = "Title", fontSize = 24.sp)},
         colors = TextFieldDefaults.colors(focusedContainerColor = MaterialTheme.colorScheme.surface, focusedIndicatorColor = Color.Transparent, unfocusedIndicatorColor = Color.Transparent, unfocusedContainerColor = MaterialTheme.colorScheme.surface,
             focusedTextColor =MaterialTheme.colorScheme.onSurface ,
             unfocusedTextColor = MaterialTheme.colorScheme.onSurface),
         textStyle = TextStyle(fontWeight = FontWeight.Bold, fontStyle = FontStyle.Italic), singleLine = true, value = taskTitle, onValueChange ={
         onTaskTitleChange(it)
     })
     TextField(modifier = Modifier
         .fillMaxSize(),
         placeholder = { Text(text = "Task")},
         colors = TextFieldDefaults.colors(focusedContainerColor = MaterialTheme.colorScheme.surface, focusedIndicatorColor = Color.Transparent, unfocusedIndicatorColor = Color.Transparent, unfocusedContainerColor = MaterialTheme.colorScheme.surface,
             focusedTextColor =MaterialTheme.colorScheme.onSurface ,
             unfocusedTextColor = MaterialTheme.colorScheme.onSurface),value = taskDesc, onValueChange ={
        onTaskDescChange(it)
     } )
     //->end of textfield
 }
     //->end of column
}