package com.depi.toegy.repo

import com.depi.toegy.model.FavouritePlace
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration

class FavouritesRepository {

    private val db = FirebaseFirestore.getInstance()

    fun addToFavourites(
        userId: String,
        place: FavouritePlace,
        onResult: (Boolean, String?) -> Unit
    ) {
        // Convert FavouritePlace to Map for Firestore
        val placeData = hashMapOf<String, Any?>(
            "id" to place.id,
            "name" to place.name,
            "desc" to place.desc,
            "location" to place.location,
            "img" to place.img
        )
        
        // Add optional fields only if they're not null
        place.lat?.let { placeData["lat"] = it }
        place.long?.let { placeData["long"] = it }
        place.url?.let { placeData["url"] = it }
        
        db.collection("users")
            .document(userId)
            .collection("favourites")
            .document(place.name)
            .set(placeData)
            .addOnSuccessListener { onResult(true, null) }
            .addOnFailureListener { 
                it.printStackTrace()
                onResult(false, it.message) 
            }
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

    fun getFavourites(userId: String, onResult: (List<FavouritePlace>) -> Unit): ListenerRegistration {
        return db.collection("users")
            .document(userId)
            .collection("favourites")
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    // Log error for debugging
                    error.printStackTrace()
                    onResult(emptyList())
                    return@addSnapshotListener
                }
                
                if (snapshot != null) {
                    if (snapshot.isEmpty) {
                        // Empty collection
                        onResult(emptyList())
                    } else {
                        // Convert documents to FavouritePlace objects
                        val list = mutableListOf<FavouritePlace>()
                        for (document in snapshot.documents) {
                            try {
                                val data = document.data
                                if (data != null) {
                                    val place = FavouritePlace(
                                        id = data["id"] as? String ?: document.id,
                                        name = data["name"] as? String ?: "",
                                        lat = (data["lat"] as? Number)?.toDouble(),
                                        long = (data["long"] as? Number)?.toDouble(),
                                        desc = data["desc"] as? String ?: "",
                                        location = data["location"] as? String ?: "",
                                        img = data["img"] as? String ?: "",
                                        url = data["url"] as? String
                                    )
                                    list.add(place)
                                }
                            } catch (e: Exception) {
                                e.printStackTrace()
                                // Continue with other documents
                            }
                        }
                        onResult(list)
                    }
                } else {
                    // Null snapshot
                    onResult(emptyList())
                }
            }
    }
}
