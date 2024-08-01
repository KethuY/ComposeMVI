package com.example.composearchsample.base

import androidx.lifecycle.ViewModel

abstract class BaseMVIViewModel<ScreenEvent>() : ViewModel() {
    abstract fun onEvent(screenEvent: ScreenEvent)
}