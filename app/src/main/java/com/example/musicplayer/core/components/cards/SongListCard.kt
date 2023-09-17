package com.example.musicplayer.core.components.cards


import android.widget.Space
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardElevation
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterStart
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight

import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.musicplayer.R
import com.example.musicplayer.ui.theme.EerieBlack
import com.example.musicplayer.ui.theme.EerieBlackLight
import com.example.musicplayer.ui.theme.PurpleAccent


@Composable
fun SongListCard(
    text: String,
    modifier: Modifier
) {
        Box(
            modifier = modifier.background(color = EerieBlack).padding(15.dp).size(75.dp),
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
                    SongTitleText(text = text, fontSize = 20.sp)
                    SongArtistText(text = text, fontSize = 15.sp)
                }
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

                    Image(
                        painter = painterResource(id = R.drawable.ic_launcher_background),
                        contentDescription ="",
                        modifier = Modifier
                            .size(65.dp)
                            .clip(RoundedCornerShape(10.dp))
                    )
                }
            }
        }
}