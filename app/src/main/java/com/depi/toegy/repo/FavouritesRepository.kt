package com.depi.toegy.repo

import com.depi.toegy.model.FavouritePlace
import com.google.firebase.firestore.FirebaseFirestore



class FavouritesRepository {

    private val db = FirebaseFirestore.getInstance()

    fun addToFavourites(
        userId: String,
        place: FavouritePlace,
        onResult: (Boolean, String?) -> Unit
    ) {
        db.collection("users")
            .document(userId)
            .collection("favourites")
            .document(place.id)
            .set(place)
            .addOnSuccessListener { onResult(true, null) }
            .addOnFailureListener { onResult(false, it.message) }
    }

    fun removeFavourite(
        userId: String,
        placeId: String,
        onResult: (Boolean, String?) -> Unit
    ) {
        db.collection("users")
            .document(userId)
            .collection("favourites")
            .document(placeId)
            .delete()
            .addOnSuccessListener { onResult(true, null) }
            .addOnFailureListener { onResult(false, it.message) }
    }

    fun getFavourites(userId: String, onResult: (List<FavouritePlace>) -> Unit) {
        db.collection("users")
            .document(userId)
            .collection("favourites")
            .addSnapshotListener { snapshot, _ ->
                if (snapshot != null) {
                    val list = snapshot.toObjects(FavouritePlace::class.java)
                    onResult(list)
                }
            }
    }
}
