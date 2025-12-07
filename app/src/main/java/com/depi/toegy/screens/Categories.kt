package com.depi.toegy.screens

import androidx.annotation.DrawableRes
import androidx.navigation.NavController
import com.depi.toegy.R

data class Category(
    @DrawableRes val icon: Int,
    val label: String,
    val onClick: () -> Unit
){
    companion object{
        fun getSampleCategories(navController: NavController) :List<Category>{
            return listOf(
                Category(R.drawable.museum_ic,"Museums"){
                    navController.navigate("ListScreen/museums")
                },
                Category(R.drawable.beachs_ic, "Beaches") {
                    navController.navigate("ListScreen/beaches")
                },
                Category(R.drawable.resturant_ic, "Restaurant") {
                    navController.navigate("ListScreen/restaurants")
                },
                Category(R.drawable.hotel_ic, "Hotels") {
                    navController.navigate("ListScreen/hotels")
                },
                Category(R.drawable.history_ic, "History") {
                    navController.navigate("ListScreen/history")
                },
                Category(R.drawable.airport_ic, "Airports") {
                    navController.navigate("ListScreen/airports")
                }

            )
        }
    }
}
