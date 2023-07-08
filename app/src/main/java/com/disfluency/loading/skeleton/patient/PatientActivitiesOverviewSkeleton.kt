package com.disfluency.loading.skeleton.patient

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.disfluency.components.grid.TwoColumnGridItemSpan
import com.disfluency.data.MockedData
import com.disfluency.loading.skeletonBackground
import com.disfluency.loading.skeletonContent
import com.disfluency.screens.phono.ActivitiesOverview
import com.disfluency.ui.theme.MyApplicationTheme
import com.valentinilk.shimmer.shimmer

@Composable
fun PatientActivitiesOverviewSkeleton(){
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .padding(16.dp)
            .height(248.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ){
        (0..5).forEachIndexed { index, activity ->
            item(span = { TwoColumnGridItemSpan(index, 5) }) {
                ActivityOverviewCardSkeleton()
            }
        }
    }
}

@Composable
private fun ActivityOverviewCardSkeleton(){
    Card(
        colors = CardDefaults.cardColors(containerColor = skeletonBackground, contentColor = skeletonBackground),
        modifier = Modifier
            .height(72.dp)
            .fillMaxWidth().shimmer(),
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .clip(CircleShape)
                    .background(skeletonContent)
            )

            Spacer(modifier = Modifier.width(10.dp))

            Text(
                text = " ".repeat(50),
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier
                    .fillMaxHeight().background(skeletonContent)
                    .wrapContentHeight(),
                maxLines = 2,
                lineHeight = 20.sp,
                fontSize = 13.sp,
                color = Color.Black
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PatientActivitiesOverviewSkeletonComparison(){

    MyApplicationTheme() {
        Column(Modifier.padding(16.dp)) {
            ActivitiesOverview(MockedData.patients[0])

            Spacer(modifier = Modifier.padding(8.dp))

            PatientActivitiesOverviewSkeleton()
        }
    }
}