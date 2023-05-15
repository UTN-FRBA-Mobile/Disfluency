package com.disfluency.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Assignment
import androidx.compose.material.icons.outlined.ContactMail
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.RecordVoiceOver
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavigationItem(val title: String, val icon: ImageVector, val screenRoute: String) {

    //TODO: read string values from R.strings
    object Home : BottomNavigationItem("Home", Icons.Outlined.Home, "home")
    object Pacientes : BottomNavigationItem("Pacientes", Icons.Outlined.ContactMail, "pacientes")
    object Cuestionarios : BottomNavigationItem("Cuestionarios", Icons.Outlined.Assignment, "cuestionarios")
    object Ejercicios : BottomNavigationItem("Ejercicios", Icons.Outlined.RecordVoiceOver, "ejercicios")

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