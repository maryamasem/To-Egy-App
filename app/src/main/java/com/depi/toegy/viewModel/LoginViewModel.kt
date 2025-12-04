package com.depi.toegy.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class LoginViewModel : ViewModel() {
    private val auth = FirebaseAuth.getInstance()

    var isLoading by mutableStateOf(false)
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    fun loginUser(
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        viewModelScope.launch {
            isLoading = true
            errorMessage = null

            try {
                // Validate fields
                if (email.isBlank()) {
                    val msg = "Please enter your email"
                    errorMessage = msg
                    isLoading = false
                    onFailure(msg)
                    return@launch
                }

                if (password.isBlank()) {
                    val msg = "Please enter your password"
                    errorMessage = msg
                    isLoading = false
                    onFailure(msg)
                    return@launch
                }

                auth.signInWithEmailAndPassword(email, password).await()
                val user = auth.currentUser

                if (user != null) {

                    // Always reload before checking verification status
                    user.reload().await()

                    // CHECK EMAIL VERIFICATION AFTER RELOAD
                    if (!user.isEmailVerified) {
                        val msg = "Please verify your email first"
                        auth.signOut()
                        errorMessage = msg
                        isLoading = false
                        onFailure(msg)
                        return@launch
                    }

                    // If verified → success
                    isLoading = false
                    onSuccess()

                } else {
                    val msg = "Login failed. Please try again."
                    errorMessage = msg
                    isLoading = false
                    onFailure(msg)
                }

            } catch (e: Exception) {
                isLoading = false

                val errorMsg = when {
                    e.message?.contains("invalid-email") == true -> "Invalid email format"
                    e.message?.contains("user-not-found") == true -> "No account found with this email"
                    e.message?.contains("wrong-password") == true -> "Incorrect password"
                    e.message?.contains("network") == true -> "Network error. Please check your connection"
                    else -> e.message ?: "Login failed. Please try again."
                }

                errorMessage = errorMsg
                onFailure(errorMsg)
            }
        }
    }
}


