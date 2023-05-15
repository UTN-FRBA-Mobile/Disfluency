package com.disfluency.navigation

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.disfluency.screens.CuestionariosScreen
import com.disfluency.screens.EjerciciosScreen
import com.disfluency.screens.HomeScreen
import com.disfluency.screens.PacientesScreen


@Composable
fun NavigationGraph(navController: NavHostController) {
    NavHost(navController, startDestination = BottomNavigationItem.Home.screenRoute) {
        composable(BottomNavigationItem.Home.screenRoute) {
            HomeScreen()
        }
        composable(BottomNavigationItem.Pacientes.screenRoute) {
            PacientesScreen()
        }
        composable(BottomNavigationItem.Ejercicios.screenRoute) {
            EjerciciosScreen()
        }
        composable(BottomNavigationItem.Cuestionarios.screenRoute) {
            CuestionariosScreen()
        }
    }
}

@Composable
fun BottomNavigation(navController: NavController) {
    val items = listOf(
        BottomNavigationItem.Home,
        BottomNavigationItem.Pacientes,
        BottomNavigationItem.Ejercicios,
        BottomNavigationItem.Cuestionarios
    )

    NavigationBar(containerColor = MaterialTheme.colorScheme.surface) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        items.forEach { item ->

            NavigationBarItem(
                selected = currentRoute == item.screenRoute,
                icon = { Icon(painterResource(id = item.icon), contentDescription = item.title, tint = MaterialTheme.colorScheme.onSurface) },
                label = { Text(text = item.title) },
                onClick = {
                    navController.navigate(item.screenRoute) {

                        navController.graph.startDestinationRoute?.let { screen_route ->
                            popUpTo(screen_route) {
                                saveState = true
                            }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}