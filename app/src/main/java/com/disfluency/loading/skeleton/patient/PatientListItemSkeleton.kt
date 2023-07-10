package com.disfluency.loading.skeleton.patient

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.disfluency.components.list.items.PatientListItem
import com.disfluency.data.MockedData
import com.disfluency.loading.skeletonBackground
import com.disfluency.loading.skeletonContent
import com.disfluency.ui.theme.MyApplicationTheme
import com.valentinilk.shimmer.shimmer

@Composable
fun PatientListItemSkeleton(){
    Card(
        modifier = Modifier.shimmer(),
        colors = CardDefaults.cardColors(containerColor = skeletonBackground, contentColor = skeletonBackground)
    ) {
        ListItem(
            colors = ListItemDefaults.colors(containerColor = skeletonBackground),
            modifier = Modifier
                .height(56.dp)
                .shimmer(),
            headlineContent = {
                Box(modifier = Modifier
                    .width(200.dp)
                    .height(20.dp)
                    .padding(bottom = 2.dp)
                    .background(
                        skeletonContent
                    )
                    .shimmer())
            },
            supportingContent = {
                Box(modifier = Modifier
                    .width(90.dp)
                    .height(20.dp)
                    .padding(top = 2.dp)
                    .background(
                        skeletonContent
                    )
                    .shimmer())
            },
            leadingContent = {
                Box(modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(skeletonContent)
                    .shimmer())
            },
            trailingContent = {
                Box(modifier = Modifier
                    .width(55.dp)
                    .height(25.dp)
                    .background(skeletonContent)
                    .shimmer())
            }
        )
    }
}

@Composable
fun PatientListSkeleton(){
    val defaultCount = 5
    LazyColumn(contentPadding = PaddingValues(start = 16.dp, top = 16.dp, end = 16.dp, bottom = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)) {
        items(defaultCount) {
            PatientListItemSkeleton()
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PatientListItemSkeletonComparison(){

    MyApplicationTheme() {
        Column(Modifier.padding(16.dp)) {
            PatientListItem(patient = MockedData.patients[0])

            Spacer(modifier = Modifier.padding(8.dp))

            PatientListItemSkeleton()
        }
    }
}