package com.disfluency.loading.skeleton.patient

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.disfluency.data.MockedData
import com.disfluency.loading.skeletonContent
import com.disfluency.screens.phono.ButtonPanel
import com.disfluency.ui.theme.MyApplicationTheme
import com.valentinilk.shimmer.shimmer

@Composable
fun PatientButtonPanelSkeleton(){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ){
        ActivityButtonSkeleton()
        ActivityButtonSkeleton()
        ActivityButtonSkeleton()
    }
}

@Composable
private fun ActivityButtonSkeleton(){
    Column(
        modifier = Modifier.shimmer(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = { },
            modifier = Modifier.size(42.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = skeletonContent
            ),
            contentPadding = PaddingValues(1.dp)
        ){}

        Spacer(modifier = Modifier.size(1.dp))

        Box(modifier = Modifier
            .width(75.dp)
            .height(13.dp)
            .background(skeletonContent)
        )
    }

}

@Preview(showBackground = true)
@Composable
private fun PatientListItemSkeletonComparison(){

    MyApplicationTheme() {
        Column(Modifier.padding(16.dp)) {
            ButtonPanel(patient = MockedData.patients[0], navController = rememberNavController())

            Spacer(modifier = Modifier.padding(8.dp))

            PatientButtonPanelSkeleton()
        }
    }
}