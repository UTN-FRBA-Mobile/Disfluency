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
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.disfluency.navigation.*
import com.disfluency.navigation.bottomNavigation.BottomNavigation
import com.disfluency.navigation.bottomNavigation.BottomNavigationItem

@Composable
fun AppScaffold(bottomNavigationItems: List<BottomNavigationItem>, content: @Composable (NavHostController)->Unit) {
    val navController = rememberNavController()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route?: Route.HomePhono.route

    Scaffold(
        topBar = {
            if (!noSupportBarsRoutes.contains(currentRoute)){
                CenterAlignedTopAppBar(
                    title = { Text(text = getItemByRoute(currentRoute).title) },
                    navigationIcon = { Icon(Icons.Filled.Menu , contentDescription = "") },
                    actions = { Icon(Icons.Filled.AccountCircle, contentDescription = "") }
                )
            }
        },
        bottomBar = {
            if (!noSupportBarsRoutes.contains(currentRoute)){
                BottomNavigation(navController, bottomNavigationItems)
            }
        },
        content = { paddingValues ->
            Box(modifier = Modifier.fillMaxSize().padding(paddingValues)) {
                content(navController)
            }
        }
    )
}