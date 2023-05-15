package com.disfluency

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
    var selectedItem by remember {
        mutableStateOf(0)
    }
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = "Pacientes") },
                navigationIcon = { Icon(Icons.Filled.Menu , contentDescription = "") },
                actions = { Icon(Icons.Filled.AccountBox, contentDescription = "", tint = MaterialTheme.colorScheme.onSurface) }
            )
        },
        bottomBar = {
            // Pasar a loop
            NavigationBar(containerColor = MaterialTheme.colorScheme.surface) {
                NavigationBarItem(
                    selected = selectedItem == 0,
                    onClick = { selectedItem = 0 },
                    icon = { Icon(Icons.Filled.Favorite , contentDescription = "", tint = MaterialTheme.colorScheme.onSurface)},
                    label = { Text(text = "Home") }
                )
                NavigationBarItem(
                    selected = selectedItem == 1,
                    onClick = { selectedItem = 1 },
                    icon = { Icon(Icons.Filled.Favorite , contentDescription = "", tint = MaterialTheme.colorScheme.onSurface)},
                    label = { Text(text = "Pacientes") }
                )
                NavigationBarItem(
                    selected = selectedItem == 2,
                    onClick = { selectedItem = 2 },
                    icon = { Icon(Icons.Filled.Favorite , contentDescription = "", tint = MaterialTheme.colorScheme.onSurface)},
                    label = { Text(text = "Ejercicios") }
                )
                NavigationBarItem(
                    selected = selectedItem == 3,
                    onClick = { selectedItem = 3 },
                    icon = { Icon(Icons.Filled.Favorite , contentDescription = "", tint = MaterialTheme.colorScheme.onSurface)},
                    label = { Text(text = "Cuestionarios") }
                )
            }
        },
        content = { paddingValues -> Text(text = "Test", Modifier.padding(paddingValues)) }
    )
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MyApp(content = { ScaffoldTest()} )
}