package com.depi.toegy.screens

import android.content.Intent
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.depi.toegy.R
import com.depi.toegy.ui.theme.BackgroundWhite
import com.depi.toegy.ui.theme.NavyBlue
import com.depi.toegy.ui.theme.Yellow

@Composable
fun Home(navController: NavController) {

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        item {
            EgyptIcon(Modifier)
        }

        item {
            MuseumOpeningBannerAnimated {}
            Spacer(Modifier.height(24.dp))
        }

        // ---------- Popular Attraction ----------
        item {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.CenterStart
            ) {
                Text(
                    text = "Popular attraction",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = NavyBlue
                )
            }
        }

        item {
            Spacer(Modifier.height(8.dp))

            Card(
                onClick = {},
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(4.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Box {
                    Image(
                        painter = painterResource(id = R.drawable.egy_museum),
                        contentDescription = "Egyptian Museum",
                        contentScale = ContentScale.FillBounds,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(220.dp)
                    )

                    Box(
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .fillMaxWidth()
                            .background(NavyBlue)
                            .padding(12.dp)
                    ) {
                        Text(
                            text = "Egyptian Museum",
                            color = BackgroundWhite,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
        }

        item { Spacer(Modifier.height(24.dp)) }

        // ---------- Categories Title ----------
        item {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.CenterStart
            ) {
                Text(
                    text = "Categories",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = NavyBlue
                )
            }
        }

        item { Spacer(Modifier.height(8.dp)) }

        // ---------- Categories Rows ----------
        item {
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                CategoryItem(R.drawable.museum_ic, "Museums") {
                    navController.navigate("ListScreen/museums")
                }
                CategoryItem(R.drawable.beachs_ic, "Beaches") {
                    navController.navigate("ListScreen/beaches")
                }
                CategoryItem(R.drawable.resturant_ic, "Restaurant") {
                    navController.navigate("ListScreen/restaurants")
                }
            }
        }

        item { Spacer(Modifier.height(8.dp)) }

        item {
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                CategoryItem(R.drawable.hotel_ic, "Hotels") {
                    navController.navigate("ListScreen/hotels")
                }
                CategoryItem(R.drawable.history_ic, "History") {
                    navController.navigate("ListScreen/history")
                }
                CategoryItem(R.drawable.airport_ic, "Airports") {
                    navController.navigate("ListScreen/airports")
                }
            }
        }
    }
}

@Composable
fun CategoryItem(icon: Int, label: String, onClick: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .width(100.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Color(0xFFEBEEFA))
            .padding(vertical = 16.dp)
            .clickable { onClick() }
    ) {
        Image(
            painter = painterResource(id = icon),
            contentDescription = label,
            modifier = Modifier.size(48.dp)
        )
        Spacer(Modifier.height(8.dp))
        Text(text = label, color = NavyBlue, fontSize = 14.sp)
    }
}

@Composable
fun MuseumOpeningBannerAnimated(onClick: () -> Unit) {

    val context = LocalContext.current
    val intent = Intent(Intent.ACTION_VIEW, "https://www.visit-gem.com/en/home".toUri())

    val alphaAnim = remember { Animatable(0f) }

    LaunchedEffect(Unit) {
        alphaAnim.animateTo(1f, animationSpec = tween(1200))
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .graphicsLayer(alpha = alphaAnim.value)
            .clip(RoundedCornerShape(20.dp))
            .background(NavyBlue)
            .padding(16.dp)
    ) {
        Column {
            Text(text = "🎉 Grand Opening!", fontSize = 18.sp, color = Color.White)
            Spacer(Modifier.height(6.dp))
            Text(
                text = "The Egyptian Museum is now officially open...",
                color = Color.White,
                fontSize = 14.sp
            )

            Spacer(Modifier.height(10.dp))

            Button(
                onClick = { context.startActivity(intent) },
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Yellow)
            ) {
                Text(text = "Show Details", color = NavyBlue)
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun HomePreview() {
    Home(navController = rememberNavController())
}
