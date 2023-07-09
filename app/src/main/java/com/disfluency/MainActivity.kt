package com.disfluency

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.core.app.ActivityCompat
import com.disfluency.navigation.AppNavigation
import com.disfluency.ui.theme.MyApplicationTheme
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

//TODO: migrar a ingles en todo lo que dejamos en español

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ActivityCompat.requestPermissions(
            this,
            arrayOf(android.Manifest.permission.RECORD_AUDIO),
            0
        )

        setContent {
            MyApp(content = { AppNavigation() } )
        }
    }


}

@Composable
fun MyApp(content: @Composable () -> Unit) {
    val context = LocalContext.current
    val gckTokenKey = stringPreferencesKey("gcm_token")
    val fcmToken = flow<String> {
        context.dataStore.data.map {
            it[gckTokenKey]
        }.collect(collector = {
            if (it != null){
                this.emit(it)
            }
        })
    }.collectAsState(initial = "")
    Log.v("FCMTOKEN", fcmToken.value)

    MyApplicationTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            content()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MyApp(content = { AppNavigation() } )
}