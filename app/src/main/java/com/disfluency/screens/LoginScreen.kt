package com.disfluency.screens

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.graphics.ExperimentalAnimationGraphicsApi
import androidx.compose.animation.graphics.res.animatedVectorResource
import androidx.compose.animation.graphics.res.rememberAnimatedVectorPainter
import androidx.compose.animation.graphics.vector.AnimatedImageVector
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.navigation.NavController
import com.disfluency.R
import com.disfluency.data.UserNotFoundException
import com.disfluency.dataStore
import com.disfluency.model.Patient
import com.disfluency.model.Phono
import com.disfluency.model.Role
import com.disfluency.navigation.Route
import com.disfluency.screens.utils.LoginService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

private val OFFSET = 120.dp

class NotSupportedUserRoleException(role: Role): Exception(role::class.toString())

@Composable
fun LoginScreen(navController: NavController, loginService: LoginService) {
    var retry by remember { mutableStateOf(false) }
    var onAuthenticate by remember { mutableStateOf(false) }
    var onSubmit by remember { mutableStateOf(false) }

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


    if (onAuthenticate) {
        LaunchedEffect(Unit) {
            val userRole = loginService.getUser().role
            navController.navigate(
                when (userRole) {
                    is Phono -> Route.HomePhono.route
                    is Patient -> Route.HomePatient.route
                    else -> throw NotSupportedUserRoleException(userRole)
                }
            )
            onSubmit = false
            onAuthenticate = false
        }
    }

    val submitAction: (String, String)->Unit = { username, password ->
        CoroutineScope(Dispatchers.IO).launch {
            try {
                onSubmit = true
                delay(2000)
                Log.i("LOGIN", "Username: $username")
                loginService.login(username, password, fcmToken.value)
                onAuthenticate = true
            } catch (_: UserNotFoundException) {
                retry = true
                onSubmit = false
            }
        }
    }

    val finishedLogoAnimation = remember { mutableStateOf(!loginService.isFirstLogin()) }

    Box(modifier = Modifier.fillMaxSize()){
        AnimatedVisibility(visible = finishedLogoAnimation.value, enter = fadeIn(animationSpec = tween(delayMillis = 500))) {
            LoginForm(retry, submitAction)
        }
        AnimatedLogo(finishedAnimation = finishedLogoAnimation)
    }

    if (onSubmit) {
        Box(
            Modifier
                .fillMaxSize()
                .background(Color.Gray.copy(0.2f)), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    }
}

@Composable
fun LoginForm(retry: Boolean, onSubmit: (String, String)->Unit){
    var username by remember{ mutableStateOf("") }
    var password by remember{ mutableStateOf("") }
    var visiblePassword by remember { mutableStateOf(false) }

    val enabledButton = remember(username, password){
        username.isNotBlank() && password.isNotBlank()
    }

    val focusManager = LocalFocusManager.current

    val submit = {
        focusManager.clearFocus()
        onSubmit(username, password)
    }

    Column(
        Modifier
            .fillMaxSize().offset(y = OFFSET / 2)
            .wrapContentSize(Alignment.Center), horizontalAlignment = Alignment.CenterHorizontally) {

        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            placeholder = { Text(stringResource(R.string.login_label_username)) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            modifier = Modifier.padding(8.dp)
        )

        OutlinedTextField(
            modifier = Modifier.padding(8.dp),
            value = password,
            onValueChange = { password = it },
            placeholder = { Text(stringResource(R.string.login_label_password)) },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Send, keyboardType = KeyboardType.Password),
            keyboardActions = KeyboardActions(onSend = { submit() }),
            singleLine = true,
            visualTransformation = if(visiblePassword) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                val icon = if (visiblePassword) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                val description = "${if (visiblePassword) "Hide " else "Show "} password"

                IconButton({visiblePassword = !visiblePassword}){
                    Icon(imageVector  = icon, description)
                }
            }
        )
        
        Button(onClick = submit, enabled = enabledButton) {
            Text(stringResource(R.string.login_button_submit))
        }
        if(retry){
            Text(stringResource(R.string.login_error_message), color=MaterialTheme.colorScheme.error)
        }
    }
}

@OptIn(ExperimentalAnimationGraphicsApi::class)
@Composable
fun AnimatedLogo(finishedAnimation: MutableState<Boolean>){
    val animationCount = 5

    val image = AnimatedImageVector.animatedVectorResource(R.drawable.disfluency_logo_animation)
    var atEnd by remember { mutableStateOf(finishedAnimation.value) }

    val offset = animateDpAsState(targetValue = if (finishedAnimation.value) OFFSET else 0.dp,
        animationSpec = tween(durationMillis = 1000)
    )

    Column(
        Modifier
            .fillMaxSize()
            .offset(y = -offset.value),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = rememberAnimatedVectorPainter(animatedImageVector = image, atEnd = atEnd),
            contentDescription = stringResource(R.string.app_name),
            modifier = Modifier.size(170.dp, 170.dp)
        )
    }

    LaunchedEffect(Unit){
        if (!finishedAnimation.value){
            var count = 0

            while (count < animationCount) {
                delay(800)
                atEnd = !atEnd
                count++
            }
            finishedAnimation.value = true
        }
    }
}