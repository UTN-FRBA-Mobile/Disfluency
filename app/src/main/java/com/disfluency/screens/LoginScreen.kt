package com.disfluency.screens

import androidx.compose.foundation.Image
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
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.disfluency.R
import com.disfluency.data.UserNotFoundException
import com.disfluency.model.Patient
import com.disfluency.model.Phono
import com.disfluency.model.Role
import com.disfluency.navigation.Route
import com.disfluency.screens.utils.LoginService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NotSupportedUserRoleException(role: Role): Exception(role::class.toString())

@Composable
fun LoginScreen(navController: NavController, loginService: LoginService) {
    var retry by remember { mutableStateOf(false) }
    var onAuthenticate by remember { mutableStateOf(false) }

    //LaunchedEffect(onAuthenticate){
        if(onAuthenticate){
            val userRole = loginService.getUser().role
            navController.navigate(
                when(userRole){
                    is Phono -> Route.HomePhono.route
                    is Patient -> Route.HomePatient.route
                    else -> throw NotSupportedUserRoleException(userRole)
                }
            )

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

    Column(
        Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center), horizontalAlignment = Alignment.CenterHorizontally) {

        Image(
            painter = painterResource(id = R.drawable.disfluency_logo),
            contentDescription = "Disfluency",
            modifier = Modifier.size(170.dp, 170.dp)
        )

        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            placeholder = { Text(stringResource(R.string.login_label_username)) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            modifier = Modifier.padding(8.dp)
        )

        val focusManager = LocalFocusManager.current
        OutlinedTextField(
            modifier = Modifier.padding(8.dp),
            value = password,
            onValueChange = { password = it },
            placeholder = { Text(stringResource(R.string.login_label_password)) },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Send, keyboardType = KeyboardType.Password),
            keyboardActions = KeyboardActions(onSend = {
                submit()
                focusManager.clearFocus()
            }),
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