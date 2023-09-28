package com.example.musicplayer.core.components.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.musicplayer.presentation.playlist.PlaylistScreenEvent
import com.example.musicplayer.presentation.playlist.PlaylistScreenViewModel
import com.example.musicplayer.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlaylistDialog(
    onDismissRequest: () -> Unit,
    dialogTitle: String,
    dialogText: String,
    updateText: (String) -> Unit,
    viewModel: PlaylistScreenViewModel = hiltViewModel()
) {
    val userNameHasError by viewModel.userNameHasError.collectAsStateWithLifecycle()
    AlertDialog(
        title = {
            Text(
                text = dialogTitle,
                style = TextStyle(
                    color = Color.White,
                    fontFamily = valeraRound,
                    fontSize = 20.sp
                )
            )
        },
        text = {
            OutlinedTextField(
                value = dialogText,
                onValueChange = { updateText(it) },
                supportingText = {
                    Text(
                        text = if (userNameHasError) "The name may not be empty." else "",
                        color = PurpleAccent
                    )
                },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = PurpleAccent,
                    unfocusedBorderColor = EerieBlackLightTransparent,
                    cursorColor = PurpleAccent,
                    focusedTextColor = Color.White
                )
            )
        },
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
            TextButton(
                onClick = {
                    if (!userNameHasError) {
                        viewModel.onEvent(PlaylistScreenEvent.CreatePlaylist)
                    }
                }
            ) {
                Text(
                    "Create",
                    style = TextStyle(
                        color = Color.White,
                        fontFamily = valeraRound,
                        fontWeight = FontWeight.Bold
                    )
                )
            }
        },
        containerColor = EerieBlack
    )
}