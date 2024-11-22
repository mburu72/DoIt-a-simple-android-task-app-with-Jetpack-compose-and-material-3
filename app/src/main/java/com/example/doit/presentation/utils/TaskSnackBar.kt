package com.example.doit.presentation.utils

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import kotlinx.coroutines.CoroutineScope

@Composable
fun TaskSnackBar(snackBarState: SnackbarHostState, scope: CoroutineScope){

    var showProgress by remember { mutableStateOf(false) }
    SnackbarHost(hostState = snackBarState,
        snackbar = {
            Snackbar(action = {
                TextButton(onClick = {snackBarState.currentSnackbarData?.dismiss()}, content = {
                    Text(text = "My Nice SnackBar")
                    LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
                })
            }) {

            }
        }
        )
}