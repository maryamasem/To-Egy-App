package com.depi.toegy.screens

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.depi.toegy.model.FavouritePlace
import com.depi.toegy.viewModel.FavouritesViewModel

@Composable
fun FavouriteButton(
    isFavouritePlace: Boolean,
    onToggle:() -> Unit
){
    IconButton(onClick = onToggle) {
        Icon(imageVector = if(isFavouritePlace) Icons.Default.Favorite else
            Icons.Default.FavoriteBorder, contentDescription = null, tint = if(isFavouritePlace) Color.Red else Color.Gray
        )
    }

}