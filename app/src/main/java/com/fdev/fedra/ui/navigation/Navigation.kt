package com.fdev.fedra.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.fdev.fedra.ui.navigation.Screens
import com.fdev.fedra.ui.screens.HomeScreen
import com.fdev.fedra.ui.screens.PostScreen
import com.fdev.fedra.ui.screens.ProfileScreen

@Composable
fun Navigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screens.HomeScreen.route) {
        composable(Screens.HomeScreen.route) {
            HomeScreen()
        }

        composable(Screens.PostScreen.route) {
            PostScreen()
        }

        composable(Screens.ProfileScreen.route) {
            ProfileScreen()
        }
    }
}