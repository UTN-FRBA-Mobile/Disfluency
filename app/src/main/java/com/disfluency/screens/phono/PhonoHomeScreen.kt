package com.disfluency.screens.phono

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Assignment
import androidx.compose.material.icons.outlined.Mic
import androidx.compose.material.icons.outlined.RecordVoiceOver
import androidx.compose.material3.*
import com.disfluency.R
import androidx.compose.runtime.*
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
import com.disfluency.components.grid.TwoColumnGridItemSpan
import com.disfluency.model.Phono

@Preview
@Composable
fun Preview(){
    val phono = Phono("2341", "Luis", "Luque", R.drawable.avatar_null)
    PhonoHomeScreen(phono = phono)
}

@Composable
fun PhonoHomeScreen(phono: Phono) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center)
    ) {
        WelcomeCard(phono)
        Spacer(modifier = Modifier.height(4.dp))
        PhonoButtons(patient = phono)
    }
}

@Composable
private fun WelcomeCard(patient: Phono) {
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
                    text = "¡Hola ${patient.name}!",
                    fontWeight = FontWeight.SemiBold,
                    style = MaterialTheme.typography.titleLarge
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Miércoles 26 de Mayo", style = MaterialTheme.typography.labelMedium
                )
            }
        }
    }
}
@Composable
fun HomeButton(text: String, icon: ImageVector) {
    val toast = Toast.makeText(LocalContext.current, stringResource(R.string.coming_soon), Toast.LENGTH_SHORT)
    Button(
        onClick = { toast.show() }, Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20)
    ) {
        Column(
            Modifier.padding(vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(icon, contentDescription = text)
            Text(text)
        }
    }
}

@Composable
private fun PhonoButtons(patient: Phono) {

    data class ActivityOverviewItem(val title: String, val icon: ImageVector, val onClick: ()->Unit)
    val context = LocalContext.current
    val comingSoonMessage = stringResource(R.string.coming_soon)

    val activities = listOf(
        ActivityOverviewItem(
            "Crear Ejercicio",
            Icons.Outlined.RecordVoiceOver
        ) {  },
        ActivityOverviewItem(
            "Crear Cuestionario",
            Icons.Outlined.Assignment
        ) { Toast.makeText(context, comingSoonMessage, Toast.LENGTH_SHORT).show() },
        ActivityOverviewItem(
            "Grabar Sesión",
            Icons.Outlined.Mic
        ) { Toast.makeText(context, comingSoonMessage, Toast.LENGTH_SHORT).show() },
        ActivityOverviewItem(
            "Registrar Paciente",
            Icons.Outlined.AccountCircle
        ) { Toast.makeText(context, comingSoonMessage, Toast.LENGTH_SHORT).show() },
    )

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .padding(16.dp)
            .height(((190+16)*2).dp),
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
            .height(190.dp)
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
                maxLines = 2,
                lineHeight = 20.sp,
                fontSize = 20.sp, //TODO: se podra hacer que se ajuste al espacio disponible?
                color = Color.Black,
                textAlign = TextAlign.Center
            )
            Surface(
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape),
                color = MaterialTheme.colorScheme.primary
            ) {
                Box(contentAlignment = Alignment.Center){
                    Icon(icon, title)
                }
            }
        }
    }
}