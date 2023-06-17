package com.disfluency.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavigationItem(val screenRoute: Route, val icon: ImageVector) {

    //TODO: read string values from R.strings
    object Home : BottomNavigationItem(Route.Home, Icons.Outlined.Home)
    object Pacientes : BottomNavigationItem(Route.Pacientes, Icons.Outlined.ContactMail)
    object Cuestionarios : BottomNavigationItem(Route.Cuestionarios, Icons.Outlined.Assignment)
    object Ejercicios : BottomNavigationItem(Route.Ejercicios, Icons.Outlined.RecordVoiceOver)

}

