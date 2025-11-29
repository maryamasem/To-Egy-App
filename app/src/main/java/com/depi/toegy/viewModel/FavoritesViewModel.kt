package com.depi.toegy.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.depi.toegy.model.FavoritePlace
import com.depi.toegy.repo.FavoriteResult
import com.depi.toegy.repo.FavoritesRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class FavoritesViewModel(
    private val repository: FavoritesRepository = FavoritesRepository()
) : ViewModel() {
    private val auth = FirebaseAuth.getInstance()
    private var favoritesJob: Job? = null
    private val authListener = FirebaseAuth.AuthStateListener {
        observeFavorites()
    }
    private val _favoritesState = MutableStateFlow<List<FavoritePlace>>(emptyList())
    val favoritesState: StateFlow<List<FavoritePlace>> = _favoritesState.asStateFlow()
    private val _loadingState = MutableStateFlow(false)
    val loadingState: StateFlow<Boolean> = _loadingState.asStateFlow()
    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    fun sanitizedId(text: String): String {
        return text
            .replace(" ", "")
            .replace(".", "_")
            .replace("/", "_")
            .replace("(", "")
            .replace(")", "")
            .replace("-", "_")
            .replace(",", "_")
            .replace("__", "_")
    }

    init {
        auth.addAuthStateListener(authListener)
        observeFavorites()
    }

    private fun observeFavorites() {
        val uid = auth.currentUser?.uid
        favoritesJob?.cancel()
        if (uid == null) {
            _favoritesState.value = emptyList()
            _loadingState.value = false
            return
        }
        favoritesJob = viewModelScope.launch {
            _loadingState.value = true
            repository.getFavorites(uid).collect { result ->
                when (result) {
                    is FavoriteResult.Success -> {
                        _favoritesState.value = result.data
                        _loadingState.value = false
                        _errorMessage.value = null
                    }

                    is FavoriteResult.Error -> {
                        _loadingState.value = false
                        _errorMessage.value = result.message
                    }
                }
            }
        }
    }

    /* fun addFavorite(place: FavoritePlace) {
         val uid = auth.currentUser?.uid ?: run {
             _errorMessage.value = "User not authenticated."
             return
         }
         viewModelScope.launch {
             when (val result = repository.addToFavorites(uid, place)) {
                 is FavoriteResult.Error -> _errorMessage.value = result.message
                 else -> Unit
             }
         }
     }*/

    fun removeFavorite(placeId: String) {
        val uid = auth.currentUser?.uid ?: run {
            _errorMessage.value = "User not authenticated."
            return
        }
        viewModelScope.launch {
            when (val result = repository.removeFavorite(uid, placeId)) {
                is FavoriteResult.Error -> _errorMessage.value = result.message
                else -> Unit
            }
        }
    }

    fun toggleFavorite(place: FavoritePlace) {
        val uid = auth.currentUser?.uid ?: run {
            _errorMessage.value = "User not authenticated."
            return
        }

        // Log original place data
        Log.e(
            "FavoriteDebug",
            "Original place data: Name='${place.name}', Lat='${place.lat}', Long='${place.long}', ID='${place.id}'"
        )

        // Generate stable unique ID safely
        val generatedId = place.id.ifBlank {
            val namePart = place.name.takeIf { it.isNotBlank() } ?: "unknown_name"
            val latPart = place.lat?.toString() ?: "0.0"
            val longPart = place.long?.toString() ?: "0.0"
            sanitizedId("${namePart}_${latPart}_${longPart}")
        }

        Log.e("FavoriteDebug", "Generated ID = '$generatedId'")

        val fixedPlace = place.copy(id = generatedId)

        val isFavorite = _favoritesState.value.any { it.id == generatedId }

        viewModelScope.launch {
            val result = if (isFavorite) {
                repository.removeFavorite(uid, generatedId)
            } else {
                repository.addToFavorites(uid, fixedPlace)
            }

            if (result is FavoriteResult.Error) {
                _errorMessage.value = result.message
                Log.e("FavoriteDebug", "Error: ${result.message}")
            }
        }
    }


    override fun onCleared() {
        super.onCleared()
        favoritesJob?.cancel()
        auth.removeAuthStateListener(authListener)
    }
}

