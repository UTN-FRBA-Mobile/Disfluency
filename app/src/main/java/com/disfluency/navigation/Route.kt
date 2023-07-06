package com.disfluency.navigation

import com.disfluency.model.ExerciseAssignment

sealed class Route(val route: String, val title: String){
    object Login: Route("login", "Iniciar Sesión")
    object HomePhono: Route("home", "Home")
    object Pacientes: Route("pacientes", "Pacientes")
    object Cuestionarios: Route("cuestionarios", "Cuestionarios")
    object Ejercicios: Route("ejercicios", "Ejercicios")
    object Paciente: Route("paciente/{id}", "Detalle del Paciente"){
        fun routeTo(idPaciente: Int): String{
            return "paciente/$idPaciente"
        }
    }
    object NuevoPaciente: Route("nuevo-paciente", "Nuevo Paciente")

    object NuevoEjercicio: Route("nuevo-ejercicio", "Nuevo Ejercicio")

    object Assignment: Route("asignacion", "Asignar")

    object Ejercicio: Route("ejercicio/{id}", "Detalle del ejercicio"){
        fun routeTo(ejercicioId: Int): String{
            return route.replace("{id}", ejercicioId.toString())
        }
    }
    object PatientExercises: Route("paciente/{id}/ejercicios", "Ejercicios del Paciente"){
        fun routeTo(patientId: Int): String{
            return route.replace("{id}", patientId.toString())
        }
    }

    object PatientQuestionnaires: Route("paciente/{id}/cuestionarios", "Cuestionarios del Paciente"){
        fun routeTo(patientId: Int): String{
            return route.replace("{id}", patientId.toString())
        }
    }

    object PatientSessions: Route("paciente/{id}/sesiones", "Sesiones del Paciente"){
        fun routeTo(patientId: Int): String{
            return route.replace("{id}", patientId.toString())
        }
    }

    object PatientExerciseAssignmentDetail: Route("ejercicio-asignado/{id}", "Ejercicio Asignado"){
        fun routeTo(exerciseAssignmentId: String): String{
            return route.replace("{id}", exerciseAssignmentId)
        }
    }

    //TODO: esta ok esta ruta?!
    object PatientExercisePracticeDetail: Route("ejercicio-resuelto/{id}", "Resolucion de Ejercicio"){
        fun routeTo(practiceId: String): String{
            return route.replace("{id}", practiceId)
        }
    }

    object PatientExerciseRecordPractice: Route("ejercicio-asignado/{id}/grabar", "Practica de Ejercicio"){
        fun routeTo(exerciseAssignmentId: String): String{
            return route.replace("{id}", exerciseAssignmentId)
        }
    }

    //TODO
    object HomePatient : Route("patient/home", "Home")

    object PracticeSuccess: Route("ejercicio-resuelto-completado", "Grabacion Exitosa")

}

//Routes that are supposed to be displayed without top or nav bar
val noSupportBarsRoutes = listOf(Route.PracticeSuccess).map { it.route }

val items = Route::class.nestedClasses.map { it.objectInstance as Route }
fun getItemByRoute(route: String): Route{
    return items.first { it.route == route }
}