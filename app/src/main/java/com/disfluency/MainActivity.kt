package com.disfluency

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.disfluency.navigation.BottomNavigation
import com.disfluency.navigation.NavigationGraph
import com.disfluency.navigation.getItemByRoute
import com.disfluency.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApp(content = { ScaffoldTest()} )
        }
    }
}

@Composable
fun MyApp(content: @Composable () -> Unit) {
    MyApplicationTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            content()
        }
    }
}

@Composable
fun ScaffoldTest() {
    val navController = rememberNavController()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = getItemByRoute(currentRoute).title) },
                navigationIcon = { Icon(Icons.Filled.Menu , contentDescription = "") },
                actions = { Icon(Icons.Filled.AccountBox, contentDescription = "", tint = MaterialTheme.colorScheme.onSurface) }
            )
        },
        bottomBar = {
            BottomNavigation(navController = navController)
        }
    ) {
        NavigationGraph(navController = navController)
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MyApp(content = { ScaffoldTest()} )
}