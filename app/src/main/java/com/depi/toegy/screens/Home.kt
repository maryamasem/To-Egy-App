package com.depi.toegy.screens

import android.content.Intent
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons

import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextFieldDefaults.contentPadding
import androidx.compose.material3.Text
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
import com.depi.toegy.R

import com.depi.toegy.ui.theme.BackgroundWhite
import com.depi.toegy.ui.theme.Grey
import com.depi.toegy.ui.theme.NavyBlue

import com.depi.toegy.ui.theme.Yellow

@Composable
fun Home() {
    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(contentPadding())
            .padding(horizontal = 16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Icon(
            imageVector = Icons.Default.LocationOn,
            contentDescription = "App icon",
            tint = Yellow,
            modifier = Modifier.size(40.dp)
        )

        Text(
            text = "TO EGY",
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            color = NavyBlue
        )

        Text(
            text = "Your Smart Guide in Egypt",
            color = Grey,
            fontSize = 12.sp
        )

        Spacer(Modifier.height(24.dp))

        MuseumOpeningBannerAnimated {}

        Spacer(Modifier.height(24.dp))


        Text(
            text = "Popular attraction",
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            color = NavyBlue,
            modifier = Modifier.align(Alignment.Start)

        )
        Spacer(Modifier.height(8.dp))




        Card (
            onClick = {

            },
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(4.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            Box {
                Image(
                    painter = painterResource(id = R.drawable.egy_museum),
                    contentDescription = "Egyptian Museum",
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier
                        .fillMaxSize()
                        .height(220.dp)

                )
                Box(
                    modifier = Modifier
                        .align (Alignment.BottomStart)
                        .fillMaxSize()
                        .background(NavyBlue)
                        .padding(12.dp)
                ){
                    Text(
                        text = "Egyptian Museum",
                        color = BackgroundWhite,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium

                    )
                }
            }
        }
        Spacer(Modifier.height(24.dp))


        Text(
            text = "Categories",
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            color = NavyBlue,
            modifier = Modifier.align(Alignment.Start)
        )

        Spacer(Modifier.height(8.dp))

        Column {
            Row (
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxSize()
            ){
                CategoryItem(icon = R.drawable.museum_ic , label = "Museums")
                CategoryItem(icon = R.drawable.beachs_ic, label = "Beaches")
                CategoryItem(icon = R.drawable.resturant_ic, label = "Restaurant")

            }
            Spacer(Modifier.height(8.dp))
            Row (
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxSize()
            ){
                CategoryItem(icon = R.drawable.hotel_ic , label = "Hotels")
                CategoryItem(icon = R.drawable.history_ic, label = "History")
                CategoryItem(icon = R.drawable.airport_ic, label = "Airports")

            }


        }
    }
}
@Composable
fun CategoryItem (icon: Int, label: String){
    Column (
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .width(100.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Color(0xFFEBEEFA))
            .padding(vertical = 16.dp)
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

    val i = Intent(Intent.ACTION_VIEW, "https://www.visit-gem.com/en/home".toUri())
    val context = LocalContext.current

    val alphaAnim = remember { Animatable(0f) }

    LaunchedEffect(Unit) {
        alphaAnim.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 1200)
        )
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
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
                onClick = {
                    context.startActivity(i)
                },
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
fun HomePreview (){
    Home()
}








