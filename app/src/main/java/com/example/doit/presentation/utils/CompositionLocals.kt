package com.example.doit.presentation.utils

import androidx.compose.material3.DrawerState
import androidx.compose.runtime.compositionLocalOf
import androidx.navigation.NavHostController
import kotlinx.coroutines.CoroutineScope

val LocalDrawerState = compositionLocalOf<DrawerState> {
    error("No DrawerState provided")
}
val LocalCoroutineScope = compositionLocalOf<CoroutineScope> {
    error("No CoroutineScope provided")
}
val LocalNavController = compositionLocalOf<NavHostController> {
    error("No NavController provided")
}