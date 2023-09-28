package com.example.musicplayer

import android.content.ComponentName
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel

import androidx.media3.session.MediaController
import androidx.media3.session.SessionToken
import com.example.musicplayer.core.components.scaffold.AppScaffold
import com.example.musicplayer.domain.exoplayer.MusicService
import com.example.musicplayer.presentation.home.HomeViewModel
import com.example.musicplayer.ui.theme.MusicPlayerTheme
import com.google.common.util.concurrent.MoreExecutors
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)

        setContent {
            MusicPlayerTheme {
                AppScaffold()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        val sessionToken = SessionToken(this, ComponentName(this, MusicService::class.java))
        val controllerFuture = MediaController.Builder(this, sessionToken).buildAsync()
        controllerFuture.addListener(
            {

                // Call controllerFuture.get() to retrieve the MediaController.
                // MediaController implements the Player interface, so it can be
                // attached to the PlayerView UI component.
                //playerView.setPlayer(controllerFuture.get())
            },
            MoreExecutors.directExecutor()
        )
    }
}
