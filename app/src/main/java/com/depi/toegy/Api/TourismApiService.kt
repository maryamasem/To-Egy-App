package com.depi.toegy.Api

import com.depi.toegy.Model.Place
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