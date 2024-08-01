package com.example.composearchsample.utils

import android.text.TextUtils
import android.util.Patterns


/**
 * Created by Kethu on 24/06/2024.
 */
object Utils {
    fun isValidEmail(target: CharSequence?): Boolean {
        return !TextUtils.isEmpty(target) && target?.let { Patterns.EMAIL_ADDRESS.matcher(it).matches() } == true
    }
}