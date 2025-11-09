package com.depi.toegy.Model

data class Place(
    val name: String ="",
    val lat: Double?=null,
    val long: Double?=null,
    val desc: String="",
    val location: String="",
    val img: String="",
    val url: String?=null
)