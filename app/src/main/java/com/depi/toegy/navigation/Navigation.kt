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

    // Track whether the user is logged in & verified
    var isUserLoggedIn by remember { mutableStateOf(auth.currentUser?.isEmailVerified == true) }

    // Very light listener (NO reload)
    DisposableEffect(Unit) {
        val listener = FirebaseAuth.AuthStateListener { firebaseAuth ->
            val user = firebaseAuth.currentUser
            isUserLoggedIn = user?.isEmailVerified == true
        }
        auth.addAuthStateListener(listener)

        onDispose {
            auth.removeAuthStateListener(listener)
        }
    }

    // Navigation when login state changes
    LaunchedEffect(isUserLoggedIn) {
        val current = navController.currentDestination?.route

        if (isUserLoggedIn && current != Screen.Main.route) {
            navController.navigate(Screen.Main.route) {
                popUpTo(0) { inclusive = true }
            }
        } else if (!isUserLoggedIn && current != Screen.Splash.route && current != Screen.Login.route) {
            navController.navigate(Screen.Login.route) {
                popUpTo(0) { inclusive = true }
            }
        }
    }

    // Start destination logic
    val actualStartDestination =
        if (isUserLoggedIn) Screen.Main.route else startDestination

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
                    // user.emailVerified already cached
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


