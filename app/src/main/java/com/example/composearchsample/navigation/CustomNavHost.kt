package com.example.composearchsample.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.composearchsample.login.LoginScreen
import com.example.composearchsample.login.LoginViewModel
import com.example.composearchsample.signup.SignUpScreen
import com.example.composearchsample.signup.SignUpViewModel
import com.example.composearchsample.splash.SplashScreen

/**
 * Created by Kethu on 11/06/2024.
 */
@Composable
fun CustomNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: Splash = Splash,
    isSignedIn: Boolean = false,
    isNewUser: Boolean = false,
    onAppExist: () -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable<Splash> {
            SplashScreen {
                when {
                    isNewUser   -> navController.navigate(SignUp)
                    !isSignedIn -> navController.navigate(Login)
                    else -> navController.navigate(InventoryFlow)
                }
            }
        }
        composable<SignUp> {
            val viewModel = hiltViewModel<SignUpViewModel>()
            val state = viewModel.uiState.collectAsStateWithLifecycle()
            SignUpScreen(viewModel::onEvent, state, onAppExist = {
                onAppExist()
            }, onNavigateToLanding = {
                navController.navigate(InventoryFlow)
            })
        }
        composable<InventoryFlow> {
            InventoryNavHost {
                onAppExist()
            }
        }
        composable<Login> {
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