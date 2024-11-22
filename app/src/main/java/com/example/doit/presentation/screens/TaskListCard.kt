package com.example.doit.presentation.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.doit.data.model.Task

@Composable
fun TaskListCard(task: Task, onClick:()->Unit){
    val backgroundColors = listOf(
        MaterialTheme.colorScheme.secondaryContainer,
        MaterialTheme.colorScheme.tertiaryContainer,
        MaterialTheme.colorScheme.tertiary,
        MaterialTheme.colorScheme.surfaceContainerHigh,
        MaterialTheme.colorScheme.surfaceContainerHighest
    )

    val colors =backgroundColors.random()
    OutlinedCard(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth()
            .clickable(enabled = true, onClick = {
                onClick()
            })
        , colors=CardDefaults.outlinedCardColors(colors)) {
        Row {
            Column(modifier = Modifier.padding(8.dp), verticalArrangement = Arrangement.Center) {
                Text(modifier = Modifier, color = MaterialTheme.colorScheme.onSurface, text = task.title,fontWeight = FontWeight.SemiBold)
                Spacer(modifier = Modifier.size(8.dp))
                Text(modifier = Modifier, maxLines = 10, text = task.description)
            }
        }
      

    }
}