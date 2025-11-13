package com.depi.toegy.screens

import ads_mobile_sdk.h6
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.depi.toegy.R
import com.depi.toegy.ui.theme.*

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun TravelDetailScreenPreview(modifier: Modifier = Modifier) {
    TravelDetailScreen()
}

@Composable
fun TravelDetailScreen() {
    val navy = Navy
    val accentYellow = AccentYellow
    val lightGrayText = SubtleGray

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(LightBackground)
    ) {
        Box(
            modifier = Modifier
                .height(210.dp)
                .fillMaxWidth()
        ) {
            Image(
                painter = painterResource(id = R.drawable.giza),
                contentDescription = "Giza",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp, vertical = 18.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Giza Pyramids",
                        fontSize = 22.sp,
                        color = navy,
                        style = MaterialTheme.typography.bodySmall
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Giza Plateau, Egypt",
                        fontSize = 14.sp,
                        color = lightGrayText
                    )
                }
                FavoriteToggle(iconSize = 26.dp, navy = navy)
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(text = "Description", fontSize = 16.sp, color = navy)
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "The Giza Pyramid Complex, known as the Giza Necropolis, is home to the Great Pyramid and other ancient structures built over 4,500 years ago.",
                fontSize = 13.sp,
                color = BodyGray,
                lineHeight = 18.sp
            )

            Spacer(modifier = Modifier.height(18.dp))

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(
                    onClick = {},
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp)
                        .shadow(elevation = 6.dp, shape = RoundedCornerShape(12.dp)),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor  = navy)
                ) {
                    Text(text = "Add to Itinerary", color = Color.White, fontSize = 16.sp)
                }

                Spacer(modifier = Modifier.height(14.dp))

                Button(
                    onClick = {},
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp)
                        .shadow(elevation = 4.dp, shape = RoundedCornerShape(12.dp)),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor  = accentYellow)
                ) {
                    Text(text = "Book Tour/Hotel", color = NavyDark, fontSize = 16.sp)
                }
            }

            Spacer(modifier = Modifier.height(18.dp))
            Text(text = "Reviews", fontSize = 16.sp, color = navy)
        }
    }
}

@Composable
fun FavoriteToggle(iconSize: Dp, navy: Color) {
    var liked by remember { mutableStateOf(false) }

    IconButton(
        onClick = { liked = !liked },
        modifier = Modifier
            .size(44.dp)
            .background(CardWhite, shape = RoundedCornerShape(10.dp))
            .border(1.dp, Color(0xFFEDEFF2), shape = RoundedCornerShape(10.dp))
    ) {
        if (liked) {
            Icon(Icons.Filled.Favorite, contentDescription = "liked", tint = Color.Red, modifier = Modifier.size(iconSize))
        } else {
            Icon(Icons.Outlined.FavoriteBorder, contentDescription = "not liked", tint = navy, modifier = Modifier.size(iconSize))
        }
    }
}
