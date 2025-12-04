package com.depi.toegy.screens

import android.content.Intent
import androidx.annotation.DrawableRes
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
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
import coil.compose.rememberAsyncImagePainter
import com.depi.toegy.R
import com.depi.toegy.model.Place
import com.depi.toegy.screens.CategoryItem
import com.depi.toegy.ui.theme.BackgroundWhite
import com.depi.toegy.ui.theme.NavyBlue
import com.depi.toegy.ui.theme.Yellow
import org.junit.experimental.categories.Categories

@Composable
fun Home(navController: NavController) {

    val imgMuseum = painterResource(R.drawable.egy_museum)
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        item { EgyptIcon(Modifier) }

        item { MuseumOpeningBannerAnimated() }

        item {
            Text(
                text = "Popular attraction",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = NavyBlue,
                modifier = Modifier.fillMaxWidth()
            )
        }

        item {
            Card(
                onClick = {
                    navController.navigate(Place(
                        id = "museums-0",
                        name= "Grand Egyptian Museum (GEM)",
                        lat = 29.9950,
                        long =  31.1193,
                        desc = "The Grand Egyptian Museum (GEM) in Giza, Egypt, is the largest museum globally dedicated entirely to ancient Egyptian civilization. It houses over 100,000 artifacts, including the full treasure collection of King Tutankhamun displayed together for the first time.",
                        location =  "Cairo - Alexandria Desert Road, Al Remayah Square, Kafr Nassar, Al Haram, Giza Governorate, Egypt.",
                        img =  "https://i.pinimg.com/736x/fd/91/62/fd91623912310ff1218c81b097627324.jpg",
                        url = "https://gem.eg/"
                    ))
                },
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(4.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Box {
                    Image(
                        painter = imgMuseum,
                        contentDescription = "The Grand Egyptian Museum",
                        contentScale = ContentScale.Crop,
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
                            text = "The Grand Egyptian Museum",
                            color = BackgroundWhite,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
        }

        item { Spacer(Modifier.height(24.dp)) }

        item {
            Text(
                text = "Categories",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = NavyBlue,
                modifier = Modifier.fillMaxWidth()
            )
        }
        val categories = Category.getSampleCategories(navController)
        item {
            CategoriesSection(categories)
        }
        item { Spacer(Modifier.height(50.dp)) }

    }
}


@Composable
fun CategoriesSection(categories: List<Category>){
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        for (i in categories.indices step 3){
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                for (j in i until (i+3).coerceAtMost(categories.size)){
                    val category = categories[j]
                    CategoryItem(
                        modifier = Modifier.weight(1f),
                        icon = category.icon,
                        label = category.label,
                        onClick = category.onClick
                    )
                }
            }
        }
    }
}




@Composable
fun CategoryItem(modifier:Modifier ,@DrawableRes icon: Int, label: String, onClick: () -> Unit) {
    val context = LocalContext.current
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .width(100.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Color(0xFFEBEEFA))
            .padding(vertical = 16.dp)
            .clickable { onClick() }
    ) {
        Image(
            painter = rememberAsyncImagePainter("android.resource://${context.packageName}/$icon"),
            contentDescription = label,
            modifier = Modifier.size(48.dp)
        )
        Spacer(Modifier.height(8.dp))
        Text(text = label, color = NavyBlue, fontSize = 14.sp)
    }
}

@Composable
fun MuseumOpeningBannerAnimated() {

    val context = LocalContext.current
    val intent = Intent(Intent.ACTION_VIEW, "https://www.visit-gem.com/en/home".toUri())

    val alphaAnim = remember { Animatable(0f) }

    LaunchedEffect(Unit) {
        alphaAnim.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 900)
        )
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