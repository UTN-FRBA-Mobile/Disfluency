package com.disfluency

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.disfluency.navigation.BottomNavigation
import com.disfluency.navigation.NavigationGraph
import com.disfluency.navigation.Route
import com.disfluency.navigation.getItemByRoute
import com.disfluency.screens.login.LoginService

val loginService = LoginService()

@Composable
fun AppScaffold() {
    val navController = rememberNavController()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route?: Route.Login.route

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = getItemByRoute(currentRoute).title) },
                navigationIcon = { Icon(Icons.Filled.Menu , contentDescription = "") },
                actions = { Icon(Icons.Filled.AccountCircle, contentDescription = "") }
            )
        },
        bottomBar = {
            BottomNavigation(navController = navController)
        },
        content = { paddingValues ->
            Box(modifier = Modifier.fillMaxSize().padding(paddingValues)) {
                NavigationGraph(navController = navController, loginService)
            }
        }
    )
}