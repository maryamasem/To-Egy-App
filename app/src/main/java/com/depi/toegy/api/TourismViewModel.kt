package com.depi.toegy.api

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.depi.toegy.model.Place
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class TourismViewModel : ViewModel() {
    var state by mutableStateOf<List<Place>>(emptyList())
    var isLoading by mutableStateOf(false)
    val apiService : TourismApiService

    init {
        val retrofit = Retrofit.Builder().baseUrl("https://news-app-c61b1-default-rtdb.firebaseio.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        apiService = retrofit.create(TourismApiService::class.java)
    }

    fun getPlaces(category: String){
        viewModelScope.launch { // by default main thread
            try {
                isLoading = true
              //  Log.d("trace", "Step 1: entered try")
                val placesFromRemote = getPlacesFromRemote(category)
                state = placesFromRemote.mapIndexed { index, place ->
                    place.copy(id = "$category-$index")
                }
             //   Log.d("trace", "Step 2: finished getPlacesFromRemote")
            } catch (e: Exception) {
             //   Log.e("trace", "Step 3: entered catch with error: ${e.message}", e)
                state = emptyList()
            } finally {
                isLoading = false
            }
        }
    }


    private suspend fun getPlacesFromRemote(category: String) =
        withContext(Dispatchers.IO){ apiService.getPlaces(category)}


}