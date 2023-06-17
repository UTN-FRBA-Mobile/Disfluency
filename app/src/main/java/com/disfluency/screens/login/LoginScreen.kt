package com.disfluency.screens.login

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.navigation.NavController
import com.disfluency.model.Patient
import com.disfluency.navigation.Route
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@Composable
fun LoginScreen(navController: NavController, loginService: LoginService) {
    var retry by remember { mutableStateOf(false) }
    var onAuthenticate by remember { mutableStateOf(false) }

    //LaunchedEffect(onAuthenticate){
        if(onAuthenticate){

            when(loginService.getUser().role){
                is Phono -> navController.navigate(Route.Home.route)
                is Patient -> TODO()
            }
            onAuthenticate = false
        }
    //}

    LoginForm(retry) { username, password ->
        CoroutineScope(Dispatchers.IO).launch {
            try {
                loginService.login(username, password)
                onAuthenticate = true
            } catch (_: UserNotFoundException) {
                retry = true
            }
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

    val submit = {onSubmit(username, password)}

    Column(Modifier.fillMaxSize().wrapContentSize(Alignment.Center), horizontalAlignment = Alignment.CenterHorizontally) {
        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            placeholder = { Text("Nombre de Usuario") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
        )

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            placeholder = { Text("Contraseña") },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Send, keyboardType = KeyboardType.Password),
            keyboardActions = KeyboardActions(onSend = {submit()}),
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
            Text("Iniciar Sesión")
        }
        if(retry){
            Text(text = "El usuario y/o la contraseña son incorrectos.", color=MaterialTheme.colorScheme.error)
        }
    }
}