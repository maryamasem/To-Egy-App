package com.depi.toegy.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.depi.toegy.repo.FavouritesRepository
import com.google.firebase.auth.FirebaseAuth

class FavouritesViewModel(
    private val repo: FavouritesRepository= FavouritesRepository()): ViewModel(){
        var favourites by mutableStateOf<List<FavouritePlace>>(emptyList())
            private set
    private val userId= FirebaseAuth.getInstance().currentUser?.uid
    init {
        userId?.let{loadFavourites(it)}
    }
    private fun loadFavourites(uid:String){
        repo.getFavourites(uid){
            list ->
            favourites=list
        }
    }
    fun addFavourite(place: FavouritePlace) {
        userId?.let {
            repo.addToFavourites(it, place) { isSuccess, _ ->
                if (isSuccess) {
                    favourites = favourites + place
                }

            }
        }
    }
    fun removeFavorite(placeId:String) {
        userId?.let {
            repo.removeFavourite(it, placeId) { isSuccess, _ ->
                if (isSuccess) {
                    favourites = favourites.filterNot { fav -> fav.id == placeId }
                }
            }
        }

    }
    }
