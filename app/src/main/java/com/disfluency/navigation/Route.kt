package com.disfluency.navigation

import androidx.compose.ui.res.stringResource
import com.disfluency.R

sealed class Route(val route: String, val title: Int){
    object Login: Route("login", R.string.login_title)
    object HomePhono: Route("home", R.string.ph_home_title)
    object Pacientes: Route("pacientes", R.string.ph_patients_title)
    object Cuestionarios: Route("cuestionarios", R.string.questionnaires_title)
    object Ejercicios: Route("ejercicios", R.string.exercises_title)
    object Paciente: Route("paciente/{id}", R.string.ph_single_patient_title){
        fun routeTo(idPaciente: Int): String{
            return "paciente/$idPaciente"
        }
    }
    object NuevoPaciente: Route("nuevo-paciente", R.string.ph_new_patient_title)

    object NuevoEjercicio: Route("nuevo-ejercicio", R.string.ph_new_exercise_title)

    object Assignment: Route("asignacion", R.string.ph_exercise_asign_title)

    object Ejercicio: Route("ejercicio/{id}", R.string.exercise_detail_title){
        fun routeTo(ejercicioId: Int): String{
            return route.replace("{id}", ejercicioId.toString())
        }
    }
    object PatientExercises: Route("paciente/{id}/ejercicios", R.string.ph_single_patient_exercises_title){
        fun routeTo(patientId: Int): String{
            return route.replace("{id}", patientId.toString())
        }
    }

    object PatientQuestionnaires: Route("paciente/{id}/cuestionarios", R.string.ph_single_patient_questionnaires_title){
        fun routeTo(patientId: Int): String{
            return route.replace("{id}", patientId.toString())
        }
    }

    object PatientSessions: Route("paciente/{id}/sesiones", R.string.ph_single_patients_sessions_title){
        fun routeTo(patientId: Int): String{
            return route.replace("{id}", patientId.toString())
        }
    }

    object PatientExerciseAssignmentDetail: Route("ejercicio-asignado/{id}", R.string.pa_exercise_title){
        fun routeTo(exerciseAssignmentId: String): String{
            return route.replace("{id}", exerciseAssignmentId)
        }
    }

    //TODO: esta ok esta ruta?!
    object PatientExercisePracticeDetail: Route("ejercicio-resuelto/{id}", R.string.pa_exercise_answer_title){
        fun routeTo(practiceId: String): String{
            return route.replace("{id}", practiceId)
        }
    }

    object PatientExerciseRecordPractice: Route("ejercicio-asignado/{id}/grabar", R.string.pa_exercise_answer_new_title){
        fun routeTo(exerciseAssignmentId: String): String{
            return route.replace("{id}", exerciseAssignmentId)
        }
    }

    object HomePatient : Route("patient/home", R.string.pa_home_title)

    object PracticeSuccess: Route("ejercicio-resuelto-completado", R.string.record_success_title)

}

//Routes that are supposed to be displayed without top or nav bar
val noSupportBarsRoutes = listOf(Route.PracticeSuccess).map { it.route }

val items = Route::class.nestedClasses.map { it.objectInstance as Route }
fun getItemByRoute(route: String): Route{
    return items.first { it.route == route }
}