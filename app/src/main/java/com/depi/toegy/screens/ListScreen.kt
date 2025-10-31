package com.depi.toegy.screens


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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.depi.toegy.R
import com.depi.toegy.ui.theme.BluePrimary
import com.depi.toegy.ui.theme.NavyDark


val places = listOf(
    Place("Giza Pyramids", "Giza Plateau", R.drawable.pyramids),
    Place("Philae Temple", "Aswan", R.drawable.attractions),
    Place("Luxor Temple", "Luxor", R.drawable.pyramids),
    Place("Sharm El Sheikh Reefs", "Sharm El Sheikh", R.drawable.attractions),
    Place("Bibliotheca Alexandrina", "Alexandria", R.drawable.pyramids)
)
@Composable
fun PlacesListScreen(modifier: Modifier = Modifier) {

//    val vm = remember { PlacesViewModel() }
//    val stateofApi = vm.state2
//    var isLoading = vm.isLoading

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

//        if (isLoading ) {
//            LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
//                items(shimmerItemCount) {
//                    AnimatedShimmer()
//                }
//            }
//        }
        // else {

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(places) { place ->
                PlaceCard(
                    place
                )
            }
        }

        //  }
    }
}

@Composable
fun PlaceCard(place: Place) {
    val context = LocalContext.current
    var isFavorite by remember { mutableStateOf(false) }

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

            Image(
                painter = painterResource(id = place.pic),
                contentDescription = place.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(12.dp))
            )
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

            IconButton(onClick = { isFavorite = !isFavorite }) {
                Icon(
                    imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = "Favorite",
                    tint = if (isFavorite) Color(0xFFFFC107) else Color.Gray
                )
            }
        }
    }
}



@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewScreen(){
    PlacesListScreen()
}

data class Place(
    val name: String,
    val location: String,
    //  val description: String? = null,
    val pic:Int ,
    val imageUrl: String? = null,
    val lat: Double? = null,
    val lon: Double? = null
)
