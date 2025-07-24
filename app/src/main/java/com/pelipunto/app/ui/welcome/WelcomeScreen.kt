package com.pelipunto.app.ui.welcome

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.pelipunto.app.R
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun WelcomeScreen(onContinue: () -> Unit) {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .clickable { onContinue() }
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Logo o imagen de bienvenida
            // Si tienes un logo, descomenta la siguiente línea y asegúrate de tener el recurso en res/drawable o mipmap
            // Image(painter = painterResource(id = R.drawable.ic_launcher_foreground), contentDescription = "Logo", modifier = Modifier.size(120.dp))
            Spacer(modifier = Modifier.height(32.dp))
            Text(
                text = "Bienvenido a Pelipunto",
                style = MaterialTheme.typography.headlineMedium
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Toca en cualquier parte para continuar",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun WelcomeScreenPreview() {
    WelcomeScreen(onContinue = {})
}

