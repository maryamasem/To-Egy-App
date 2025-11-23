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

    // حالة تسجيل الدخول
    var isLoading by mutableStateOf(false)
        private set
    
    var errorMessage by mutableStateOf<String?>(null)
        private set

    // Function to handle login
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
                // التحقق من صحة البيانات
                if (email.isBlank()) {
                    errorMessage = "Please enter your email"
                    isLoading = false
                    onFailure("Please enter your email")
                    return@launch
                }
                
                if (password.isBlank()) {
                    errorMessage = "Please enter your password"
                    isLoading = false
                    onFailure("Please enter your password")
                    return@launch
                }

                // تسجيل الدخول باستخدام Firebase Auth
                val result = auth.signInWithEmailAndPassword(email, password).await()
                
                if (result.user != null) {
                    isLoading = false
                    onSuccess()
                } else {
                    errorMessage = "Login failed. Please try again."
                    isLoading = false
                    onFailure("Login failed. Please try again.")
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

    // التحقق من حالة تسجيل الدخول
    fun isUserLoggedIn(): Boolean {
        return auth.currentUser != null
    }

    // تسجيل الخروج
    fun logout() {
        auth.signOut()
    }
}

