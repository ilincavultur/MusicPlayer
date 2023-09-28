package com.example.musicplayer.core.components.cards

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.musicplayer.R
import com.example.musicplayer.domain.models.Song
import com.example.musicplayer.ui.theme.EerieBlackLight
import com.example.musicplayer.ui.theme.EerieBlackMedium


@Composable
fun CurrentlyPlayingBar(
    modifier: Modifier,
    onClick: () -> Unit,
    onPlayIconClick: () -> Unit,
    playPauseIcon: Int,
    song: Song
) {
    Box(
        modifier = modifier
            .background(color = EerieBlackMedium)
            .fillMaxWidth()
            .height(70.dp)
            .padding(10.dp)
            .clickable {
                onClick()
            }
        ,
    ) {

        Row(
            modifier = modifier
                .fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.Center
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    AsyncImage(
                        model = song.coverUrl.toString(),
                        contentDescription = "image_preview",
                        modifier = Modifier
                            .size(65.dp)
                            .clip(RoundedCornerShape(10.dp))
                    )
                    Spacer(modifier = Modifier.size(10.dp))
                    Column(
                        modifier = Modifier
                            .fillMaxHeight(),
                        verticalArrangement = Arrangement.Center
                    ) {
                        SongTitleText(text = song.title.toString(), fontSize = 20.sp, color = EerieBlackLight)
                        SongArtistText(text = song.artist.toString(), fontSize = 15.sp, color = EerieBlackLight)
                    }
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.Center
            ) {
                IconButton(onClick = {
                    onPlayIconClick()
                }) {
                    Icon(painter = painterResource(id = playPauseIcon), contentDescription = "play_icon", tint = EerieBlackLight, modifier = Modifier.size(32.dp))
                }
            }
        }
    }
}