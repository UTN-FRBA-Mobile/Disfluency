package com.disfluency.navigation

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.disfluency.screens.CuestionariosScreen
import com.disfluency.screens.EjerciciosScreen
import com.disfluency.screens.HomeScreen
import androidx.navigation.navArgument
import com.disfluency.screens.*


@Composable
fun NavigationGraph(navController: NavHostController) {
    NavHost(navController, startDestination = BottomNavigationItem.Home.screenRoute.route) {
        composable(BottomNavigationItem.Home.screenRoute.route) {
            HomeScreen()
        }
        composable(BottomNavigationItem.Pacientes.screenRoute.route) {
            PacientesScreen(navController)
        }
        composable(BottomNavigationItem.Ejercicios.screenRoute.route) {
            EjerciciosScreen()
        }
        composable(BottomNavigationItem.Cuestionarios.screenRoute.route) {
            CuestionariosScreen()
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
            FormNewPatient(navController)
        }
    }
}

@Composable
fun BottomNavigation(navController: NavController) {
    val items = listOf(
        BottomNavigationItem.Home,
        BottomNavigationItem.Pacientes,
        BottomNavigationItem.Ejercicios,
        BottomNavigationItem.Cuestionarios
    )

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
                                saveState = false
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