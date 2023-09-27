package com.example.musicplayer.core.components.cards

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.InspectableModifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.musicplayer.R
import com.example.musicplayer.domain.models.CheckableSong
import com.example.musicplayer.domain.models.Song
import com.example.musicplayer.ui.theme.EerieBlack
import com.example.musicplayer.ui.theme.EerieBlackExtraLight
import com.example.musicplayer.ui.theme.EerieBlackLight
import com.example.musicplayer.ui.theme.PurpleAccent

@Composable
fun CheckableSongCard(
    song: CheckableSong,
    isSongChecked: (Int) -> Boolean,
    modifier: Modifier,
    onSongCardClick: () -> Unit,
    onCheckedChanged: (Int, Boolean) -> Unit,
) {
    Box(
        modifier = modifier
            .background(color = EerieBlack)
            .padding(5.dp)
            .fillMaxSize(),
    ) {

        Row(
            modifier = modifier
                .fillMaxSize()
                .clickable {
                    onSongCardClick()
                },
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Column(
                modifier = Modifier
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start
            ) {
                Row(

                ) {
                    AsyncImage(
                        model = song.coverUrl.toString(),
                        contentDescription = "image_preview",
                        modifier = Modifier
                            .size(65.dp)
                        //.clip(RoundedCornerShape(10.dp))
                    )
                    Spacer(modifier = Modifier.size(10.dp))
                    Column(
                        modifier = Modifier
                            .size(65.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.Start
                    ) {
                        SongTitleText(text = song.title.toString(), fontSize = 15.sp, color = EerieBlackExtraLight)
                        SongArtistText(text = song.artist.toString(), fontSize = 10.sp, color = EerieBlackLight)
                    }
                }
            }

            Column(
                modifier = Modifier
                    .size(65.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.End
            ) {
                Checkbox(
                    checked = isSongChecked(song.id),
                    onCheckedChange = { checked ->
                        //viewModel.checkAll.value = checked
                        song.checked = checked
                        onCheckedChanged(song.id, checked)
                    },
                    colors = CheckboxDefaults.colors(
                        checkedColor = PurpleAccent,
                        checkmarkColor = PurpleAccent
                    )
                    //modifier = Modifier.padding(12.dp, 4.dp, 0.dp, 0.dp)
                )
            }
        }
    }
}