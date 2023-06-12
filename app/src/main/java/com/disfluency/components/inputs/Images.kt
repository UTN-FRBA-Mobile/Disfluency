package com.disfluency.components.inputs

import androidx.compose.ui.tooling.preview.Preview
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.disfluency.R

//TODO: Investigar context, ver si vale la pena usar corrutinas

@Preview
@Composable
fun inputImage(): Input<Bitmap?> {
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

    val onClick = { launcher.launch("image/*") }
    val buttonModifier = Modifier.size(150.dp)

    if(image == null){
        //Icon(Icons.Outlined.AccountCircle, "Add photo", tint = MaterialTheme.colorScheme.inverseSurface, modifier = Modifier.fillMaxSize())
        Image(
            painter = painterResource(id = R.drawable.add_image),
            contentDescription = "Add photo",
            modifier = buttonModifier.clickable(onClick = onClick)
        )
    } else{
        IconButton(onClick = onClick
            , modifier = buttonModifier.background(
                color = MaterialTheme.colorScheme.primary
                , shape = CircleShape
            )
        ){
            Image(
                bitmap = image!!.asImageBitmap(),
                contentDescription = "Change Image",
                contentScale = ContentScale.FillBounds
            )
        }
    }

    return Input(image, {false}, {})
}