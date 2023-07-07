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

@Composable
fun AppNavigation(){

    val loginService: LoginService = remember { LoginService() }
    val navController = rememberNavController()

    var didLogout by remember { mutableStateOf(false) }
    /* el logout se ejecuta desde una corrutina (la idea es que pegue al backend), pero compose no
     * deja hacer el navigate desde un hilo aparte. La manera que encontre es usar el observer del remember.
     * El if de abajo lo suelen hacer con un LaunchedEffect(booleano) que lo probé y no me cambia nada.
     */

    if(didLogout){
        didLogout = false
        navController.navigate(Route.Login.route){
            popUpTo(Route.Login.route){
                inclusive = true //Sin esto, al querer ir para atras desde la pantalla "login", me devuelve a la sesion del usuario.
            }
        }
        return
        /*TODO si no lo freno aca, ejecuta lo de abajo con la ruta actual igual a la pantalla en que este.
         *Intenta volver a hacer el recorrido y al llegar al .getUsuario(), que como se hizo logout es null,
         *rompe. Recien al finalizar la ejecucion de esta tanda, vuelve a correr toda la composicion ahora sí
         *con ruta = "login". No se si es un bug mio o de compose.
         */
    }

    val onLogout: ()->Unit = {
        CoroutineScope(Dispatchers.IO).launch {
            didLogout = true
            loginService.logout()
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
                BottomNavigationItem.Asignaciones
//                BottomNavigationItem.Cuestionarios
            )){
                PhonoNavigationGraph(navController = it, loginService.getUser(), onLogout)
            }
        }
        composable(Route.HomePatient.route){
            AppScaffold(listOf(
                BottomNavigationItem.HomePatient,
                BottomNavigationItem.Ejercicios
//                BottomNavigationItem.Cuestionarios
            )){
                PatientNavigationGraph(navController = it, loginService.getUser(), onLogout)
            }
        }
    }
}