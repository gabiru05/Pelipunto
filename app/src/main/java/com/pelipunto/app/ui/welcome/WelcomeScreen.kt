package com.pelipunto.app.ui.welcome

import android.net.Uri
import android.widget.VideoView
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.pelipunto.app.R
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

@Composable
fun WelcomeScreen(onContinue: () -> Unit) {
    var hasNavigated by remember { mutableStateOf(false) }
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .clickable {
                if (!hasNavigated) {
                    hasNavigated = true
                    onContinue()
                }
            },
        color = Color(8, 8, 8) // Fondo rgb(8,8,8)
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            val context = LocalContext.current
            AndroidView(
                modifier = Modifier.fillMaxSize(),
                factory = { ctx ->
                    VideoView(ctx).apply {
                        val videoUri = Uri.parse("android.resource://${ctx.packageName}/${R.raw.intro_video}")
                        setVideoURI(videoUri)
                        setOnPreparedListener { mp ->
                            mp.isLooping = false // Solo reproducir una vez
                        }
                        setOnCompletionListener {
                            if (!hasNavigated) {
                                hasNavigated = true
                                onContinue()
                            }
                        }
                        start()
                    }
                },
                update = { videoView ->
                    if (!videoView.isPlaying && !hasNavigated) {
                        videoView.start()
                    }
                }
            )
        }
    }
}

