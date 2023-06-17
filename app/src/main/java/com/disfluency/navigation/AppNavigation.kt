package com.disfluency.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.disfluency.AppScaffold
import com.disfluency.screens.SplashScreen

@Composable
fun AppNavigation(){
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "splash"){
        composable("splash"){
            SplashScreen(navController = navController)
        }
        composable(Route.Login.route){
            AppScaffold()
        }
    }
}