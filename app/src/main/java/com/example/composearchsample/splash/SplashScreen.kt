package com.example.composearchsample.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.example.composearchsample.R
import kotlinx.coroutines.delay

/**
 * Created by Kethu on 11/06/2024.
 */
private const val SplashWaitTime: Long = 2000
@Composable
fun SplashScreen(modifier: Modifier = Modifier, onTimeout: () -> Unit) {
    val currentOnTimeout by rememberUpdatedState(onTimeout)
    LaunchedEffect(Unit) {
        delay(SplashWaitTime)
        currentOnTimeout()
    }
    Image(
        painterResource(id = R.drawable.ic_launcher_background),
        contentDescription = null,
        modifier
            .fillMaxSize()
            .wrapContentSize()
    )
}