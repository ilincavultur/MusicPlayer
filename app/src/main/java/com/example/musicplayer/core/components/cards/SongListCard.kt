package com.example.musicplayer.core.components.cards


import android.graphics.BitmapFactory
import android.widget.Space
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardElevation
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterStart
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight

import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.media3.exoplayer.ExoPlayer
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.musicplayer.R
import com.example.musicplayer.domain.models.Song
import com.example.musicplayer.ui.theme.EerieBlack
import com.example.musicplayer.ui.theme.EerieBlackExtraLight
import com.example.musicplayer.ui.theme.EerieBlackLight
import com.example.musicplayer.ui.theme.PurpleAccent



@Composable
fun SongListCard(
    song: Song,
    modifier: Modifier,
    onSongCardClick: (Int) -> Unit,
    isPlaying: Boolean
) {
        Box(
            modifier = modifier.background(color = EerieBlack).padding(15.dp).size(75.dp),
        ) {

            Row(
                modifier = modifier
                    .fillMaxSize()
                    .clickable {
                        onSongCardClick(song.mediaId?.toInt() ?: -1)
                    },
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Column(
                    modifier = Modifier
                        .fillMaxHeight(),
                    verticalArrangement = Arrangement.Center
                ) {
                    SongTitleText(text = song.title.toString(), fontSize = 20.sp, color = EerieBlackExtraLight)
                    SongArtistText(text = song.artist.toString(), fontSize = 15.sp, color = EerieBlackLight)
                }
                if (isPlaying) {
                    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.animation_lmro1qt2))
                    val logoAnimationState = animateLottieCompositionAsState(composition = composition)
                    Column(
                        modifier = Modifier
                            .fillMaxHeight(),
                        verticalArrangement = Arrangement.Center
                    ) {
                        LottieAnimation(
                            modifier = Modifier.size(60.dp),
                            composition = composition,
                            iterations = Int.MAX_VALUE,
                        )
                    }
                }
                Column(
                    modifier = Modifier
                        .fillMaxHeight(),
                    verticalArrangement = Arrangement.Center
                ) {
                     AsyncImage(
                        model = song.coverUrl.toString(),
                        contentDescription = "image_preview",
                        modifier = Modifier
                            .size(65.dp)
                            //.clip(RoundedCornerShape(10.dp))
                    )
                }
            }
        }
}