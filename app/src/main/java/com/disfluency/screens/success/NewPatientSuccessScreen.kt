package com.disfluency.screens.success

import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@Composable
fun NewPatientSuccessScreen(navController: NavController){
    SuccessScreen(message = "Se creo el paciente correctamente", delay = false) {
        navController.popBackStack()
    }
}