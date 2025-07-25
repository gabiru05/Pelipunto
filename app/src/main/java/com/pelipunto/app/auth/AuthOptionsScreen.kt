package com.pelipunto.app.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Logout
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import com.pelipunto.app.ui.components.GlassmorphicCard

@Composable
fun AuthOptionsScreen(
    onLoginClick: () -> Unit,
    onRegisterClick: () -> Unit,
    isLoggedIn: Boolean,
    onLogout: () -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            GlassmorphicCard(
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(horizontal = 16.dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
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
    }
}

@Preview(showBackground = true)
@Composable
fun AuthOptionsScreenPreview() {
    AuthOptionsScreen(
        onLoginClick = {},
        onRegisterClick = {},
        isLoggedIn = true,
        onLogout = {}
    )
} 