package com.depi.toegy.model

import androidx.annotation.Keep
import com.google.firebase.firestore.IgnoreExtraProperties

@Keep
@IgnoreExtraProperties
data class FavoritePlace(
    val id: String = "",
    val name: String = "",
    val lat: Double? = null,
    val long: Double? = null,
    val desc: String = "",
    val location: String = "",
    val img: String = "",
    val url: String? = null
)

