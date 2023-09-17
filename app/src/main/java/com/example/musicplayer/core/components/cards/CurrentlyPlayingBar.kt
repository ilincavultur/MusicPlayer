package com.example.musicplayer.core.components.cards

import android.widget.Space
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon

import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.musicplayer.R
import com.example.musicplayer.core.navigation.Screen
import com.example.musicplayer.ui.theme.EerieBlackLight
import com.example.musicplayer.ui.theme.EerieBlackMedium


@Composable
fun CurrentlyPlayingBar(
    modifier: Modifier,
    onClick: (String) -> Unit
) {
    Box(
        modifier = modifier
            .background(color = EerieBlackMedium)
            .fillMaxWidth()
            .height(70.dp)
            .padding(10.dp)
            .clickable {
                onClick(Screen.PlaySongScreen.route)
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

//                     AsyncImage(
//                        model = imagePlaceholderUri,
//                        contentDescription = "image_preview",
//                        modifier = Modifier
//                            .clickable {
//                                onImagePlaceholderClick()
//                            }
//                            .size(Constants.FEED_CARD_MEDIA_WIDTH, Constants.FEED_CARD_MEDIA_HEIGHT)
//                            .aspectRatio(FEED_CARD_MEDIA_WIDTH / FEED_CARD_MEDIA_HEIGHT)
//                    )

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_launcher_background),
                        contentDescription ="",
                        modifier = Modifier
                            .size(60.dp)
                            .clip(RoundedCornerShape(10.dp))
                    )
                    Spacer(modifier = Modifier.size(10.dp))
                    Column(
                        modifier = Modifier
                            .fillMaxHeight(),
                        verticalArrangement = Arrangement.Center
                    ) {
                        SongTitleText(text = "text", fontSize = 20.sp)
                        SongArtistText(text = "text", fontSize = 15.sp)
                    }

                }

            }

            Column(
                modifier = Modifier
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.Center
            ) {
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(painter = painterResource(id = R.drawable.ic_baseline_play_arrow_24), contentDescription = "play_icon", tint = EerieBlackLight, modifier = Modifier.size(32.dp))
                }
            }
        }
    }
}