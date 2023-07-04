package com.disfluency.navigation.navigationGraphs

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.disfluency.model.Patient
import com.disfluency.model.User
import com.disfluency.navigation.Route
import com.disfluency.navigation.bottomNavigation.BottomNavigationItem
import com.disfluency.screens.exercise.ExerciseAssignmentDetail
import com.disfluency.screens.exercise.ExercisePracticeDetailScreen
import com.disfluency.screens.exercise.ExerciseRecordingScreen
import com.disfluency.screens.patient.PatientExerciseAssignmentsScreen
import com.disfluency.screens.utils.EmptyScreen

@Composable
fun PatientNavigationGraph(navController: NavHostController, user: User, onLogout: () -> Unit) {
    NavHost(navController, startDestination = Route.HomePatient.route) {
        composable(BottomNavigationItem.HomePatient.screenRoute.route) {
            EmptyScreen("Home Paciente", onLogout)
        }
        composable(BottomNavigationItem.Ejercicios.screenRoute.route) {
            PatientExerciseAssignmentsScreen(navController, user.role as Patient) //TODO: Revisar
        }

        composable(Route.PatientExerciseAssignmentDetail.route, listOf(navArgument("id"){})){
            it.arguments?.getString("id")?.let {id->
                ExerciseAssignmentDetail(id, navController)
            }
        }
        composable(Route.PatientExerciseRecordPractice.route, listOf(navArgument("id"){})){
            it.arguments?.getString("id")?.let {id->
                ExerciseRecordingScreen(id, navController)
            }
        }
        composable(Route.PatientExercisePracticeDetail.route, listOf(navArgument("id"){})){
            it.arguments?.getString("id")?.let {id->
                ExercisePracticeDetailScreen(practiceId = id)
            }
        }
    }
}
