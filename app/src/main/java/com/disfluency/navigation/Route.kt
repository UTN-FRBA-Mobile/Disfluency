package com.disfluency.navigation

sealed class Route(val route: String, val title: String){
    object Home: Route("home", "Home")
    object Pacientes: Route("pacientes", "Pacientes")
    object Cuestionarios: Route("cuestionarios", "Cuestionarios")
    object Ejercicios: Route("ejercicios", "Ejercicios")
    object Paciente: Route("paciente/{id}", "Detalle del Paciente"){
        fun routeTo(idPaciente: String): String{
            return "paciente/$idPaciente"
        }
    }
    object NuevoPaciente: Route("nuevo-paciente", "Nuevo Paciente")
}

fun getItemByRoute(route: String?): Route{
    val items = listOf(
        Route.Home,
        Route.Pacientes,
        Route.Cuestionarios,
        Route.Ejercicios,
        Route.Paciente,
        Route.NuevoPaciente
    )

    if (route == null) return Route.Home
    return items.first { item -> item.route == route }
}