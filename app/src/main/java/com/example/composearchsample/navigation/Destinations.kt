package com.example.composearchsample.navigation

import kotlinx.serialization.Serializable

/**
 * Created by Kethu on 11/06/2024.
 */
@Serializable
object Splash

@Serializable
object Login

@Serializable
object SignUp

@Serializable
object InventoryFlow

@Serializable
data class InventoryEntryScreen(val id: Int = 0)

@Serializable
object InventoryLandingScreen

