package com.pelipunto.app.ui.settings

import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pelipunto.app.ui.components.GlassmorphicCard

@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    onLogoutClicked: () -> Unit,
    userName: String? = null,
    userEmail: String? = null
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(32.dp),
        contentAlignment = Alignment.Center
    ) {
        GlassmorphicCard(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Fila para icono y texto "Ajustes"
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 8.dp, top = 8.dp, end = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Filled.Settings,
                        contentDescription = "Ajustes",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(40.dp) // Icono más pequeño
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = "Ajustes",
                        style = MaterialTheme.typography.headlineMedium.copy(fontSize = 34.sp), // Letra más grande
                        color = MaterialTheme.colorScheme.onBackground,
                        maxLines = 1
                    )
                }
                Spacer(modifier = Modifier.height(24.dp))
                // Caja invisible con nombre y correo
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.Start
                    ) {
                        if (!userName.isNullOrBlank()) {
                            Text(
                                text = userName,
                                style = MaterialTheme.typography.bodyLarge.copy(fontSize = 20.sp),
                                color = MaterialTheme.colorScheme.onBackground,
                                modifier = Modifier.padding(bottom = 4.dp)
                            )
                        }
                        if (!userEmail.isNullOrBlank()) {
                            Text(
                                text = userEmail,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(24.dp))
                // Centrar el resto de los elementos
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(48.dp))
                    Button(
                        onClick = onLogoutClicked,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.error
                        )
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.Logout,
                            contentDescription = "Cerrar Sesión"
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = "Cerrar Sesión")
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SettingsScreenPreview() {
    SettingsScreen(onLogoutClicked = {})
}