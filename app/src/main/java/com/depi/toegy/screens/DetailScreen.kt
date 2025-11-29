package com.depi.toegy.screens

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import com.depi.toegy.model.Place
import com.depi.toegy.model.Review
import com.depi.toegy.ui.theme.*
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.tasks.await

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun TravelDetailScreenPreview(modifier: Modifier = Modifier) {
    // TravelDetailScreen()
}

@Composable
fun getname() : String{
    val auth = FirebaseAuth.getInstance()
    val db = FirebaseFirestore.getInstance()
    var userName by remember { mutableStateOf("Guest") }


    // Fetch user name from Firestore (stored during signup in SignupScreen)
    LaunchedEffect(Unit) {
        val currentUser = auth.currentUser
        if (currentUser != null) {
            try {
                // Get the user document from Firestore
                // The name is stored here during signup in SignupViewModel
                val userDoc = db.collection("users")
                    .document(currentUser.uid)
                    .get()
                    .await()

                userName = userDoc.getString("name")
                    ?: currentUser.email?.substringBefore("@")
                            ?: "Guest"
            } catch (e: Exception) {
                // Fallback to email if name not found or error occurs
                userName = currentUser.email?.substringBefore("@") ?: "Guest"
            }
        } else {
            userName = "Guest"
        }

    }
    return userName
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TravelDetailScreen(place: Place) {
    val navy = Navy
    val accentYellow = AccentYellow
    val lightGrayText = SubtleGray
    val context = LocalContext.current
    // ⭐ BottomSheet States
    var showRatingSheet by remember { mutableStateOf(false) }
    var userRating by remember { mutableStateOf(0) }
    var userComment by remember { mutableStateOf("") }
    val sheetState = rememberModalBottomSheetState()
    // ⭐ REVIEW LIST (state list)
    val reviews = remember { mutableStateListOf<Review>() }
    val savedName = getname()
    LaunchedEffect(place.id) {
        val reviewsRef = Firebase.firestore
            .collection("Items")
            .document(place.id)
            .collection("Reviews")
        reviewsRef.addSnapshotListener { snapshot, error ->
            if (error != null) {
                Log.e("review", "Failed to load reviews", error)
                return@addSnapshotListener
            }
            if (snapshot != null) {
                reviews.clear()
                reviews.addAll(snapshot.toObjects(Review::class.java))
                Log.d("review", "Loaded reviews: ${reviews.size}")
            }
        }
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(LightBackground)
            .verticalScroll(rememberScrollState())
    ) {
        Box(
            modifier = Modifier
                .height(210.dp)
                .fillMaxWidth()
        ) {
            SubcomposeAsyncImage(
                model = place.img,
                contentDescription = place.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize(),
                loading = {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                },
                error = {
                    SubcomposeAsyncImageContent()
                }
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 18.dp)
        ) {

            Row(
                verticalAlignment = Alignment.Top,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 12.dp)
                ) {
                    Text(
                        text = place.name,
                        fontSize = 22.sp,
                        color = navy,
                        style = MaterialTheme.typography.titleMedium,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        lineHeight = 26.sp
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = place.location,
                        fontSize = 14.sp,
                        color = lightGrayText,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        lineHeight = 18.sp
                    )
                }
                MapToggle(
                    iconSize = 26.dp,
                    navy = navy,
                    onMapClick = {
                        val lat = place.lat
                        val lng = place.long
                        val geoUri =
                            Uri.parse("geo:$lat,$lng?q=$lat,$lng(${Uri.encode(place.name)})")
                        val mapIntent = Intent(Intent.ACTION_VIEW, geoUri).apply {
//                            `package` = "com.google.android.apps.maps"
                        }
                        try {
                            context.startActivity(mapIntent)
                        } catch (e: ActivityNotFoundException) {
                            val fallbackIntent = Intent(Intent.ACTION_VIEW, geoUri)
                            context.startActivity(fallbackIntent)
                        }
                    }
                )
            }

            Spacer(modifier = Modifier.height(20.dp))


            Text(
                text = "Description",
                fontSize = 18.sp,
                color = navy,
                style = MaterialTheme.typography.titleSmall
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = place.desc,
                fontSize = 14.sp,
                color = BodyGray,
                lineHeight = 20.sp,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))


            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(
                    onClick = {
                        val url = place.url
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                        context.startActivity(intent)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = navy),
                    elevation = ButtonDefaults.buttonElevation(
                        defaultElevation = 6.dp,
                        pressedElevation = 4.dp
                    )
                ) {
                    Text(
                        text = "View Website/Info",
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = androidx.compose.ui.text.font.FontWeight.Medium
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        showRatingSheet = true
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = accentYellow),
                    elevation = ButtonDefaults.buttonElevation(
                        defaultElevation = 4.dp,
                        pressedElevation = 2.dp
                    )
                ) {
                    Text(
                        text = "Rate Place",
                        color = NavyDark,
                        fontSize = 16.sp,
                        fontWeight = androidx.compose.ui.text.font.FontWeight.Medium
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))


            Text(
                text = "Reviews",
                fontSize = 18.sp,
                color = navy,
                style = MaterialTheme.typography.titleSmall
            )


            Spacer(modifier = Modifier.height(16.dp))

            if (reviews.isEmpty()) {
                Text(
                    "No reviews yet. Be the first!",
                    color = Color.Gray,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(top = 6.dp)
                )
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                        .padding(top = 10.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(reviews) { review ->
                        ReviewCard(review)
                    }
                }
            }
        }
    }


    //-------------------- ⭐ BOTTOM SHEET --------------------
    if (showRatingSheet) {
        ModalBottomSheet(
            onDismissRequest = { showRatingSheet = false },
            sheetState = sheetState
        ) {
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Rate this Place", fontSize = 20.sp, color = navy)

                Spacer(Modifier.height(16.dp))


                // ✨ Star Rating
                Row {
                    for (i in 1..5) {
                        val isSelected = i <= userRating
                        val scale by animateFloatAsState(
                            targetValue = if (isSelected) 1.3f else 1f,
                            animationSpec = tween(250)
                        )

                        Icon(
                            imageVector = if (isSelected)
                                Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                            contentDescription = null,
                            tint = if (isSelected) Yellow else navy,
                            modifier = Modifier
                                .size(38.dp)
                                .scale(scale)
                                .clickable { userRating = i }
                                .padding(4.dp)
                        )
                    }
                }

                Spacer(Modifier.height(16.dp))

                // ✨ Comment
                OutlinedTextField(
                    value = userComment,
                    onValueChange = { userComment = it },
                    label = { Text("Comment...") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(Modifier.height(20.dp))

                // ✨ Submit
                Button(
                    onClick = {
                       // Log.d("trace", "TravelDetailScreen:before ")
                        if ( userComment.isNotBlank() && userRating > 0) {
                      //      Log.d("trace", "TravelDetailScreen:after ")
                            Firebase.firestore.collection("Items")
                                .document(place.id)
                                .collection("Reviews")
                                .add(
                                    Review(
                                        username = savedName!!,
                                        email = "",
                                        rating = userRating,
                                        comment = userComment
                                    )
                                )
                                .addOnSuccessListener {

                                }
                                .addOnFailureListener { e ->
                                    Log.d("trace", "TravelDetailScreen: fail added${e}")
                                }
                        }
                        showRatingSheet = false

                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(Yellow)
                ) {
                    Text("Submit", color = NavyDark)
                }

                Spacer(Modifier.height(20.dp))
            }
        }
    }

}

@Composable
fun MapToggle(iconSize: Dp, navy: Color, onMapClick: () -> Unit) {
    IconButton(
        onClick = onMapClick,
        modifier = Modifier
            .size(48.dp)
    ) {
        Icon(
            imageVector = Icons.Filled.Place,
            contentDescription = "Open Map",
            tint = navy,
            modifier = Modifier.size(iconSize)
        )
    }
}
// -------------------- REVIEW CARD --------------------
@Composable
fun ReviewCard(review:Review) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(2.dp, RoundedCornerShape(12.dp)),
        colors = CardDefaults.cardColors(containerColor = CardWhite)
    ) {
        Column(Modifier.padding(14.dp)) {
            Text(review.username, fontSize = 16.sp, color = Navy, modifier = Modifier.padding(bottom = 4.dp))

            Row {
                repeat(review.rating) {
                    Icon(
                        Icons.Filled.Favorite,
                        contentDescription = null,
                        tint = Yellow,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }

            Spacer(Modifier.height(6.dp))

            Text(review.comment, fontSize = 14.sp, color = Color.Gray)
        }
    }
}



