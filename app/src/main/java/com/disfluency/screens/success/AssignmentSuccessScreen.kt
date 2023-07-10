package com.disfluency.screens.success

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.disfluency.navigation.Route

@Composable
fun AssignmentSuccessScreen(navController: NavController){
    SuccessScreen(message = "Se asignaron los ejercicios correctamente", delay = false) {
        navController.popBackStack()
        navController.navigate(Route.HomePhono.route)
    }
}