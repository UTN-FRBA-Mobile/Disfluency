package com.disfluency

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.Logout
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.disfluency.navigation.*
import com.disfluency.navigation.bottomNavigation.BottomNavigation
import com.disfluency.navigation.bottomNavigation.BottomNavigationItem

@Composable
fun AppScaffold(onLogout: () -> Unit, bottomNavigationItems: List<BottomNavigationItem>, content: @Composable (NavHostController)->Unit) {
    val navController = rememberNavController()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route?: Route.HomePhono.route

    Scaffold(
        topBar = {
            if (!noSupportBarsRoutes.contains(currentRoute)){
                CenterAlignedTopAppBar(
                    title = { Text(text = stringResource(getItemByRoute(currentRoute).title)) },
                    navigationIcon = { /*Icon(Icons.Filled.Menu , contentDescription = "")*/ },
                    actions = { AccountSettingsButton(onLogout) }
                )
            }
        },
        bottomBar = {
            if (!noSupportBarsRoutes.contains(currentRoute)){
                BottomNavigation(navController, bottomNavigationItems)
            }
        },
        content = { paddingValues ->
            Box(modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)) {
                content(navController)
            }
        }
    )
}

@Composable
fun AccountSettingsButton(onLogout: ()->Unit) {
    var dropdownVisible by remember { mutableStateOf(false) }
    IconButton(onClick = {
        dropdownVisible = !dropdownVisible
    }) {
        Icon(Icons.Filled.AccountCircle, contentDescription = "")
    }

    DropdownMenu(expanded = dropdownVisible, onDismissRequest = { dropdownVisible = false }) {
        DropdownMenuItem(
            leadingIcon = { Icon(Icons.Outlined.Logout, contentDescription = "logout" )},
            text = { Text(stringResource(R.string.logout)) },
            onClick = onLogout
        )
    }
}
