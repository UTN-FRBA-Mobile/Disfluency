package com.disfluency.navigation

import com.disfluency.R

sealed class BottomNavigationItem(val title: String, val icon: Int, val screenRoute: String) {

    //TODO: read string values from R.strings
    object Home : BottomNavigationItem("Home", R.drawable.ic_home, "home")
    object Pacientes : BottomNavigationItem("Pacientes", R.drawable.ic_pacientes, "pacientes")
    object Cuestionarios : BottomNavigationItem("Cuestionarios", R.drawable.ic_cuestionarios, "cuestionarios")
    object Ejercicios : BottomNavigationItem("Ejercicios", R.drawable.ic_ejercicios, "ejercicios")

}

fun getItemByRoute(route: String?): BottomNavigationItem{
    val items = listOf(
        BottomNavigationItem.Home,
        BottomNavigationItem.Pacientes,
        BottomNavigationItem.Ejercicios,
        BottomNavigationItem.Cuestionarios
    )

    if (route == null) return BottomNavigationItem.Home
    return items.first { item -> item.screenRoute == route }
}