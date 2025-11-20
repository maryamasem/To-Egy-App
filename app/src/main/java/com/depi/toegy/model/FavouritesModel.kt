package com.depi.toegy.model

data class FavouritePlace(
    val name: String ="",
    val lat: Double?=null,
    val long: Double?=null,
    val desc: String="",
    val location: String="",
    val img: String="",
    val url: String?=null
)
