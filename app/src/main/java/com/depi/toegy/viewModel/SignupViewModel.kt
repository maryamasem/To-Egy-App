package com.depi.toegy.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class SignupViewModel : ViewModel() {
    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()

    fun registerUser(
        name: String,
        email: String,
        password: String,
        onSuccess: (String) -> Unit, // send message to UI
        onFailure: (String) -> Unit
    ) {
        viewModelScope.launch {
            try {
                // 1) Create user in Firebase Auth
                val result = auth.createUserWithEmailAndPassword(email, password).await()
                val user = result.user ?: throw Exception("User not found")

                // 2) Save data to Firestore BEFORE sending verification
                val userData = mapOf(
                    "name" to name,
                    "email" to email
                )
                db.collection("users")
                    .document(user.uid)
                    .set(userData)
                    .await()

                // 3) Send email verification
                user.sendEmailVerification().await()

                // 4) Sign out user so they must log in only after verification
                auth.signOut()

                // 5) Notify UI
                onSuccess("Verification email sent. Please check your inbox.")

            } catch (e: Exception) {
                onFailure(e.message ?: "Unknown error")
            }
        }
    }

}
