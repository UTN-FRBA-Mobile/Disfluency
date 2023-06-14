package com.disfluency.screens.login

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.tooling.preview.Preview

@Preview
@Composable
fun LoginScreen(/*navController: NavController*/){
    var username by remember{ mutableStateOf("") }
    var password by remember{ mutableStateOf("") }
    var visiblePassword by remember { mutableStateOf(false) }

    val enabledButton = remember(username, password){
        username.isNotBlank() && password.isNotBlank()
    }

    val onSubmit = {print("\n\nenviar\n\n")}

    Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
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
            keyboardActions = KeyboardActions(onSend = {onSubmit()}),
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

        Button(onClick = onSubmit, enabled = enabledButton) {
            Text("Iniciar Sesión")
        }
    }
}