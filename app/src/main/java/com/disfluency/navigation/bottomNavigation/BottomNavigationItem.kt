package com.disfluency.navigation.bottomNavigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Assignment
import androidx.compose.material.icons.outlined.ContactMail
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.RecordVoiceOver
import androidx.compose.ui.graphics.vector.ImageVector
import com.disfluency.navigation.Route

sealed class BottomNavigationItem(val screenRoute: Route, val icon: ImageVector) {

    //TODO: read string values from R.strings
    object HomePhono : BottomNavigationItem(Route.HomePhono, Icons.Outlined.Home)
    object Pacientes : BottomNavigationItem(Route.Pacientes, Icons.Outlined.ContactMail)
    object Cuestionarios : BottomNavigationItem(Route.Cuestionarios, Icons.Outlined.Assignment)
    object Ejercicios : BottomNavigationItem(Route.Ejercicios, Icons.Outlined.RecordVoiceOver)
    object Asignaciones : BottomNavigationItem(Route.Assignment, Icons.Outlined.Assignment)


    object HomePatient : BottomNavigationItem(Route.HomePatient, Icons.Outlined.Home)
}
