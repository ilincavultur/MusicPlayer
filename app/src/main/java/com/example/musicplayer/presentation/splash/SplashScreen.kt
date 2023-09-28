package com.example.musicplayer.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.musicplayer.R
import com.example.musicplayer.core.navigation.Screen
import com.example.musicplayer.ui.theme.EerieBlack

@Composable
fun SplashScreen(
    navController: NavController
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(EerieBlack)
    ) {
        val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.animation_lmm6iotc))
        val logoAnimationState = animateLottieCompositionAsState(composition = composition)
        LottieAnimation(
            composition = composition,
            progress = { logoAnimationState.progress }
        )
        if (logoAnimationState.isAtEnd && logoAnimationState.isPlaying) {
            navController.navigate(Screen.SongListScreen.route)
        }
    }
}