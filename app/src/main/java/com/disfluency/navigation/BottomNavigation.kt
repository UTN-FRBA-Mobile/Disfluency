package com.disfluency.navigation

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument
import com.disfluency.components.text.AdjustableSizeText
import com.disfluency.components.text.TEXT_SCALE_REDUCTION_INTERVAL
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
            FormNewPatient()
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

    var textSize by remember { mutableStateOf(15.sp) }
    val onSizeChange = {
        textSize = textSize.times(TEXT_SCALE_REDUCTION_INTERVAL)
    }

    NavigationBar {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        items.forEach { item ->

            NavigationBarItem(
                selected = currentRoute == item.screenRoute.route,
                icon = { Icon(item.icon, contentDescription = item.screenRoute.title) },
                label = {
                    AdjustableSizeText(text = item.screenRoute.title, textSize = textSize, onSizeChange = onSizeChange)
                },
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


