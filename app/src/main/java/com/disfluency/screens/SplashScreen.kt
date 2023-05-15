package com.disfluency.screens

import androidx.compose.animation.*
import androidx.compose.animation.graphics.ExperimentalAnimationGraphicsApi
import androidx.compose.animation.graphics.res.animatedVectorResource
import androidx.compose.animation.graphics.res.rememberAnimatedVectorPainter
import androidx.compose.animation.graphics.vector.AnimatedImageVector
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.disfluency.R
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

@OptIn(ExperimentalAnimationGraphicsApi::class)
@Composable
fun Splash(){

    val image = AnimatedImageVector.animatedVectorResource(R.drawable.disfluency_logo_animation)
    var atEnd by remember { mutableStateOf(false) }

    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = rememberAnimatedVectorPainter(animatedImageVector = image, atEnd = atEnd),
            contentDescription = "Disfluency",
            modifier = Modifier.size(170.dp, 170.dp)
        )
    }
    
    LaunchedEffect(Unit){
        //TODO: esta ok?
        while (true) {
            delay(800)
            atEnd = !atEnd
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SplashPreview(){
    Splash()
}
