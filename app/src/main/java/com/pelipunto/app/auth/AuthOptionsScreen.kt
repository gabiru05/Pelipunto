package com.pelipunto.app.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun AuthOptionsScreen(
    onLoginClick: () -> Unit,
    onRegisterClick: () -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(32.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = onLoginClick,
                modifier = Modifier.padding(top = 32.dp)
            ) {
                Text("Iniciar sesión")
            }
            Button(
                onClick = onRegisterClick,
                modifier = Modifier.padding(top = 16.dp)
            ) {
                Text("Registrarse")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AuthOptionsScreenPreview() {
    AuthOptionsScreen(
        onLoginClick = {},
        onRegisterClick = {}
    )
} 