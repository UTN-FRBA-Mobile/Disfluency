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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.disfluency.model.Patient
import com.disfluency.model.Phono
import com.disfluency.model.User
import com.disfluency.navigation.*
import com.disfluency.navigation.bottomNavigation.BottomNavigation
import com.disfluency.navigation.bottomNavigation.BottomNavigationItem
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.draw.clip
import com.disfluency.model.getProfilePicFromRole

@Composable
fun AppScaffold(user: User, onLogout: () -> Unit, bottomNavigationItems: List<BottomNavigationItem>, content: @Composable (NavHostController)->Unit) {
    val navController = rememberNavController()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route?: Route.HomePhono.route

    Scaffold(
        topBar = {
            if (!noTopBarRoutes.contains(currentRoute)){
                CenterAlignedTopAppBar(
                    title = { Text(text = stringResource(getItemByRoute(currentRoute).title)) },
                    navigationIcon = { /*Icon(Icons.Filled.Menu , contentDescription = "")*/ },
                    actions = { AccountSettingsButton(user, onLogout) }
                )
            }
        },
        bottomBar = {
            if (!noBottomBarRoutes.contains(currentRoute)){
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
fun AccountSettingsButton(user: User, onLogout: ()->Unit) {
    var dropdownVisible by remember { mutableStateOf(false) }
    IconButton(onClick = {
        dropdownVisible = !dropdownVisible
    }) {
        Image(
            painter= painterResource(user.profilePic()),
            contentDescription="profile pic",
            modifier = Modifier.fillMaxSize().clip(shape = CircleShape))
    }

    DropdownMenu(expanded = dropdownVisible, onDismissRequest = { dropdownVisible = false }) {
        DropdownMenuItem(
            leadingIcon = { Icon(Icons.Outlined.Logout, contentDescription = "logout" )},
            text = { Text(stringResource(R.string.logout)) },
            onClick = onLogout
        )
    }
}
