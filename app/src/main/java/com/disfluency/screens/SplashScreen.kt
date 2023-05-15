package com.disfluency.screens

import android.window.SplashScreen
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.disfluency.R
import com.disfluency.navigation.BottomNavigation
import com.disfluency.navigation.BottomNavigationItem
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController){
    LaunchedEffect(key1 = true){
        delay(3000)
        navController.popBackStack()
        navController.navigate(BottomNavigationItem.Home.screenRoute)
    }

    Splash()
}

@Composable
fun Splash(){
    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.disfluency_logo),
            contentDescription = "Disfluency",
            modifier = Modifier.size(170.dp, 170.dp)
        )
        
        Spacer(modifier = Modifier.height(8.dp))

        LinearProgressIndicator()
    }
}

@Preview(showBackground = true)
@Composable
fun SplashPreview(){
    Splash()
}
