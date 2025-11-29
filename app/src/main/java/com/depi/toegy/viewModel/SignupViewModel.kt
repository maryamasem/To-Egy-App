package com.depi.toegy.viewModel

import android.content.Context
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class SignupViewModel(): ViewModel() {
    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()

    // Function to handle sign up
    fun registerUser(
        name: String,
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit) {
        viewModelScope.launch {

            try {
                //Create User in Firebase Auth
                val result = auth.createUserWithEmailAndPassword(email, password).await()
                val userId = result.user?.uid ?: throw Exception("User ID not found")

                // Create user data
                val userData = mapOf(
                    "name" to name,
                    "email" to email
                )

                //Save user document in Firestore
                db.collection("users")
                    .document(userId)
                    .set(userData)
                    .await()

                onSuccess()

            } catch (e: Exception) {
                onFailure(e.message ?: "Unknown Error")
            }
        }
    }
    
}