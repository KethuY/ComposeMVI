package com.example.composearchsample

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.example.composearchsample.navigation.CustomNavHost
import com.example.composearchsample.ui.theme.ComposeArchSampleTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        mainViewModel.onEvent(MainEvent.GetUserStatus)
        lifecycleScope.launch {
            mainViewModel.uiState.flowWithLifecycle(lifecycle).collectLatest {
                setContent {
                    ComposeArchSampleTheme {
                        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                            CustomNavHost(
                                modifier = Modifier.padding(innerPadding),
                                isNewUser = it.isNewUser,
                                isSignedIn = it.isSignInUser,
                            ) {
                                mainViewModel.onEvent(MainEvent.ClearLoginPreferences)
                                finishAndRemoveTask()
                            }
                        }
                    }
                }
            }
        }
    }
}


