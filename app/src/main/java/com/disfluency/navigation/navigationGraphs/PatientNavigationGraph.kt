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
import com.disfluency.screens.patient.PatientHome

@Composable
fun PatientNavigationGraph(navController: NavHostController, user: User) {
    NavHost(navController, startDestination = Route.HomePatient.route) {
        composable(BottomNavigationItem.HomePatient.screenRoute.route) {
            PatientHome(user.role as Patient, navController)
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
        composable(Route.Ejercicio.route, listOf(navArgument("id"){})){
            it.arguments?.getString("id")?.let { id ->
                SingleExerciseScreen(id = id.toInt())
            }
        }
        composable(Route.PracticeSuccess.route){
            RecordSuccessScreen(navController)
        }
    }
}
