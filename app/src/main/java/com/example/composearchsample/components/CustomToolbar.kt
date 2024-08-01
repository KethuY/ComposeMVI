package com.example.composearchsample.components

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.style.TextOverflow
import com.example.composearchsample.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomToolbar(title: String, imageVector: ImageVector = Icons.Filled.ArrowBack,
                  onBack: (() -> Unit) = { }, isNavIcon: Boolean = false
) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                title,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.titleMedium
            )
        },
        navigationIcon = {
            if (isNavIcon) {
                IconButton(onClick = onBack) {
                    Icon(
                        imageVector = imageVector,
                        contentDescription = "Localized description"
                    )
                }
            }
        },
        windowInsets = WindowInsets(
            top = dimensionResource(id = R.dimen.size_0dp),
            bottom = dimensionResource(id = R.dimen.size_0dp)
        )
    )
}