package com.disfluency

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.*
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
    val pages = listOf(
        Pair("Home", Icons.Outlined.Home),
        Pair("Pacientes", Icons.Outlined.ContactMail),
        Pair("Ejercicios", Icons.Outlined.RecordVoiceOver),
        Pair("Cuestionarios", Icons.Outlined.Assignment)
    )

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = pages[selectedItem].first) },
                navigationIcon = { Icon(Icons.Filled.Menu , contentDescription = "") },
                actions = { Icon(Icons.Filled.AccountCircle, contentDescription = "") }
            )
        },
        bottomBar = {
            NavigationBar {
                pages.forEachIndexed {
                    index, (name, iconImage)->

                    NavigationBarItem(
                        selected = selectedItem == index,
                        onClick = { selectedItem = index },
                        icon = { Icon(iconImage, contentDescription = name)},
                        label = { Text(text = name) }
                    )
                }
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