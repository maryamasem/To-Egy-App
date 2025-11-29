package com.depi.toegy.model

import android.R
import kotlinx.serialization.Serializable

@Serializable
data class Place(
    val id: String = "",
    val name: String ="",
    val lat: Double?=null,
    val long: Double?=null,
    val desc: String="",
    val location: String="",
    val img: String="",
    val url: String?=null
)

data class Review(
    val username: String="",
    val email : String="",
    val rating: Int=0,
    val comment: String=""
)