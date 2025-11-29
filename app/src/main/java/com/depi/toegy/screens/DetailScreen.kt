package com.depi.toegy.screens

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import com.depi.toegy.ui.theme.*

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun TravelDetailScreenPreview(modifier: Modifier = Modifier) {
    // TravelDetailScreen()
}

@Composable
fun TravelDetailScreen(place: Place) {
    val navy = Navy
    val accentYellow = AccentYellow
    val lightGrayText = SubtleGray
    val context = LocalContext.current

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
                        val geoUri = Uri.parse("geo:$lat,$lng?q=$lat,$lng(${Uri.encode(place.name)})")
                        val mapIntent = Intent(Intent.ACTION_VIEW, geoUri).apply {
                            `package` = "com.google.android.apps.maps"
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
                    onClick = {},
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
                        text = "Book Tour/Hotel",
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

            Text(
                text = "No reviews yet",
                fontSize = 14.sp,
                color = lightGrayText,
                modifier = Modifier.padding(bottom = 32.dp)
            )
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
