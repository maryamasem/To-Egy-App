package com.depi.toegy.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.depi.toegy.R
import com.depi.toegy.ui.theme.NavyBlue
import androidx.compose.ui.platform.testTag

@Composable
fun SplashScreenUI(
    onNavigateToLogin: () -> Unit = {},
    pagerState: PagerState = rememberPagerState(initialPage = 0) { 3 }
) {
    val coroutineScope = rememberCoroutineScope()

    Box(modifier = Modifier.fillMaxHeight()) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxSize()
                .testTag("pager")
        ) { page ->

            val (text, resource, description) = when (page) {
                0 -> Triple("TO EGY", R.drawable.pyramids, "Your Smart Guide in Egypt")
                1 -> Triple(
                    "Discover Attraction",
                    R.drawable.museum,
                    "Explore Egypt like never before! To EGY is your personal travel guide helping you discover the most famous attractions, hidden gems, and cultural landmarks across Egypt."
                )
                else -> Triple(
                    "Plan Your Trips",
                    R.drawable.nile,
                    "Your next adventure starts here! Choose your destinations, organize your plans, and make every trip unforgettable."
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 20.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(40.dp))
                Text(
                    text = text,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Serif,
                    color = NavyBlue,
                    modifier = Modifier.padding(bottom = 15.dp, top = 35.dp)
                )
                Image(
                    painter = painterResource(id=resource),
                    contentDescription = "OnBoarding Screens",
                    contentScale =  ContentScale.Crop,
                    modifier=Modifier
                        .fillMaxWidth()
                        .height(410.dp)
                        .clip(RoundedCornerShape(16.dp))
                )
                Spacer(modifier = Modifier.height(10.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp)
                        .verticalScroll(rememberScrollState()),
                    contentAlignment = Alignment.TopCenter
                ) {
                    Text(
                        text = description,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = NavyBlue,
                        lineHeight = 20.sp
                    )
                }
                Spacer(modifier = Modifier.height(30.dp))
            }
        }

        // Next Button
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(horizontal = 20.dp, vertical = 120.dp),
            horizontalArrangement = Arrangement.End
        ) {
            AnimatedVisibility(
                visible = (pagerState.currentPage == 2),
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                Button(
                    onClick = { onNavigateToLogin() },
                    modifier = Modifier.testTag("next_button")
                        .width(80.dp)
                        .height(40.dp)
                ) {
                    Text(
                        "Next",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                    )
                }
            }
        }

        // Indicators
        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 80.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            repeat(3) { index ->
                CustomIndicator(isSelected = pagerState.currentPage == index, index = index)
                Spacer(modifier = Modifier.size(2.5.dp))
            }
        }
    }
}

@Composable
fun CustomIndicator(isSelected: Boolean, index: Int) {
    Box(
        modifier = Modifier
            .testTag("indicator_$index")
            .height(10.dp)
            .width(if (isSelected) 20.dp else 15.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(if (isSelected) NavyBlue else Color.LightGray)
    )
}

@Preview(showBackground = true)
@Composable
fun onBoardingScreenPreview() {
    SplashScreenUI()
}
