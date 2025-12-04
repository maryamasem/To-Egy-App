package com.depi.toegy.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class ForgotPasswordViewModel : ViewModel() {
    private val auth = FirebaseAuth.getInstance()
    var isLoading by mutableStateOf(false)
        private set
    var errorMessage by mutableStateOf<String?>(null)
        private set
    var successMessage by mutableStateOf<String?>(null)
        private set

    // Function to handle password reset
    fun resetPassword(
        email: String,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        viewModelScope.launch {
            isLoading = true
            errorMessage = null
            successMessage = null
            
            try {
                if (email.isBlank()) {
                    errorMessage = "Please enter your email"
                    isLoading = false
                    onFailure("Please enter your email")
                    return@launch
                }

                auth.sendPasswordResetEmail(email).await()
                
                isLoading = false
                successMessage = "Password reset email sent. Please check your inbox."
                onSuccess()

            } catch (e: Exception) {
                isLoading = false
                val errorMsg = when {
                    e.message?.contains("invalid-email") == true -> "Invalid email format"
                    e.message?.contains("user-not-found") == true -> "No account found with this email"
                    e.message?.contains("network") == true -> "Network error. Please check your connection"
                    else -> e.message ?: "Failed to send reset email. Please try again."
                }
                errorMessage = errorMsg
                onFailure(errorMsg)
            }
        }
    }
}

