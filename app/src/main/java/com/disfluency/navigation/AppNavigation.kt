package com.disfluency.navigation

import androidx.compose.runtime.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.disfluency.AppScaffold
import com.disfluency.navigation.bottomNavigation.BottomNavigationItem
import com.disfluency.navigation.navigationGraphs.PatientNavigationGraph
import com.disfluency.navigation.navigationGraphs.PhonoNavigationGraph
import com.disfluency.screens.LoginScreen
import com.disfluency.screens.SplashScreen
import com.disfluency.screens.utils.LoginService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

val loginService = LoginService()

@Composable
fun AppNavigation(){
    val navController = rememberNavController()

    var didLogout by remember { mutableStateOf(false) }
    if(didLogout){
        didLogout = false
        navController.navigate(Route.Login.route){
            popUpTo(Route.Login.route){
                inclusive = true
            }
        }
    }
    val onLogout: ()->Unit = {
        CoroutineScope(Dispatchers.IO).launch {
            loginService.logout()
            didLogout = true
        }
    }

    NavHost(navController = navController, startDestination = "splash"){
        composable("splash"){
            SplashScreen(navController = navController)
        }
        composable(Route.Login.route){
            LoginScreen(navController, loginService)
        }
        composable(Route.HomePhono.route){
            AppScaffold(listOf(
                BottomNavigationItem.HomePhono,
                BottomNavigationItem.Pacientes,
                BottomNavigationItem.Ejercicios,
                BottomNavigationItem.Cuestionarios
            )){
                PhonoNavigationGraph(navController = it, onLogout)
            }
        }
        composable(Route.HomePatient.route){
            AppScaffold(listOf(
                BottomNavigationItem.HomePatient,
                BottomNavigationItem.Ejercicios,
                BottomNavigationItem.Cuestionarios
            )){
                PatientNavigationGraph(navController = it, onLogout)
            }
        }
    }
}