package com.example.musicplayer.presentation

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.ui.PlayerControlView
import androidx.media3.ui.PlayerView
import androidx.navigation.NavController
import com.example.musicplayer.R
import com.example.musicplayer.core.components.cards.SongArtistText
import com.example.musicplayer.core.components.cards.SongTitleText
import com.example.musicplayer.core.navigation.Screen
import com.example.musicplayer.ui.theme.EerieBlack
import com.example.musicplayer.ui.theme.EerieBlackLight

@Composable
fun PlaySongScreen(
    navController: NavController,
    onClick: (String) -> Unit
) {
    val context = LocalContext.current
    Column(
        modifier = Modifier.fillMaxSize()
    ) {

        Row(
            verticalAlignment = Alignment.Top,
            modifier = Modifier
                .background(color = EerieBlack)
                .fillMaxWidth()
                .padding(10.dp)
                .height(20.dp)
        ) {
            Column(

            ) {
                IconButton(onClick = {
                    onClick(Screen.SongListScreen.route)
                }) {
                    Icon(painter = painterResource(id = R.drawable.ic_baseline_keyboard_arrow_down_24), contentDescription = "minimize_current_song", tint = EerieBlackLight)
                }
            }
        }


        Row(
            verticalAlignment = Alignment.Top,
            modifier = Modifier
                .background(color = EerieBlack)
                .fillMaxWidth()
                .height(410.dp)
        ) {
            SongCoverPreview(
                coverUrl = ""
            )
        }

        Row(
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.End,
            modifier = Modifier
                .background(color = EerieBlack)
                .fillMaxWidth()
                .padding(10.dp)

        ) {
            Column(
                horizontalAlignment = Alignment.End,
            ) {
                SongTitleText(text = "text", fontSize = 25.sp)
                SongArtistText(text = "text", fontSize = 20.sp)
            }
        }

        Row(
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.End,
            modifier = Modifier
                .background(color = EerieBlack)
                .fillMaxWidth()
                .padding(10.dp)

        ) {
            Column(

            ) {
                SongSlider()


            }
        }

        SongControls(context = context)
    }

}

@Composable
fun SongCoverPreview(
    coverUrl: String
) {
    Image(
        painter = painterResource(id = R.drawable.ic_launcher_background),
        contentDescription ="",
        modifier = Modifier
            .fillMaxSize()
    )
}

@Composable
fun SongSlider(

) {
    var sliderPosition by remember { mutableStateOf(0f) }
    Column {
        Slider(
            value = sliderPosition,
            onValueChange = { sliderPosition = it }
        )
        //Text(text = sliderPosition.toString())
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

                Text(text = "00:00", color = EerieBlackLight)


                Text(text = "04:20", color = EerieBlackLight)

        }
    }
}

@Composable
fun SongControls(
    context: Context
) {
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .background(color = EerieBlack),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = { /*TODO*/ }) {
            Icon(painter = painterResource(id = R.drawable.ic_baseline_skip_previous_24), contentDescription = "skip_prev", tint = EerieBlackLight, modifier = Modifier.size(25.dp))
        }

        IconButton(onClick = { /*TODO*/ }) {
            Icon(painter = painterResource(id = R.drawable.ic_baseline_pause_circle_24), contentDescription = "pause_play", tint = EerieBlackLight, modifier = Modifier.size(45.dp))
        }

        IconButton(onClick = { /*TODO*/ }) {
            Icon(painter = painterResource(id = R.drawable.ic_baseline_skip_next_24), contentDescription = "skip_next", tint = EerieBlackLight, modifier = Modifier.size(25.dp))
        }

    }
}