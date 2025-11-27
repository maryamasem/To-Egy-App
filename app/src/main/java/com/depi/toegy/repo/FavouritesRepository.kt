package com.depi.toegy.repo

import com.depi.toegy.model.FavoritePlace
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

sealed interface FavoriteResult<out T> {
    data class Success<T>(val data: T) : FavoriteResult<T>
    data class Error(val message: String, val throwable: Throwable? = null) : FavoriteResult<Nothing>
}

class FavoritesRepository(
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
) {
    private val usersCollection get() = firestore.collection("users")

    suspend fun addToFavorites(userId: String, place: FavoritePlace): FavoriteResult<Unit> {
        val documentId = place.id.ifBlank { place.name }.takeIf { it.isNotBlank() }
            ?: return FavoriteResult.Error("Missing place id or name.")

        return runCatching {
            usersCollection
                .document(userId)
                .collection("favorites")
                .document(documentId)
                .set(place.copy(id = documentId))
                .await()
        }.fold(
            onSuccess = { FavoriteResult.Success(Unit) },
            onFailure = {
                FavoriteResult.Error(it.localizedMessage ?: "Unable to add favorite.", it)
            }
        )
    }

    suspend fun removeFavorite(userId: String, placeId: String): FavoriteResult<Unit> {
        if (placeId.isBlank()) {
            return FavoriteResult.Error("Missing favorite id.")
        }

        return runCatching {
            usersCollection
                .document(userId)
                .collection("favorites")
                .document(placeId)
                .delete()
                .await()
        }.fold(
            onSuccess = { FavoriteResult.Success(Unit) },
            onFailure = {
                FavoriteResult.Error(it.localizedMessage ?: "Unable to remove favorite.", it)
            }
        )
    }

    suspend fun isFavorite(userId: String, placeId: String): FavoriteResult<Boolean> {
        if (placeId.isBlank()) {
            return FavoriteResult.Error("Missing favorite id.")
        }
        return runCatching {
            usersCollection
                .document(userId)
                .collection("favorites")
                .document(placeId)
                .get()
                .await()
                .exists()
        }.fold(
            onSuccess = { FavoriteResult.Success(it) },
            onFailure = {
                FavoriteResult.Error(it.localizedMessage ?: "Unable to check favorite.", it)
            }
        )
    }

    fun getFavorites(userId: String): Flow<FavoriteResult<List<FavoritePlace>>> = callbackFlow {
        val registration = usersCollection
            .document(userId)
            .collection("favorites")
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    trySend(
                        FavoriteResult.Error(
                            error.localizedMessage ?: "Unable to load favorites.",
                            error
                        )
                    )
                    return@addSnapshotListener
                }

                if (snapshot == null) {
                    trySend(FavoriteResult.Success(emptyList()))
                    return@addSnapshotListener
                }

                val places = snapshot.documents.mapNotNull { it.toFavoritePlace() }
                trySend(FavoriteResult.Success(places))
            }

        awaitClose { registration.remove() }
    }

    private fun DocumentSnapshot.toFavoritePlace(): FavoritePlace? {
        return runCatching {
            val place = toObject(FavoritePlace::class.java) ?: FavoritePlace()
            val resolvedId = place.id.ifBlank { id }
            place.copy(id = resolvedId)
        }.getOrElse {
            null
        }
    }
}
