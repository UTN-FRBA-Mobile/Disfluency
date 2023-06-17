package com.disfluency.navigation

sealed class Route(val route: String, val title: String){
    object Login: Route("login", "Iniciar Sesión")
    object Home: Route("home", "Home")
    object Pacientes: Route("pacientes", "Pacientes")
    object Cuestionarios: Route("cuestionarios", "Cuestionarios")
    object Ejercicios: Route("ejercicios", "Ejercicios")
    object Paciente: Route("paciente/{id}", "Detalle del Paciente"){
        fun routeTo(idPaciente: Int): String{
            return "paciente/$idPaciente"
        }
    }
    object NuevoPaciente: Route("nuevo-paciente", "Nuevo Paciente")

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

}

val items = Route::class.nestedClasses.map { it.objectInstance as Route }
fun getItemByRoute(route: String): Route{
    print("\n\nRoute.getItemByRoute($route) entre ${items.map{it.route}}\n\n")
    return items.first { it.route == route }
}