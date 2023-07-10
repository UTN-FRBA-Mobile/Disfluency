package com.disfluency.screens.success

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.disfluency.R

@Composable
fun RecordSuccessScreen(navController: NavController){
    SuccessScreen(message = stringResource(R.string.pa_record_success_message)) {
        navController.popBackStack()
    }
}