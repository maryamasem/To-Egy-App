package com.depi.toegy.api

import com.depi.toegy.model.Place
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface TourismApiService {

    @GET("{category}.json")
    suspend fun getPlaces(
        @Path("category") category: String,
        @Header("User-Agent") userAgent: String = "MyToEgyptApp/1.0"
    ) :List<Place>

}