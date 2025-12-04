package com.depi.toegy.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.depi.toegy.model.Place
import com.depi.toegy.screens.TravelDetailScreen
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Login : Screen("login")
    object Main : Screen("main")
}

@Composable
fun AppNavigation(
    navController: NavHostController = rememberNavController(),
    startDestination: String = Screen.Splash.route
) {
    val auth = FirebaseAuth.getInstance()
    val coroutineScope = rememberCoroutineScope()

    // Track whether there is a logged-in and verified user
    var isUserLoggedIn by remember { mutableStateOf(false) }

    // Initial check: reload user and verify email status
    LaunchedEffect(Unit) {
        val user = auth.currentUser
        if (user != null) {
            try {
                user.reload().await()
            } catch (_: Exception) {
                // Ignore reload errors; fallback to current cached state
            }
        }
        isUserLoggedIn = auth.currentUser?.isEmailVerified == true
    }

    DisposableEffect(Unit) {
        val authStateListener = FirebaseAuth.AuthStateListener { firebaseAuth ->
            val user = firebaseAuth.currentUser
            if (user == null) {
                isUserLoggedIn = false
            } else {
                // When auth state changes, always reload before checking verification
                coroutineScope.launch {
                    try {
                        user.reload().await()
                    } catch (_: Exception) {
                        // Ignore reload errors
                    }
                    isUserLoggedIn = auth.currentUser?.isEmailVerified == true
                }
            }
        }
        auth.addAuthStateListener(authStateListener)

        onDispose {
            auth.removeAuthStateListener(authStateListener)
        }
    }

    // Navigate based on auth state changes
    LaunchedEffect(isUserLoggedIn) {
        val currentRoute = navController.currentDestination?.route
        if (isUserLoggedIn && currentRoute != Screen.Main.route) {
            navController.navigate(Screen.Main.route) {
                popUpTo(0) { inclusive = true }
            }
        } else if (!isUserLoggedIn && currentRoute != Screen.Splash.route && currentRoute != Screen.Login.route) {
            navController.navigate(Screen.Login.route) {
                popUpTo(0) { inclusive = true }
            }
        }
    }

    // Determine start destination based on login status
    val actualStartDestination = if (isUserLoggedIn) {
        Screen.Main.route
    } else {
        startDestination
    }

    NavHost(
        navController = navController,
        startDestination = actualStartDestination
    ) {
        composable(Screen.Splash.route) {
            _root_ide_package_.com.depi.toegy.screens.SplashScreenUI(
                onNavigateToLogin = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Splash.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Screen.Login.route) {
            _root_ide_package_.com.depi.toegy.navigation.AuthNavHost(
                onAuthSuccess = {
                    navController.navigate(Screen.Main.route) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }

        composable(Screen.Main.route) {
            _root_ide_package_.com.depi.toegy.screens.MainScreen()
        }

    }
}

