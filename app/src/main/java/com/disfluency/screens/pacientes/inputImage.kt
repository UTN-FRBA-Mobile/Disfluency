package com.disfluency.screens.pacientes

import androidx.compose.ui.tooling.preview.Preview
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Photo
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
//TODO: Investigar context, ver si vale la pena usar corrutinas

@Preview
@Composable
fun inputImage(): Input<Bitmap?>{
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var imageAsBitmap by remember { mutableStateOf<Bitmap?>(null) }
    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.GetContent()
    ) {
        imageUri = it
        if(imageUri != null) {
            val source = ImageDecoder.createSource(context.contentResolver, imageUri!!)
            imageAsBitmap = ImageDecoder.decodeBitmap(source)
        }
    }

    val onClick = { launcher.launch("image/*") }

     val modifier = Modifier
         .size(150.dp)
         .border(border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary), shape = CircleShape)

    if(imageUri == null){
        Button(onClick, modifier) {
            Icon(Icons.Outlined.Photo, "Add photo", tint = MaterialTheme.colorScheme.inverseSurface, modifier = Modifier.size(60.dp))
        }
    } else{
        imageAsBitmap?.let {
            Image(
                bitmap = it.asImageBitmap(),
                contentDescription = "Selected Image"
                , modifier = modifier
                    .clip(CircleShape)
                    .clickable(onClick = onClick)
                    .background(color = MaterialTheme.colorScheme.primary)
            )
        }
    }

    return Input(imageAsBitmap, {false}, {})
}