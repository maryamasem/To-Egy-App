package com.depi.toegy.screens

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.depi.toegy.viewModel.LoginViewModel
import com.depi.toegy.viewModel.SignupViewModel
import com.depi.toegy.viewModel.ForgotPasswordViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

sealed class AuthScreen(val route: String) {
    object Login : AuthScreen("login")
    object SignUp : AuthScreen("signup")
    object ForgotPassword : AuthScreen("forgot_password")
}

@Composable
fun AuthNavHost(
    navController: NavHostController = rememberNavController(),
    startDestination: String = AuthScreen.Login.route,
    onAuthSuccess: () -> Unit = {}
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(AuthScreen.Login.route) {
            val loginViewModel: LoginViewModel = viewModel()
            LoginScreen(
                onNavigateToSignUp = {
                    navController.navigate(AuthScreen.SignUp.route)
                },
                onNavigateToForgotPassword = {
                    navController.navigate(AuthScreen.ForgotPassword.route)
                },
                onLoginSuccess = {
                    onAuthSuccess()
                },
                viewModel = loginViewModel
            )
        }

        composable(AuthScreen.SignUp.route) {
            val signupViewModel: SignupViewModel = viewModel()
            SignUpScreen(
                onNavigateToLogin = {
                    navController.navigate(AuthScreen.Login.route) {
                        popUpTo(AuthScreen.Login.route) { inclusive = false }
                    }
                },
                onSignUpSuccess = {
                    onAuthSuccess()
                },
                viewModel = signupViewModel
            )
        }

        composable(AuthScreen.ForgotPassword.route) {
            val forgotPasswordViewModel: ForgotPasswordViewModel = viewModel()
            ForgotPasswordScreen(
                onNavigateToLogin = {
                    navController.navigate(AuthScreen.Login.route) {
                        popUpTo(AuthScreen.Login.route) { inclusive = false }
                    }
                },
                viewModel = forgotPasswordViewModel
            )
        }
    }
}

