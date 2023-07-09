package com.disfluency.components.user

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccessTime
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.disfluency.R
import com.disfluency.components.inputs.formatDayOfWeek
import com.disfluency.data.MockedData
import com.disfluency.model.Patient
import com.disfluency.ui.theme.MyApplicationTheme
import java.time.DayOfWeek
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun PatientInfoCard(patient: Patient){
    Card(
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .height(122.dp)
                .padding(16.dp)
        ) {
            Image(
                painter = painterResource(id = patient.profilePic),
                contentDescription = "User Thumbnail",
                modifier = Modifier.size(90.dp)
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 16.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = patient.fullName(),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Black,
                    maxLines = 2
                )

                Spacer(modifier = Modifier.height(4.dp))

                FlowRow {
                    IconLabeled(
                        icon = Icons.Outlined.CalendarMonth,
                        label = weeklyTurnFormat(patient.weeklyTurn),
                        content = "Turn"
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    IconLabeled(
                        icon = Icons.Outlined.AccessTime,
                        label = patient.weeklyHour.format(DateTimeFormatter.ofPattern(stringResource(R.string.time_format))),
                        content = "Time"
                    )
                }
            }
        }
    }
}

@Composable
fun weeklyTurnFormat(weeklyTurn: List<DayOfWeek>): String{
    return if(weeklyTurn.size>1){
        val lastDay = formatDayOfWeek(weeklyTurn.last())
        val daysBeforeLast = weeklyTurn.dropLast(1).map{ formatDayOfWeek(it) }
        "${daysBeforeLast.joinToString(", ")} ${stringResource(id = R.string.symbol_and)} $lastDay"
    }

    else weeklyTurn.joinToString { formatDayOfWeek(it) }
}

@Composable
fun IconLabeled(icon: ImageVector, label: String, content: String){
    Row {
        Icon(
            imageVector = icon,
            contentDescription = content,
            modifier = Modifier.size(20.dp)
        )
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium,
            color = Color.Black,
            modifier = Modifier
                .padding(start = 2.dp)
                .height(20.dp)
                .wrapContentHeight()
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewInfoCard(){
    MyApplicationTheme() {
        PatientInfoCard(patient = MockedData.patients.first())
    }
}