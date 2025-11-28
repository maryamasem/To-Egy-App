package com.depi.toegy.model

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