package com.disfluency.loading.skeleton.patient

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.disfluency.components.user.PatientInfoCard
import com.disfluency.data.MockedData
import com.disfluency.loading.skeletonBackground
import com.disfluency.loading.skeletonContent
import com.disfluency.ui.theme.MyApplicationTheme
import com.valentinilk.shimmer.shimmer

@Composable
fun PatientInfoCardSkeleton(){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .shimmer(),
        colors = CardDefaults.cardColors(containerColor = skeletonBackground, contentColor = skeletonBackground)
    ) {
        Row(
            modifier = Modifier
                .height(122.dp)
                .padding(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(90.dp)
                    .clip(CircleShape)
                    .background(skeletonContent)
                    .shimmer()
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 16.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Box(
                    modifier = Modifier
                        .width(170.dp)
                        .height(30.dp)
                        .background(skeletonContent)
                        .shimmer()
                )
                Spacer(modifier = Modifier.height(8.dp))

                Row() {
                    Box(modifier = Modifier
                        .width(105.dp)
                        .height(25.dp)
                        .background(skeletonContent)
                        .shimmer())

                    Spacer(modifier = Modifier.width(10.dp))

                    Box(modifier = Modifier
                        .width(55.dp)
                        .height(25.dp)
                        .background(skeletonContent)
                        .shimmer())
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PatientInfoCardSkeletonComparison(){

    MyApplicationTheme() {
        Column(Modifier.padding(16.dp)) {
            PatientInfoCard(MockedData.patients[0])

            Spacer(modifier = Modifier.padding(8.dp))

            PatientInfoCardSkeleton()
        }
    }
}