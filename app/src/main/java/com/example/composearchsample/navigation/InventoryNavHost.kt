package com.example.composearchsample.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.composearchsample.inventory.entry.InventoryEntryScreen
import com.example.composearchsample.inventory.entry.InventoryEntryViewModel
import com.example.composearchsample.inventory.landing.InventoryLandingScreen
import com.example.composearchsample.inventory.landing.InventoryLandingViewModel
import com.example.composearchsample.login.LoginScreen
import com.example.composearchsample.login.LoginViewModel
import com.example.composearchsample.signup.SignUpScreen
import com.example.composearchsample.signup.SignUpViewModel

/**
 * Created by Kethu on 11/06/2024.
 */
@Composable
fun InventoryNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: InventoryLandingScreen = InventoryLandingScreen,
    onAppExist: () -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable<InventoryLandingScreen> {
            val viewModel = hiltViewModel<InventoryLandingViewModel>()
            val uiState = viewModel.uiState.collectAsStateWithLifecycle()
            InventoryLandingScreen(
                viewModel::onEvent,
                uiState,
                navigateToInventoryUpdate = { navController.navigate(InventoryEditScreen) },
                navigateToInventoryEntry = { navController.navigate(InventoryEntryScreen) },
                onAppExist = onAppExist
            )
        }
        composable<InventoryEntryScreen> {
            val viewModel = hiltViewModel<InventoryEntryViewModel>()
            val state = viewModel.uiState.collectAsStateWithLifecycle()
             InventoryEntryScreen(viewModel::onEvent,state,onNavBack={},onInventorySavedSuccessfully={})
        }

        composable<InventoryDetailsScreen> {
            val viewModel = hiltViewModel<LoginViewModel>()
            val loginUiState = viewModel.uiState.collectAsStateWithLifecycle()
            LoginScreen(
                onEvent = viewModel::onEvent,
                state = loginUiState,
                onNavigateToLanding = { navController.navigate(InventoryFlow) },
                onAppExist = onAppExist
            )
        }

        composable<InventoryEditScreen> {
            val viewModel = hiltViewModel<LoginViewModel>()
            val loginUiState = viewModel.uiState.collectAsStateWithLifecycle()
            LoginScreen(
                onEvent = viewModel::onEvent,
                state = loginUiState,
                onNavigateToLanding = { navController.navigate(InventoryFlow) },
                onAppExist = onAppExist
            )
        }
    }
}