package com.disfluency.navigation

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument
import com.disfluency.screens.*

@Composable
fun PhonoNavigationGraph(navController: NavHostController, onLogout: ()->Unit) {
    NavHost(navController, startDestination = Route.HomePhono.route) {
        composable(BottomNavigationItem.HomePhono.screenRoute.route) {
            PhonoHomeScreen(onLogout)
        }
        composable(BottomNavigationItem.Pacientes.screenRoute.route) {
            PacientesScreen(navController)
        }
        composable(BottomNavigationItem.Ejercicios.screenRoute.route) {
            PhonoEjerciciosScreen()
        }
        composable(BottomNavigationItem.Cuestionarios.screenRoute.route) {
            PhonoCuestionariosScreen()
        }
        composable(
            route = Route.Paciente.route,
            arguments = listOf(navArgument("id") {  })
        ) { backStackEntry -> //TODO: ver si hay forma de no tener que hacer el pasamanos de navController
            backStackEntry.arguments?.getString("id")?.let { SinglePatientScreen(id = it.toInt(), navController = navController) }
        }
        composable(
            route = Route.PatientExercises.route,
            arguments = listOf(navArgument("id") {  })
        ){ backStackEntry ->
            backStackEntry.arguments?.getString("id")?.let { PatientExercisesScreen(id = it.toInt()) }
        }
        composable(
            route = Route.PatientQuestionnaires.route,
            arguments = listOf(navArgument("id") {  })
        ){ backStackEntry ->
            backStackEntry.arguments?.getString("id")?.let { PatientQuestionnairesScreen(id = it.toInt()) }
        }
        composable(
            route = Route.PatientSessions.route,
            arguments = listOf(navArgument("id") {  })
        ){ backStackEntry ->
            backStackEntry.arguments?.getString("id")?.let { PatientSessionsScreen(id = it.toInt()) }
        }
        composable(Route.NuevoPaciente.route) {
            FormNewPatient()
        }
    }
}

@Composable
fun PatientNavigationGraph(navController: NavHostController, onLogout: ()->Unit) {
    NavHost(navController, startDestination = Route.HomePatient.route) {
        composable(BottomNavigationItem.HomePatient.screenRoute.route) {
            EmptyScreen("Home Paciente", onLogout)
        }
        composable(BottomNavigationItem.Ejercicios.screenRoute.route) {
            EmptyScreen("Ejercicios Paciente", onLogout)
        }
        composable(BottomNavigationItem.Cuestionarios.screenRoute.route) {
            EmptyScreen("Cuestionarios Paciente", onLogout)
        }
    }
}

@Composable
fun BottomNavigation(navController: NavController, items: List<BottomNavigationItem>) {

    NavigationBar {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        items.forEach { item ->

            NavigationBarItem(
                selected = currentRoute == item.screenRoute.route,
                icon = { Icon(item.icon, contentDescription = item.screenRoute.title) },
                label = { Text(text = item.screenRoute.title) },
                onClick = {
                    navController.navigate(item.screenRoute.route) {

                        navController.graph.startDestinationRoute?.let { screen_route ->
                            popUpTo(screen_route) {
                                saveState = true
                            }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}