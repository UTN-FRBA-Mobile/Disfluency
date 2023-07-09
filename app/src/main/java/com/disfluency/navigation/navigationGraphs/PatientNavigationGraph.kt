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
import com.disfluency.screens.exercise.*
import com.disfluency.screens.patient.PatientExerciseAssignmentsScreen
import com.disfluency.screens.utils.EmptyScreen

@Composable
fun PatientNavigationGraph(navController: NavHostController, user: User) {
    val patient = user.role as Patient
    NavHost(navController, startDestination = Route.HomePatient.route) {
        composable(BottomNavigationItem.HomePatient.screenRoute.route) {
            EmptyScreen("Home Paciente")
        }
        composable(BottomNavigationItem.Ejercicios.screenRoute.route) {
            PatientExerciseAssignmentsScreen(navController, patient.id) //TODO: Revisar
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
        composable(Route.PatientExercisePracticeDetail.route, listOf(navArgument("id"){}, navArgument("practiceId"){})){
            it.arguments?.let {args ->
                ExercisePracticeDetailScreen(practiceId = args.getString("practiceId")!!, assignmentId = args.getString("id")!!)
            }
        }
        composable(Route.Ejercicio.route, listOf(navArgument("id"){})){
            it.arguments?.getString("id")?.let { id ->
                SingleExerciseScreen(id = id)
            }
        }
        composable(Route.PracticeSuccess.route){
            RecordSuccessScreen(navController)
        }
    }
}
