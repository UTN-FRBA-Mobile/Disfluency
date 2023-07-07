package com.disfluency.components.inputs

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.disfluency.R

//TODO: Investigar context, ver si vale la pena usar corrutinas

@Composable
fun ImagePicker(state: MutableState<Int>){
    Image(painterResource(id = state.value), "Pick photo",
        Modifier.size(150.dp))

    val pickableImage: @Composable (Int, Int)->Unit = { photo, index ->
        Image(painterResource(id = photo),
            "photo_$index",
            Modifier.size(50.dp).clickable {
                state.value = photo
            }
        )
    }

    Row(horizontalArrangement = Arrangement.Center){
        listOf(
            R.drawable.avatar_12,
            R.drawable.avatar_17,
            R.drawable.avatar_26,
            R.drawable.avatar_30
        ).forEachIndexed { index, photo -> pickableImage(photo, index) }
    }
}

/*
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
}*/