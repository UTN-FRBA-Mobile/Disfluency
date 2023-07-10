package com.disfluency.loading.skeleton.exercise

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.disfluency.data.MockedData
import com.disfluency.loading.skeletonContent
import com.disfluency.screens.exercise.ExercisePracticeDetail
import com.disfluency.ui.theme.MyApplicationTheme
import com.valentinilk.shimmer.shimmer

@Composable
fun ExercisePracticeDetailSkeleton(){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
            .shimmer(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Box(modifier = Modifier
            .width(320.dp)
            .height(52.dp)
            .padding(8.dp)
            .background(skeletonContent, RoundedCornerShape(4.dp)))

        Box(modifier = Modifier
            .width(100.dp)
            .height(20.dp)
            .background(skeletonContent, RoundedCornerShape(4.dp)))

        Box(modifier = Modifier
            .width(350.dp)
            .height(120.dp)
            .padding(8.dp)
            .background(skeletonContent, RoundedCornerShape(4.dp)))

        ExampleRecordingSkeleton()
    }
}


@Preview(showBackground = true)
@Composable
private fun ExercisePracticeDetailSkeletonComparison(){

    MyApplicationTheme() {
//        Column() {
            ExercisePracticeDetail(practice = MockedData.practices[0], exercise = MockedData.exercises[4])

//            Spacer(modifier = Modifier.padding(8.dp))

            ExercisePracticeDetailSkeleton()
//        }
    }
}