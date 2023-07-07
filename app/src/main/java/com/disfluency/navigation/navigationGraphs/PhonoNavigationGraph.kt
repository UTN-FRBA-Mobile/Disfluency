package com.disfluency.navigation.navigationGraphs

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.disfluency.R
import com.disfluency.model.Phono
import com.disfluency.model.User
import com.disfluency.navigation.Route
import com.disfluency.navigation.bottomNavigation.BottomNavigationItem
import com.disfluency.screens.exercise.ExercisesScreen
import com.disfluency.screens.exercise.SingleExerciseScreen
import com.disfluency.screens.exercise.TranscriptionScreen
import com.disfluency.screens.phono.*

@Composable
fun PhonoNavigationGraph(navController: NavHostController, user: User, onLogout: () -> Unit) {
    NavHost(navController, startDestination = Route.HomePhono.route) {
        composable(BottomNavigationItem.HomePhono.screenRoute.route) {
            PhonoHomeScreen()
            Button(onClick = onLogout) {
                Text(stringResource(id = R.string.logout))
            }
        }
        composable(BottomNavigationItem.Pacientes.screenRoute.route) {
            PatientsScreen(navController, user.role as Phono)
        }
        composable(BottomNavigationItem.Ejercicios.screenRoute.route) {
            ExercisesScreen(navController)
        }
        composable(BottomNavigationItem.Cuestionarios.screenRoute.route) {
            PhonoQuestionnaireScreen()
        }
        composable(BottomNavigationItem.Asignaciones.screenRoute.route) {
            AssignmentScreen(navController, user.role as Phono)
        }
        composable(
            route = Route.Paciente.route,
            arguments = listOf(navArgument("id") {  })
        ) { backStackEntry -> //TODO: ver si hay forma de no tener que hacer el pasamanos de navController
            backStackEntry.arguments?.getString("id")?.let { SinglePatientScreen(id = it, navController = navController) }
        }
        composable(
            route = Route.PatientExercises.route,
            arguments = listOf(navArgument("id") {  })
        ){ backStackEntry ->
            backStackEntry.arguments?.getString("id")?.let { PhonoExercisesScreen(id = it, navController = navController) }
        }
        composable(
            route = Route.PatientQuestionnaires.route,
            arguments = listOf(navArgument("id") {  })
        ){ backStackEntry ->
            backStackEntry.arguments?.getString("id")?.let { PatientQuestionnairesScreen(id = it) }
        }
        composable(
            route = Route.PatientSessions.route,
            arguments = listOf(navArgument("id") {  })
        ){ backStackEntry ->
            backStackEntry.arguments?.getString("id")?.let { PatientSessionsScreen(id = it) }
        }
        composable(Route.NuevoPaciente.route) {
            FormNewPatient(navController, user.role as Phono)
        }

        composable(
            route = Route.Ejercicio.route,
            arguments = listOf(navArgument("id") {  })
        ){ backStackEntry ->
            backStackEntry.arguments?.getString("id")?.let { SingleExerciseScreen(id = it.toInt()) }
        }

        composable(Route.NuevoEjercicio.route) {
            FormNewExercise()
        }

        composable(Route.PatientExerciseAssignmentDetail.route, listOf(navArgument("id"){})){
            it.arguments?.getString("id")?.let {id->
                TherapistExerciseAssignmentDetail(id, navController)
            }
        }

        composable(Route.PatientExercisePracticeDetail.route, listOf(navArgument("id"){})){
            it.arguments?.getString("id")?.let {id->
                TherapistExercisePracticeDetail(id = id, navController = navController)
            }
        }

        composable(Route.TranscriptionAnalysis.route, listOf(navArgument("id"){})){
            it.arguments?.getString("id")?.let {id->
                TranscriptionScreen(practiceId = id)
            }
        }
    }
}
