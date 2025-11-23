package com.depi.toegy.viewModel

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.depi.toegy.model.FavouritePlace
import com.depi.toegy.repo.FavouritesRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ListenerRegistration

class FavouritesViewModel(
    private val repo: FavouritesRepository = FavouritesRepository()
): ViewModel() {
    // Private mutable state
    private val _favourites = mutableStateOf<List<FavouritePlace>>(emptyList())
    
    // Public read-only state that can be observed
    val favourites: State<List<FavouritePlace>> = _favourites
    
    private val auth = FirebaseAuth.getInstance()
    private var currentUserId: String? = null
    private var snapshotListener: ListenerRegistration? = null
    private var authStateListener: FirebaseAuth.AuthStateListener? = null
    
    private fun getUserId(): String? = auth.currentUser?.uid
    
    init {
        // Set up auth state listener to reload favorites when user changes
        authStateListener = FirebaseAuth.AuthStateListener { firebaseAuth ->
            val newUserId = firebaseAuth.currentUser?.uid
            if (newUserId != currentUserId) {
                currentUserId = newUserId
                // Remove old listener before setting up new one
                snapshotListener?.remove()
                snapshotListener = null
                loadFavourites()
            }
        }
        auth.addAuthStateListener(authStateListener!!)
        currentUserId = getUserId()
        loadFavourites()
    }
    
    private fun loadFavourites() {
        // Remove existing listener if any
        snapshotListener?.remove()
        snapshotListener = null
        
        getUserId()?.let { uid ->
            // The repository uses a snapshot listener, so it will automatically update
            // when favorites change in Firestore
            snapshotListener = repo.getFavourites(uid) { list ->
                // Update on main thread (Firestore callbacks are already on main thread)
                _favourites.value = list
            }
        } ?: run {
            // If no user is logged in, clear favorites
            _favourites.value = emptyList()
        }
    }
    
    fun addFavourite(place: FavouritePlace) {
        getUserId()?.let { uid ->
            // Repository methods are already async, so we can call them directly
            repo.addToFavourites(uid, place) { success, error ->
                if (!success) {
                    // Handle error if needed - favorites will update automatically via snapshot listener
                }
            }
        }
    }
    
    fun removeFavorite(placeId: String) {
        getUserId()?.let { uid ->
            // Repository methods are already async, so we can call them directly
            repo.removeFavourite(uid, placeId) { success, error ->
                if (!success) {
                    // Handle error if needed - favorites will update automatically via snapshot listener
                }
            }
        }
    }
    
    fun refreshFavourites() {
        loadFavourites()
    }
    
    override fun onCleared() {
        super.onCleared()
        // Remove snapshot listener to prevent memory leaks
        snapshotListener?.remove()
        snapshotListener = null
        
        // Remove auth state listener
        authStateListener?.let {
            auth.removeAuthStateListener(it)
        }
        authStateListener = null
    }
}
