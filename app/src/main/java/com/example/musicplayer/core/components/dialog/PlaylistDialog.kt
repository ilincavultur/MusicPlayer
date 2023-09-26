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
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.musicplayer.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlaylistDialog(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    dialogTitle: String,
    dialogText: String,
    updateText: (String) -> Unit,
    userNameHasError: Boolean
) {
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
                onValueChange = { updateText(it) }
            )
            if (userNameHasError) {
                Text(
                    text = "The name may not be empty.",
                    color = PurpleAccent
                )
            }
        },
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirmation()
                },
            ) {
                Text(
                    "Create",
                    style = TextStyle(
                        color = PurpleAccent,
                        fontFamily = valeraRound,
                    )
                )
            }
        },
        containerColor = EerieBlack
    )
}