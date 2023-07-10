package com.disfluency.screens.phono

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Assignment
import androidx.compose.material.icons.outlined.Mic
import androidx.compose.material.icons.outlined.RecordVoiceOver
import androidx.compose.material3.*
import com.disfluency.R
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.disfluency.AppScaffold
import com.disfluency.components.grid.TwoColumnGridItemSpan
import com.disfluency.model.Phono
import com.disfluency.model.User
import com.disfluency.navigation.Route
import com.disfluency.ui.theme.MyApplicationTheme
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.*

@Preview
@Composable
fun Preview(){
    val phono = Phono("2341", "Luis", "Luque", R.drawable.avatar_5)
    val user = User("us", "us", phono)
    MyApplicationTheme() {
        AppScaffold(user = user, onLogout = { /*TODO*/ }, bottomNavigationItems = listOf()) {
            PhonoHomeScreen(phono = phono, rememberNavController())
        }
    }
}

@Composable
fun PhonoHomeScreen(phono: Phono, navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center)
    ) {
        WelcomeCard(phono)
        Spacer(modifier = Modifier.height(4.dp))
        PhonoButtons(navController)
    }
}

@Composable
private fun WelcomeCard(phono: Phono) {
    val datetime = LocalDateTime.now()
    val turnCount = phono.patients.count { it.daysTillNextTurnFromDate(datetime) == 0L }
    val turnCountMesssage = if(turnCount == 0) "Hoy no tenés turnos" else "Hoy tenés $turnCount turno${if(turnCount>1)"s" else ""}"

    OutlinedCard(
        Modifier
            .fillMaxWidth()
            .padding(all = 16.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(all = 12.dp)
                .height(120.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Image(
                modifier = Modifier
                    .clip(shape = CircleShape)
                    .size(size = 95.dp),
                painter = painterResource(id = R.drawable.disfluency_logo),
                contentDescription = "",
                contentScale = ContentScale.Crop,

                )
            Spacer(Modifier.width(width = 8.dp))
            Column(Modifier.wrapContentWidth(unbounded = false)) {
                Text(
                    text = "¡Hola ${phono.name}!",
                    fontWeight = FontWeight.SemiBold,
                    style = MaterialTheme.typography.titleLarge
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = datetime.format(
                        DateTimeFormatter.ofLocalizedDate ( FormatStyle.FULL )
                            .withLocale(Locale(stringResource(R.string.locale))
                            )
                    ).replaceFirstChar { it.uppercase() },
                    style = MaterialTheme.typography.labelMedium
                )

                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = turnCountMesssage, style = MaterialTheme.typography.labelMedium
                )
            }
        }
    }
}

@Composable
private fun PhonoButtons(navController: NavController) {
    val toast = Toast.makeText(LocalContext.current, stringResource(R.string.coming_soon), Toast.LENGTH_SHORT)
    data class ActivityOverviewItem(val title: String, val icon: ImageVector, val onClick: ()->Unit)

    val activities = listOf(
        ActivityOverviewItem(
            stringResource(R.string.ph_home_button_new_exercise),
            Icons.Outlined.RecordVoiceOver
        ) { toast.show() },
        ActivityOverviewItem(
            stringResource(R.string.ph_home_button_new_questionnaire),
            Icons.Outlined.Assignment
        ) { toast.show() },
        ActivityOverviewItem(
            stringResource(R.string.ph_home_button_new_session),
            Icons.Outlined.Mic
        ) { toast.show() },
        ActivityOverviewItem(
            stringResource(R.string.ph_home_button_new_patient),
            Icons.Outlined.AccountCircle
        ) { navController.navigate(Route.NuevoPaciente.route) },
    )

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .padding(16.dp)
            .height(((190 + 16) * 2).dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        activities.forEachIndexed { index, activity ->
            item(span = { TwoColumnGridItemSpan(index, activities.size) }) {
                Box(Modifier.clickable {activity.onClick()}) {
                    ActivityShortcutCard(title = activity.title, icon = activity.icon)
                }
            }
        }
    }
}

@Composable
private fun ActivityShortcutCard(title: String, icon: ImageVector){
    Card(
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
        modifier = Modifier
            .height(140.dp)
            .fillMaxWidth(),
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize()
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceAround
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold,
                minLines = 2,
                maxLines = 2,
                lineHeight = 18.sp,
                fontSize = 18.sp, //TODO: se podra hacer que se ajuste al espacio disponible?
                color = Color.Black,
                textAlign = TextAlign.Center
            )
            Surface(
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape),
                color = MaterialTheme.colorScheme.primary
            ) {
                Box(contentAlignment = Alignment.Center, modifier = Modifier.padding(12.dp)){
                    Icon(icon, title, modifier = Modifier.fillMaxSize())
                }
            }
        }
    }
}