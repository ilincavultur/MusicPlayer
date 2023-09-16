package com.example.musicplayer.core.components.cards


import android.widget.Space
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.CardElevation
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import java.lang.reflect.Modifier

@Composable
@Preview
fun SongListCard(

) {
    Row(
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        androidx.compose.material.Card(

        ) {

                Column {
                    Text(text = "Title")
                    Text(text = "Artist")
                }

                Column {
                    Text(text = "Picture")
                }


        }
    }

}