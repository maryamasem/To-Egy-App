package com.depi.toegy.screens


import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import coil.request.ImageRequest
import com.depi.toegy.api.TourismViewModel
import com.depi.toegy.model.Place
import com.depi.toegy.R
import com.depi.toegy.model.FavouritePlace
import com.depi.toegy.viewModel.FavouritesViewModel
import com.depi.toegy.ui.theme.BluePrimary
import com.depi.toegy.ui.theme.NavyDark



@Composable
fun PlacesListScreen(
    navController: NavController,
    category:String,
    modifier: Modifier = Modifier
) {

    Log.d("cat", "PlacesListScreen: $category")
    val vm: TourismViewModel = viewModel()
   // vm.getPlaces(category)

    LaunchedEffect(category) {
        vm.getPlaces(category)
    }




    val stateofApi = vm.state
    var isLoading = vm.isLoading



    val configuration = LocalConfiguration.current
    val screenHeightDp = configuration.screenHeightDp
    val cardHeight = 100 // تقريبي: ارتفاع الكارت + المسافات
    val shimmerItemCount = (screenHeightDp / cardHeight).coerceIn(3, 10)

    var searchQuery by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Places to Visit",
                style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Bold),
                color = BluePrimary
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 🔍 Search Field
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            placeholder = { Text("Search locations...") },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = NavyDark
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

         val places = stateofApi
        if (isLoading ) {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                items(shimmerItemCount) {
                    AnimatedShimmer()
                }
            }
        }
         else {
         val favouriteViewModel : FavouritesViewModel=viewModel()
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(places) { place ->
                        PlaceCard(
                            place=place,
                            favouriteViewModel=favouriteViewModel
                        )
                    }
                }

          }
    }
}

@Composable
fun PlaceCard(place: Place, favouriteViewModel: FavouritesViewModel) {
    val context = LocalContext.current
    // Properly observe the state to trigger recomposition when it changes
    val favourites by favouriteViewModel.favourites
    
    // Check if this place is in favorites
    val isFavorite = favourites.any { it.name == place.name || it.id == place.id }

    Card(
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFFFFFFF))
            .clickable { /* Navigate to details screen */ },
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            SubcomposeAsyncImage(
                model = ImageRequest.Builder(context)
                    .data(place.img)
                    .setHeader("User-Agent", "MyToEgyptApp/1.0")
                    .crossfade(true)
                    .build(),
                contentDescription = place.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(12.dp))

            ) {
                when (painter.state) {
                    is AsyncImagePainter.State.Loading -> {
                        // أثناء التحميل
                        Box(
                            modifier = Modifier
                                .fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(30.dp),
                                strokeWidth = 3.dp
                            )
                        }
                    }

                    is AsyncImagePainter.State.Error -> {
                        // لو الصورة فشلت في التحميل
                        Image(
                            painter = painterResource(R.drawable.pyramids),
                            contentDescription = "Error",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(RoundedCornerShape(12.dp))
                        )
                    }

                    else -> {
                        // لما الصورة تتحمل
                        SubcomposeAsyncImageContent()
                    }
                }
            }


//            AsyncImage(
//                model = ImageRequest.Builder(context)
//                    .data(place.imageUrl)
//                    .placeholder(R.drawable.dog)
//                    .setHeader("User-Agent", "MyToEgyptApp/1.0")
//                    .error(R.drawable.pyramids)
//                    .crossfade(true)
//                    .build(),
//                contentDescription = place.name,
//                contentScale = ContentScale.Crop,
//                modifier = Modifier
//                    .size(60.dp)
//                    .clip(RoundedCornerShape(12.dp))
//            )

//            Image(
//                painter = painterResource(id = place.pic),
//                contentDescription = place.name,
//                contentScale = ContentScale.Crop,
//                modifier = Modifier
//                    .size(80.dp)
//                    .clip(RoundedCornerShape(12.dp))
//            )
            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = place.name,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF0D1B2A)
                )
                Text(
                    text = place.location,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
            }

            IconButton(onClick = {
                if (isFavorite) {
                    // Remove from favorites
                    favouriteViewModel.removeFavorite(place.name)
                } else {
                    // Add to favorites
                    favouriteViewModel.addFavourite(
                        FavouritePlace(
                            id = place.id,
                            name = place.name,
                            lat = place.lat,
                            long = place.long,
                            desc=place.desc,
                            location = place.location,
                            img = place.img,
                            url = place.url
                        )
                    )
                }
            }
            ) {
                Icon(imageVector = if(isFavorite)
                    Icons.Default.Favorite else
                Icons.Default.FavoriteBorder, contentDescription = "Favourite",
                    tint = if(isFavorite)
                Color(0xFFFFC107) else Color.Gray
                )

            }
        }
    }
}



//@Preview(showBackground = true, showSystemUi = true)
//@Composable
//fun PreviewScreen(){
//    PlacesListScreen()
//}

