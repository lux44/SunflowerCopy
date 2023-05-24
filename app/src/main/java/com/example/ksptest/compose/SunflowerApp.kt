package com.example.ksptest.compose

import android.app.Activity
import androidx.appcompat.widget.Toolbar
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.ksptest.compose.home.HomeScreen
import com.example.ksptest.compose.home.SunFlowerPage

@Composable
fun SunflowerApp(
    onPageChange: (SunFlowerPage) -> Unit = {},
    onAttached: (Toolbar) -> Unit = {}
) {
    val navController = rememberNavController()
    SUnFlowerNavHost(
        navController = navController,
        onPageChange = onPageChange,
        onAttached = onAttached
    )
}

@Composable
fun SUnFlowerNavHost(
    navController: NavHostController,
    onPageChange: (SunFlowerPage) -> Unit = {},
    onAttached: (Toolbar) -> Unit = {}
){
    val activity = (LocalContext.current as Activity)
    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeScreen(
                onPlantClick = {},
                onPageChange = onPageChange,
                onAttached = onAttached
            )
        }
    }
}