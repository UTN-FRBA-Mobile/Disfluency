package com.disfluency.loading.skeleton.exercise

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.disfluency.AppScaffold
import com.disfluency.data.MockedData
import com.disfluency.loading.skeletonBackground
import com.disfluency.loading.skeletonContent
import com.disfluency.screens.exercise.ExampleRecording
import com.disfluency.ui.theme.MyApplicationTheme
import com.valentinilk.shimmer.shimmer

@Composable
fun SingleExerciseSkeleton(){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
            .shimmer(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(modifier = Modifier
                .width(200.dp)
                .height(40.dp)
                .background(skeletonBackground))

            Box(modifier = Modifier
                .padding(8.dp)
                .width(350.dp)
                .height(100.dp)
                .background(skeletonBackground))

            Box(modifier = Modifier
                .padding(bottom = 4.dp, top = 24.dp)
                .width(80.dp)
                .height(20.dp)
                .background(skeletonBackground))

            Box(modifier = Modifier
                .padding(8.dp)
                .width(350.dp)
                .height(120.dp)
                .background(skeletonBackground))
        }

        ExampleRecordingSkeleton()
    }
}

@Composable
fun ExampleRecordingSkeleton(){
    Column(
        Modifier
            .padding(top = 16.dp)
            .shimmer()) {

        Box(modifier = Modifier
            .width(200.dp)
            .height(20.dp)
            .padding(vertical = 4.dp)
            .background(skeletonBackground))

        AudioPlayerSkeleton()
    }
}

@Composable
fun AudioPlayerSkeleton(){
    Box(modifier = Modifier
        .fillMaxWidth()
        .height(60.dp)
        .clip(RoundedCornerShape(10.dp))
        .background(skeletonBackground)
        .shimmer()
    ) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically) {
            //button
            Box(modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(skeletonContent))

            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ){
                //slider
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    .padding(horizontal = 4.dp)
                    .clip(CircleShape)
                    .background(skeletonContent))
                //time
                Text(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 8.dp, vertical = 8.dp)
                        .wrapContentSize(align = Alignment.BottomStart)
                        .background(skeletonContent),
                    text = " ".repeat(12),
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.Gray,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SingleExerciseSkeletonComparison(){

    MyApplicationTheme() {
        AppScaffold(bottomNavigationItems = listOf()) {
            SingleExerciseSkeleton()
//        SingleExerciseScreen(MockedData.exercises[3].id)
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ExampleRecordingSkeletonComparison(){

    MyApplicationTheme() {
        Column(Modifier.padding(16.dp)) {
            ExampleRecording(MockedData.testUrl, "Ejemplo generado por el profesional")

            Spacer(modifier = Modifier.padding(8.dp))

            ExampleRecordingSkeleton()
        }
    }
}