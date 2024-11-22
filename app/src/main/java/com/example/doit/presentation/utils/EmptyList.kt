package com.example.doit.presentation.utils

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.doit.R

@Composable
fun EmptyList(text: String?){
    val painter = painterResource(id = R.drawable.baseline_lightbulb_outline_24)
   Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center){
       Icon(modifier = Modifier.size(128.dp, 128.dp), tint = MaterialTheme.colorScheme.tertiary, painter = painter, contentDescription = "Empty tasks, Add tasks")
       Text(text = "Your$text tasks will appear here",fontWeight = FontWeight.Thin, fontSize = 16.sp)
   }

}