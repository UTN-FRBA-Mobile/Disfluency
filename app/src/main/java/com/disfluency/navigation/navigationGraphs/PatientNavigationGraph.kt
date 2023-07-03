package com.disfluency.navigation.navigationGraphs

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.disfluency.model.Patient
import com.disfluency.model.User
import com.disfluency.navigation.bottomNavigation.BottomNavigationItem
import com.disfluency.navigation.Route
import com.disfluency.screens.exercise.SingleExerciseScreen
import com.disfluency.screens.patient.PatientExercisesScreen
import com.disfluency.screens.phono.PhonoExercisesScreen
import com.disfluency.screens.utils.EmptyScreen

@Composable
fun PatientNavigationGraph(navController: NavHostController, user: User, onLogout: () -> Unit) {
    NavHost(navController, startDestination = Route.HomePatient.route) {
        composable(BottomNavigationItem.HomePatient.screenRoute.route) {
            EmptyScreen("Home Paciente", onLogout)
        }
        composable(BottomNavigationItem.Ejercicios.screenRoute.route) {
            PatientExercisesScreen(navController, user.role as Patient) //TODO: Revisar
        }

        composable(Route.Ejercicio.route, listOf(navArgument("id"){})){
            it.arguments?.getString("id")?.let {id->
                EmptyScreen("Ejercicio $id", onLogout)
            }
        }

/*        composable(BottomNavigationItem.Cuestionarios.screenRoute.route) {
            EmptyScreen("Cuestionarios Paciente", onLogout)
        }
 */
    }
}
