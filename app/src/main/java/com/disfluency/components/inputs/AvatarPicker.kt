package com.disfluency.components.inputs

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.NavigateBefore
import androidx.compose.material.icons.filled.NavigateNext
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.disfluency.R

val USER_AVATARS = listOf(
    R.drawable.avatar_1,
    R.drawable.avatar_2,
    R.drawable.avatar_3,
    R.drawable.avatar_4,
    R.drawable.avatar_5,
    R.drawable.avatar_6,
    R.drawable.avatar_7
)

@Composable
fun AvatarPicker(selectedAvatarIndex: MutableState<Int>){
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {

        IconButton(
            modifier = Modifier.size(60.dp),
            onClick = { selectedAvatarIndex.value-- },
            enabled = selectedAvatarIndex.value > 0,
            colors = IconButtonDefaults.iconButtonColors(contentColor = MaterialTheme.colorScheme.primary, disabledContentColor = Color.Gray)
        ) {
            Icon(imageVector = Icons.Filled.NavigateBefore, contentDescription = "Previous")
        }

        Image(
            modifier = Modifier
                .size(90.dp),
            painter = painterResource(id = USER_AVATARS[selectedAvatarIndex.value]),
            contentDescription = "Avatar"
        )

        IconButton(
            modifier = Modifier.size(60.dp),
            onClick = { selectedAvatarIndex.value++ },
            enabled = selectedAvatarIndex.value < USER_AVATARS.size - 1,
            colors = IconButtonDefaults.iconButtonColors(contentColor = MaterialTheme.colorScheme.primary, disabledContentColor = Color.Gray)
        ) {
            Icon(imageVector = Icons.Filled.NavigateNext, contentDescription = "Next")
        }
    }
}