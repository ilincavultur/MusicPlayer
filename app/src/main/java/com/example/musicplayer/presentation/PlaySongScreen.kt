package com.example.musicplayer.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.musicplayer.R
import com.example.musicplayer.core.components.cards.SongArtistText
import com.example.musicplayer.core.components.cards.SongTitleText
import com.example.musicplayer.ui.theme.EerieBlack
import com.example.musicplayer.ui.theme.EerieBlackLight

@Composable
fun PlaySongScreen(
    navController: NavController
) {

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
                IconButton(onClick = { /*TODO*/ }) {
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