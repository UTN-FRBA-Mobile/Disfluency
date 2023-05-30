package com.disfluency.screens.pacientes

import androidx.compose.ui.tooling.preview.Preview
import android.graphics.Bitmap
import android.graphics.ImageDecoder
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
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
//TODO: Investigar context, ver si vale la pena usar corrutinas

@Preview
@Composable
fun inputImage(): Input<Bitmap?>{
    var image by remember { mutableStateOf<Bitmap?>(null) }
    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.GetContent()
    ) {
        if(it != null) {
            val source = ImageDecoder.createSource(context.contentResolver, it)
            image = ImageDecoder.decodeBitmap(source)
        }
    }
    val buttonModifier = Modifier.size(150.dp).background(
        color = MaterialTheme.colorScheme.primary
        , shape = CircleShape
    )

    IconButton(
        onClick = { launcher.launch("image/*") }
        , buttonModifier
    ) {
        if(image == null){
            Icon(Icons.Outlined.Photo, "Add photo", tint = MaterialTheme.colorScheme.inverseSurface, modifier = Modifier.size(60.dp))
        } else{
            Image(
                bitmap = image!!.asImageBitmap()
                , contentDescription = "Selected Image"
                , contentScale = ContentScale.FillBounds
            )
        }
    }

    return Input(image, {false}, {})
}