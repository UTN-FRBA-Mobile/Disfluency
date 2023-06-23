package com.disfluency.navigation

sealed class Route(val route: String, val title: String){
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

    object NuevoEjercicio: Route("nuevo-ejercicio", "Nuevo Ejercicio")


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
}

fun getItemByRoute(route: String?): Route{
    //TODO: ver si hay forma de que esta lista se arme al compilar y no cada vez que se llama al metodo
    val items = Route::class.nestedClasses.map { it.objectInstance as Route }

    if (route == null) return Route.Home
    return items.first { item -> item.route == route }
}