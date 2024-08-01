package com.example.composearchsample.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.window.DialogProperties
import com.example.composearchsample.R

@Composable
fun CustomDialog(
    icon: ImageVector? = null,
    title: String,
    message: String,
    okText: String = stringResource(id = R.string.ok),
    cancelText: String? = null,
    onConfirmation: (() -> Unit)? = null,
    onDismissRequest: (() -> Unit)? = null
) {
    AlertDialog(
        properties = DialogProperties(
            dismissOnBackPress = false,
            dismissOnClickOutside = false
        ),
        icon = {
            icon?.let {
                Icon(it, contentDescription = null)
            }
        },
        title = {
            Text(text = title)
        },
        text = {
            Text(text = message)
        },
        onDismissRequest = {
            onDismissRequest?.let {
                it()
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirmation?.let {
                        it()
                    }

                }
            ) {
                Text(okText)
            }
        },
        dismissButton = {
            cancelText?.let {
                TextButton(
                    onClick = {
                        onDismissRequest?.let {
                            it()
                        }
                    }
                ) {
                    Text(cancelText)
                }
            }
        }
    )
}